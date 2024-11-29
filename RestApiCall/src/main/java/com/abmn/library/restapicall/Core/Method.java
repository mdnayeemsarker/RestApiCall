package com.abmn.library.restapicall.Core;

import com.android.volley.Request;

public class Method {

    public static int GET() {
        return Request.Method.GET; // GET request
    }
    public static int POST() {
        return Request.Method.POST; // POST request
    }

    public static int PUT() {
        return Request.Method.PUT; // PUT request
    }
    public static int DELETE() {
        return Request.Method.DELETE; // PUT request
    }
    public static int HEAD() {
        return Request.Method.HEAD; // HEAD request
    }
    public static int OPTIONS() {
        return Request.Method.OPTIONS; // OPTIONS request
    }
    public static int TRACE() {
        return Request.Method.TRACE; // TRACE request
    }
    public static int PATCH() {
        return Request.Method.PATCH; // PATCH request
    }
}
