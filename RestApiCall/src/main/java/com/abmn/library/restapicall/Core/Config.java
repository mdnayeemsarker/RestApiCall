package com.abmn.library.restapicall.Core;

import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Config {
    private static final Map<String, String> headers = new HashMap<>();
    private static final Map<String, String> parameters = new HashMap<>();
    private static String apiBaseUrl = "";
    private static boolean formatResMode = false;
    private static boolean organizeMode = false;
    private static boolean debugMode = false;
    private static int startColor = Color.parseColor("#A867F6"); // Default start color
    private static int centerColor = Color.parseColor("#B593DF"); // Default center color
    private static int endColor = Color.parseColor("#6D29BF"); // Default end color

    // Initialize the base configuration
    public static void init(String baseUrl) {
        apiBaseUrl = baseUrl;
    }
    // Getters for base fields
    public static String getBaseUrl() {
        return apiBaseUrl;
    }

    // set setDebug mode
    public static void setFormatResponseMode(boolean formatResponse) {
        formatResMode = formatResponse;
    }
    public static boolean isFormatResponse() {
        return formatResMode;
    }

    // set setDebug mode
    public static void setDebugMode(boolean debug) {
        debugMode = debug;
    }
    public static boolean isDebugMode() {
        return debugMode;
    }

    // set setDebug mode
    public static void setOrganizeResponseMode(boolean organize) {
        organizeMode = organize;
    }
    public static boolean isOrganizeMode() {
        return organizeMode;
    }

    // Add headers dynamically
    public static void addHeader(String key, String value) {
        headers.put(key, value);
    }

    // Remove headers dynamically
    public static void removeHeader(String key) {
        headers.remove(key);
    }
    // Return headers
    public static Map<String, String> getHeaders() {
        return headers;
    }

    // Add parameters dynamically
    public static void addParameter(String key, String value) {
        parameters.put(key, value);
    }
    // Remove parameters dynamically
    public static void removeParameter(String key) {
        parameters.remove(key);
    }
    // Return parameters
    public static Map<String, String> getParameters() {
        return parameters;
    }

    public static int getStartColor() {
        return startColor;
    }

    public static int getCenterColor() {
        return centerColor;
    }

    public static int getEndColor() {
        return endColor;
    }

    // Method to set progress bar colors
    public static void setProgressColors(int start, int center, int end) {
        startColor = start;
        centerColor = center;
        endColor = end;
    }

    // Check is Library properly connected or not.
    public static String getCheckConfig() {
        if (Objects.equals(Config.getBaseUrl(), ""))
            return "Not Set api Base URL";
        else
            return Config.getBaseUrl();
    }
}
