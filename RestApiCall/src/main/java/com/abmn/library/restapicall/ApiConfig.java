package com.abmn.library.restapicall;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.abmn.library.restapicall.Core.Callback;
import com.abmn.library.restapicall.Core.Config;
import com.abmn.library.restapicall.Core.ProgressDisplay;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ApiConfig {

    Activity activity;

    public ApiConfig(Activity activity) {
        this.activity = activity;
    }

    public void stringRequest(Callback result, int method, String endpoint, Map<String, String> additionalParams, boolean isProgress) {
        if (ProgressDisplay.mProgressBar != null) {
            ProgressDisplay.mProgressBar.setVisibility(View.GONE);
        }
        ProgressDisplay progressDisplay = new ProgressDisplay(activity);
        if (isProgress) progressDisplay.showProgress();
        String url = Config.getBaseUrl() + endpoint;

        Map<String, String> params = new HashMap<>(Config.getParameters());
        if (additionalParams != null) {
            params.putAll(additionalParams);
        }
        RequestQueue queue = Volley.newRequestQueue(activity);
        StringRequest request = new StringRequest(method, url, response -> {
            progressDisplay.hideProgress();
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (Config.isFormatResponse()) {
                    if (Config.isOrganizeMode()) {
                        result.onResponse(true, String.valueOf(jsonObject), jsonObject.getString("message"), jsonObject.getString("keyVal"));
                    } else {
                        JSONObject success = jsonObject.getJSONObject("success");
                        result.onResponse(true, String.valueOf(success.getJSONObject("data")), success.getString("message"), "");
                    }
                } else {
                    result.onResponse(true, response, "UnFormatted Response", "");
                }
            } catch (Exception e) {
                if (Config.isDebugMode())
                    Log.d("ABMN_" + endpoint, Objects.requireNonNull(e.getMessage()));
                result.onResponse(false, "", "Response error Getting...", "");
            }
        }, error -> {
            progressDisplay.hideProgress();
            NetworkResponse networkResponse = error.networkResponse;
            if (networkResponse != null) {
                String errorData = new String(networkResponse.data);
                try {
                    JSONObject jsonObject = new JSONObject(errorData);
                    if (Config.isFormatResponse()) {
                        if (Config.isOrganizeMode()) {
                            result.onResponse(false, errorData, jsonObject.getString("message"), "");
                        } else {
                            JSONObject errorObj = jsonObject.getJSONObject("error");
                            result.onResponse(false, errorData, errorObj.getString("message"), "");
                        }
                    } else {
                        result.onResponse(false, errorData, "UnFormatted Response", "");
                    }
                } catch (Exception e) {
                    if (Config.isDebugMode())
                        Log.d("ABMN_" + endpoint, errorData + ", " + Objects.requireNonNull(e.getMessage()));
                    result.onResponse(false, errorData, "Error error Getting...", "");
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                if (Config.isDebugMode())
                    Log.d("ABMN_header_" + endpoint, Config.getHeaders().toString());
                return Config.getHeaders();
            }

            @Override
            protected Map<String, String> getParams() {
                if (Config.isDebugMode()) Log.d("ABMN_params_" + endpoint, params.toString());
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    public void jsonRequest(Callback result, int method, String endpoint, Map<String, String> additionalParams, boolean isProgress) {
        if (ProgressDisplay.mProgressBar != null) {
            ProgressDisplay.mProgressBar.setVisibility(View.GONE);
        }
        ProgressDisplay progressDisplay = new ProgressDisplay(activity);
        if (isProgress) progressDisplay.showProgress();

        String url = Config.getBaseUrl() + endpoint;

        JSONObject jsonParams = new JSONObject();
        if (additionalParams != null) {
            for (Map.Entry<String, String> entry : additionalParams.entrySet()) {
                try {
                    jsonParams.put(entry.getKey(), entry.getValue());
                } catch (Exception e) {
                    if (Config.isDebugMode())
                        Log.d("ABMN_addiParams" + endpoint, Objects.requireNonNull(e.getMessage()));
                }
            }
        }

        JsonObjectRequest request = new JsonObjectRequest(method, url, jsonParams, response -> {
            progressDisplay.hideProgress();
            try {
                JSONObject jsonObject = new JSONObject(response.toString());
                if (Config.isFormatResponse()) {
                    if (Config.isOrganizeMode()) {
                        result.onResponse(true, jsonObject.toString(), jsonObject.getString("message"), jsonObject.getString("keyVal"));
                    } else {
                        JSONObject success = jsonObject.getJSONObject("success");
                        result.onResponse(true, success.getJSONObject("data").toString(), success.getString("message"), "");
                    }
                } else {
                    result.onResponse(true, response.toString(), "UnFormatted Response", "");
                }
            } catch (Exception e) {
                if (Config.isDebugMode())
                    Log.d("ABMN_" + endpoint, Objects.requireNonNull(e.getMessage()));
                result.onResponse(false, "", "Response error Getting...", "");
            }
        }, error -> {
            progressDisplay.hideProgress();
            NetworkResponse networkResponse = error.networkResponse;
            if (networkResponse != null) {
                String errorData = new String(networkResponse.data);
                try {
                    JSONObject jsonObject = new JSONObject(errorData);
                    if (Config.isFormatResponse()) {
                        if (Config.isOrganizeMode()) {
                            result.onResponse(true, errorData, jsonObject.getString("message"), "");
                        } else {
                            JSONObject errorObj = jsonObject.getJSONObject("error");
                            result.onResponse(true, errorData, errorObj.getString("message"), "");
                        }
                    } else {
                        result.onResponse(true, errorData, "UnFormatted Response", "");
                    }
                } catch (Exception e) {
                    if (Config.isDebugMode())
                        Log.d("ABMN_" + endpoint, errorData + ", " + Objects.requireNonNull(e.getMessage()));
                    result.onResponse(false, errorData, "Error error Getting...", "");
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = Config.getHeaders();
                headers.put("Content-Type", "application/json"); // Set the correct content type
                if (Config.isDebugMode()) Log.d("ABMN_header", headers.toString());
                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(activity);
        queue.add(request);
    }
}
