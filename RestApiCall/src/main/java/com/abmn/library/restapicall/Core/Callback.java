package com.abmn.library.restapicall.Core;

public interface Callback {
    void onResponse(boolean result, String response, String message, String keyVal);
}
