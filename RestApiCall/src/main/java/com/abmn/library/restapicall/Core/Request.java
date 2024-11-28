package com.abmn.library.restapicall.Core;

public class Request {

    public static int GET() {
        return com.android.volley.Request.Method.GET; // GET request
    }
    public static int POST() {
        return com.android.volley.Request.Method.POST; // POST request
    }

    public static int PUT() {
        return com.android.volley.Request.Method.PUT; // PUT request
    }
    public static int DELETE() {
        return com.android.volley.Request.Method.DELETE; // PUT request
    }

}
