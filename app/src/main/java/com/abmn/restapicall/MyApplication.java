package com.abmn.restapicall;

import android.app.Application;
import android.graphics.Color;

import com.abmn.library.restapicall.Core.Config;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize base configuration
        String API_BASE_URL = "https://juktoshop.com/api/v1/";
        String API_KEY = "RELEASE_API_KEY";
        boolean IS_ORGANIZE = true;
        boolean DEBUG_MODE = true;
        Config.init(API_BASE_URL, API_KEY, IS_ORGANIZE, DEBUG_MODE);

        // Add dynamic headers
        Config.addHeader("Content-Type", "application/json");
        Config.addHeader("Accept", "application/json");
        Config.addHeader("Authorization", "Bearer " + API_KEY);
        Config.addHeader("X-Requested-With", "app");
        Config.addHeader("x-api-key", "eyJpdiI6IkJxRkVvUzJQT2VVWVpiNldRbmxJV3c9PSIsInZhbHVlIjoidkpHcmJPOHVkOFlHR2ltM0JZNDR2dGdFWW5iVEZJYWwrOXN3aGtRYnJSeW44aXd0dFltRUg0OGZMMkllWk1ENCIsIm1hYyI6ImU4MjE4Y2UyZjRlYjNiYjhlOWZjODJhOTE4OWFhNzVmMzRkOTQyYzY4Njc2NzMyMjU5ODkxNzZkYTEyMmYxZDYiLCJ0YWciOiIifQ==");

        // Add dynamic parameters
//        Config.addParameter("lang", "en");
//        Config.addParameter("timezone", "GMT+6");


        // Set progress bar colors globally
        Config.setProgressColors(
                Color.parseColor("#FF5722"), // Start color
                Color.parseColor("#FFC107"), // Center color
                Color.parseColor("#4CAF50")  // End color
        );

    }
}