package com.abmn.library.restapicall;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.abmn.library.restapicall.Core.Callback;
import com.abmn.library.restapicall.Core.Config;
import com.abmn.library.restapicall.Core.InternetConfig;
import com.abmn.library.restapicall.Core.ProgressDisplay;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
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

    public void makeRequest(Callback result, int method, String endpoint, Map<String, String> additionalParams, boolean isProgress) {
        if (ProgressDisplay.mProgressBar != null) {
            ProgressDisplay.mProgressBar.setVisibility(View.GONE);
        }
        ProgressDisplay progressDisplay = new ProgressDisplay(activity);
        if (isProgress)
            progressDisplay.showProgress();
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
                if (Config.isFormatRes()) {
                    if (Config.isOrganizeMode()) {
                        result.onResponse(true, String.valueOf(jsonObject), jsonObject.getString("message"));
                    } else {
                        JSONObject success = jsonObject.getJSONObject("success");
                        result.onResponse(true, String.valueOf(success.getJSONObject("data")), jsonObject.getString("message"));
                    }
                }else {
                    result.onResponse(true, response, "UnFormatted Response");
                }
            } catch (Exception e) {
                if (Config.isDebugMode())
                    Log.d("ABMN_" + endpoint, Objects.requireNonNull(e.getMessage()));
                result.onResponse(false, "", "Error Getting...");
            }
        }, error -> {
            progressDisplay.hideProgress();
            NetworkResponse networkResponse = error.networkResponse;
            if (networkResponse != null) {
                String errorData = new String(networkResponse.data);
                try {
                    JSONObject jsonObject = new JSONObject(errorData);
                    if (Config.isFormatRes()) {
                        if (Config.isOrganizeMode()) {
                            result.onResponse(true, String.valueOf(jsonObject), jsonObject.getString("message"));
                        } else {
                            JSONObject success = jsonObject.getJSONObject("error");
                            result.onResponse(true, String.valueOf(success.getJSONObject("error")), jsonObject.getString("message"));
                        }
                    }else {
                        result.onResponse(true, errorData, "UnFormatted Response");
                    }
                } catch (Exception e) {
                    if (Config.isDebugMode())
                        Log.d("ABMN_" + endpoint, Objects.requireNonNull(e.getMessage()));
                    result.onResponse(false, "", "Error Getting...");
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return Config.getHeaders();
            }

            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }
}
