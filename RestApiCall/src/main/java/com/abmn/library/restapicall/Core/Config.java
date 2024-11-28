package com.abmn.library.restapicall.Core;

import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;

public class Config {
    private static final Map<String, String> headers = new HashMap<>();
    private static final Map<String, String> parameters = new HashMap<>();
    private static String apiBaseUrl;
    private static String apiKey;
    private static boolean organizeMode;
    private static boolean debugMode;
    private static int startColor = Color.parseColor("#A867F6"); // Default start color
    private static int centerColor = Color.parseColor("#B593DF"); // Default center color
    private static int endColor = Color.parseColor("#6D29BF"); // Default end color

    // Getter methods for progress bar colors
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

    // Initialize the base configuration
    public static void init(String baseUrl, String key, boolean organize, boolean debug) {
        apiBaseUrl = baseUrl;
        apiKey = key;
        organizeMode = organize;
        debugMode = debug;
    }

    // Getters for base fields
    public static String getBaseUrl() {
        return apiBaseUrl;
    }

    public static String getApiKey() {
        return apiKey;
    }

    public static boolean isDebugMode() {
        return debugMode;
    }

    public static boolean isOrganizeMode() {
        return organizeMode;
    }

    public static String getApiBaseUrl() {
        return apiBaseUrl;
    }

    // Add headers dynamically
    public static void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public static void removeHeader(String key) {
        headers.remove(key);
    }

    public static Map<String, String> getHeaders() {
        return headers;
    }

    // Add parameters dynamically
    public static void addParameter(String key, String value) {
        parameters.put(key, value);
    }

    public static void removeParameter(String key) {
        parameters.remove(key);
    }

    public static Map<String, String> getParameters() {
        return parameters;
    }

}
