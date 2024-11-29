package com.abmn.restapicall;

import android.app.Application;
import android.graphics.Color;

import com.abmn.library.restapicall.Core.Config;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize base configuration
        String API_BASE_URL = "https://your-domain.com"; // Adjust your api base url
        boolean IS_DEBUG_MODE = true; // set true if you want to get debug log
        boolean IS_FORMAT_RESPONSE = true; // set true if you want to Json Formated response see more for formated response example
        boolean IS_ORGANIZE_RESPONSE = true; // set true for organize response see more for organize response example

        // Api base url initialization
        Config.init(API_BASE_URL); // you mast need init otherwise base url not connected with library

        Config.setDebugMode(IS_DEBUG_MODE);
        Config.setFormatResponseMode(IS_FORMAT_RESPONSE);
        Config.setOrganizeResponseMode(IS_ORGANIZE_RESPONSE);

        // Add dynamic headers
        Config.addHeader("Accept", "application/json"); // optional
        Config.addHeader("Content-Type", "application/x-www-form-urlencoded"); // need for string request
        Config.addHeader("Content-Type", "application/json"); // need for json request
        Config.addHeader("Authorization", "Bearer " + "TOKEN"); // optional
        Config.addHeader("X-Requested-With", "app"); // optional
        Config.addHeader("x-api-key", "app"); // optional

        // Add dynamic parameters
        Config.addParameter("lang", "en"); // optional
        Config.addParameter("timezone", "GMT+6"); // optional

        // Set progress bar colors globally
        Config.setProgressColors(
                Color.parseColor("#FF5722"), // Start color
                Color.parseColor("#FFC107"), // Center color
                Color.parseColor("#4CAF50")  // End color
        );
    }
}