package com.abmn.restapicall;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.abmn.library.restapicall.ApiConfig;
import com.abmn.library.restapicall.Core.Config;
import com.abmn.library.restapicall.Core.Method;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ApiConfig apiConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        apiConfig = new ApiConfig(this);

        Log.d("checkConfiguration", Config.getCheckConfig());
        Log.d("debugMode", "" + Config.isDebugMode());
        Log.d("formatResponseMode", "" + Config.isFormatResponse());
        Log.d("organizeResponseMode", "" + Config.isOrganizeMode());
    }

    public void clickToCheck(View view) {
        apiConfig.stringRequest((result, response, message, key) -> {
            Log.d("ApiCallResult: ", String.valueOf(result));
            Log.d("ApiCallResponse: ", response);
            Log.d("ApiCallMessage: ", message);
            Log.d("ApiCallKey: ", key);
        }, Method.GET(),"user", new HashMap<>(), true);

        HashMap<String, String> postParams = new HashMap<>();
        postParams.put("name", "MD NAYEEM SARKER");
        postParams.put("email", "dev.ab.nayeem@gmail.com");
        apiConfig.jsonRequest((result, response, message, key) -> {
            Log.d("ApiCallResult: ", String.valueOf(result));
            Log.d("ApiCallResponse: ", response);
            Log.d("ApiCallMessage: ", message);
            Log.d("ApiCallKey: ", key);
        }, Method.POST(),"update-user", postParams, true);

        HashMap<String, String> putParams = new HashMap<>();
        putParams.put("age", "27");
        apiConfig.jsonRequest((result, response, message, key) -> {
            Log.d("ApiCallResult: ", String.valueOf(result));
            Log.d("ApiCallResponse: ", response);
            Log.d("ApiCallMessage: ", message);
            Log.d("ApiCallKey: ", key);
        }, Method.PUT(),"update-user", putParams, true);

        apiConfig.jsonRequest((result, response, message, key) -> {
            Log.d("ApiCallResult: ", String.valueOf(result));
            Log.d("ApiCallResponse: ", response);
            Log.d("ApiCallMessage: ", message);
            Log.d("ApiCallKey: ", key);
        }, Method.DELETE(),"delete", new HashMap<>(), true);

        HashMap<String, String> patchParams = new HashMap<>();
        patchParams.put("age", "27");
        apiConfig.jsonRequest((result, response, message, key) -> {
            Log.d("ApiCallResult: ", String.valueOf(result));
            Log.d("ApiCallResponse: ", response);
            Log.d("ApiCallMessage: ", message);
            Log.d("ApiCallKey: ", key);
        }, Method.PATCH(),"delete", patchParams, true);
    }
}