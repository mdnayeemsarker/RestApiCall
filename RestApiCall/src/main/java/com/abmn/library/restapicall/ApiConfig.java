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

    public static String getMessage() {
        return Config.getBaseUrl();
    }

    public static void RequestToVolleyOld(Callback result, int method, Activity activity, String url, Map<String, String> params, boolean isProgress) {
        InternetConfig internetConfig = new InternetConfig(activity);

        if (!internetConfig.isConnected()) {
            return;
        }

//        if (ProgressDisplay.mProgressBar != null) {
//            ProgressDisplay.mProgressBar.setVisibility(View.GONE);
//        }
//        ProgressDisplay progressDisplay = new ProgressDisplay(activity);
//        if (isProgress) progressDisplay.showProgress();

        RequestQueue queue = Volley.newRequestQueue(activity);

        StringRequest stringRequest = new StringRequest(method, url, response -> {
//                    if (isProgress) progressDisplay.hideProgress();
//                    try {
//                        JSONObject jsonObject = new JSONObject(response);
//                        if (jsonObject.has(Constant.SUCCESS)) {
//                            JSONObject success = jsonObject.getJSONObject(Constant.SUCCESS);
//                            JSONObject data = success.optJSONObject(Constant.DATA);
//                            result.onResponse(true, String.valueOf(data), success.getString(Constant.MESSAGE));
//                        }
//                    } catch (JSONException e) {
//                        result.onResponse(false, null, "Error parsing server response.");
//                    }
        }, error -> {
//                    if (isProgress) progressDisplay.hideProgress();
            NetworkResponse networkResponse = error.networkResponse;
            if (networkResponse != null && networkResponse.data != null) {
                String errorResponse = new String(networkResponse.data);
                Log.d("ErrorAPI", errorResponse);
//                        try {
//                            JSONObject jsonObject = new JSONObject(errorResponse);
//                            if (jsonObject.has(Constant.ERROR)) {
//                                JSONObject errorObject = jsonObject.getJSONObject(Constant.ERROR);
//                                result.onResponse(true, String.valueOf(errorObject), errorObject.getString(Constant.MESSAGE));
//                            }
//                        } catch (JSONException e) {
//                            throw new RuntimeException(e);
//                        }
            } else {
                result.onResponse(false, "Unknown error occurred.", "");
            }
        }) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params1 = new HashMap<>();
                params1.put("Accept", "application/json");
                params1.put("Content-Type", "application/json");
                params1.put("X-Requested-With", "app");
                params1.put("x-api-key", "eyJpdiI6IkJxRkVvUzJQT2VVWVpiNldRbmxJV3c9PSIsInZhbHVlIjoidkpHcmJPOHVkOFlHR2ltM0JZNDR2dGdFWW5iVEZJYWwrOXN3aGtRYnJSeW44aXd0dFltRUg0OGZMMkllWk1ENCIsIm1hYyI6ImU4MjE4Y2UyZjRlYjNiYjhlOWZjODJhOTE4OWFhNzVmMzRkOTQyYzY4Njc2NzMyMjU5ODkxNzZkYTEyMmYxZDYiLCJ0YWciOiIifQ==");
//                String token = uConfig.getData(Constant.TOKEN);
//                if (token != null && !token.isEmpty()) {
//                    params1.put(Constant.AUTHORIZATION, Constant.BEARER + token);
//                } else {
//                    Log.w("RequestToVolley", "Authorization token is missing or invalid.");
//                }
                return params1;
            }

            @Override
            protected Map<String, String> getParams() {
                return params != null ? params : new HashMap<>();
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(stringRequest);
    }

    public void RequestToVolley(Callback result, int method, Activity activity, String url, Map<String, String> params, boolean isProgress) {
        InternetConfig internetConfig = new InternetConfig(activity);

        if (!internetConfig.isConnected()) {
            return;
        }

        RequestQueue queue = Volley.newRequestQueue(activity);

        StringRequest stringRequest = new StringRequest(method, url, response -> {
            result.onResponse(true, response, "");
        }, error -> {
            NetworkResponse networkResponse = error.networkResponse;
            if (networkResponse != null && networkResponse.data != null) {
                String errorResponse = new String(networkResponse.data);
                result.onResponse(false, errorResponse, "");
            } else {
                result.onResponse(false, error.toString(), "Unknown error occurred.");
            }
        }) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params1 = new HashMap<>();
                params1.put("Accept", "application/json");
                params1.put("Content-Type", "application/json");
                return params1;
            }

            @Override
            protected Map<String, String> getParams() {
                return params != null ? params : new HashMap<>();
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(stringRequest);
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
                if (Config.isOrganizeMode()) {
                    result.onResponse(true, String.valueOf(jsonObject), jsonObject.getString("message"));
                } else {
                    JSONObject success = jsonObject.getJSONObject("success");
                    result.onResponse(true, String.valueOf(success.getJSONObject("data")), jsonObject.getString("message"));
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
                    if (Config.isOrganizeMode()) {
                        result.onResponse(true, String.valueOf(jsonObject), jsonObject.getString("message"));
                    } else {
                        JSONObject success = jsonObject.getJSONObject("error");
                        result.onResponse(true, String.valueOf(success.getJSONObject("error")), jsonObject.getString("message"));
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
