package com.abmn.library.restapicall.Core;
import android.os.Build;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;
public abstract class MultiPartRequest extends Request<String> {

    private final Response.Listener<String> mListener;
    private final Map<String, String> mParams;
    private final Map<String, String> mFileUploads;

    public static final int TIMEOUT_MS = 30000;
    private final String mBoundary = "Volley-" + System.currentTimeMillis();
    private final String lineEnd = "\r\n";
    private final String twoHyphens = "--";

    private final ByteArrayOutputStream mOutputStream = new ByteArrayOutputStream();

    public MultiPartRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Request.Method.POST, url, errorListener);
        setRetryPolicy(new DefaultRetryPolicy(TIMEOUT_MS, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mListener = listener;
        mParams = getDefaultParams();
        mFileUploads = getFileParams();
    }

    private void writeFirstBoundary() throws IOException {
        mOutputStream.write((twoHyphens + mBoundary + lineEnd).getBytes());
    }

    @Override
    public String getBodyContentType() {
        return "multipart/form-data;boundary=" + mBoundary;
    }

    @Override
    public byte[] getBody() {
        try (DataOutputStream dos = new DataOutputStream(mOutputStream)) {
            // Text parameters
            if (mParams != null && !mParams.isEmpty()) {
                textParse(dos, mParams, getParamsEncoding());
            }

            // File uploads
            if (mFileUploads != null) {
                for (Map.Entry<String, String> entry : mFileUploads.entrySet()) {
                    String filePath = entry.getValue();
                    if (filePath == null) {
                        throw new IllegalArgumentException("File path for key " + entry.getKey() + " is null");
                    }

                    File file = new File(filePath);
                    if (!file.exists()) {
                        throw new IllegalArgumentException("File " + file.getAbsolutePath() + " does not exist");
                    }

                    writeFirstBoundary();

                    String fileName = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        fileName = URLEncoder.encode(file.getName(), StandardCharsets.UTF_8);
                    }
                    dos.writeBytes("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"; filename=\"" + fileName + "\"" + lineEnd);
                    dos.writeBytes("Content-Type: application/octet-stream" + lineEnd);
                    dos.writeBytes("Content-Transfer-Encoding: binary" + lineEnd);
                    dos.writeBytes(lineEnd);

                    try (FileInputStream fin = new FileInputStream(file)) {
                        byte[] buffer = new byte[4096];
                        int len;
                        while ((len = fin.read(buffer)) != -1) {
                            dos.write(buffer, 0, len);
                        }
                    }
                    dos.writeBytes(lineEnd);
                }
            }

            // Byte data payloads
            Map<String, ArrayList<DataPart>> byteData = getByteData();
            if (byteData != null && !byteData.isEmpty()) {
                dataParse(dos, byteData);
            }

            // Closing boundary
            dos.writeBytes(twoHyphens + mBoundary + twoHyphens + lineEnd);

            return mOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to build multipart body", e);
        }
    }

    private void textParse(DataOutputStream dataOutputStream, Map<String, String> params, String encoding) throws IOException {
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                buildTextPart(dataOutputStream, entry.getKey(), entry.getValue());
            }
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + encoding, uee);
        }
    }

    private void buildTextPart(DataOutputStream dataOutputStream, String parameterName, String parameterValue) throws IOException {
        dataOutputStream.writeBytes(twoHyphens + mBoundary + lineEnd);
        dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + parameterName + "\"" + lineEnd);
        dataOutputStream.writeBytes(lineEnd);
        dataOutputStream.write(parameterValue.getBytes(StandardCharsets.UTF_8));
        dataOutputStream.writeBytes(lineEnd);
    }

    public abstract Map<String, String> getHeaders();

    public Map<String, String> getDefaultParams() {
        return null;
    }

    public Map<String, String> getFileParams() {
        return null;
    }

    protected Map<String, ArrayList<DataPart>> getByteData() {
        return null;
    }

    private void dataParse(DataOutputStream dataOutputStream, Map<String, ArrayList<DataPart>> data) throws IOException {
        for (Map.Entry<String, ArrayList<DataPart>> entry : data.entrySet()) {
            buildDataPart(dataOutputStream, entry.getValue(), entry.getKey());
        }
    }

    private void buildDataPart(DataOutputStream dos, ArrayList<DataPart> dataFile, String inputName) throws IOException {
        for (DataPart dp : dataFile) {
            writeFirstBoundary();
            dos.writeBytes("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + dp.getFileName() + "\"" + lineEnd);
            if (dp.getType() != null && !dp.getType().trim().isEmpty()) {
                dos.writeBytes("Content-Type: " + dp.getType() + lineEnd);
            }
            dos.writeBytes(lineEnd);

            ByteArrayInputStream fileInputStream = new ByteArrayInputStream(dp.getContent());
            byte[] buffer = new byte[4096];
            int len;
            while ((len = fileInputStream.read(buffer)) != -1) {
                dos.write(buffer, 0, len);
            }
            dos.writeBytes(lineEnd);
        }
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }

    public static class DataPart {
        private final String fileName;
        private final byte[] content;
        private final String type;

        public DataPart(String fileName, byte[] content, String type) {
            this.fileName = fileName;
            this.content = content;
            this.type = type;
        }

        public String getFileName() {
            return fileName;
        }

        public byte[] getContent() {
            return content;
        }

        public String getType() {
            return type;
        }
    }

}
