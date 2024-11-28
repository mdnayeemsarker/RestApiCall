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
import com.abmn.library.restapicall.Core.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

        Log.d("ApiConfigFirst", ApiConfig.getMessage());
    }

    public void clickToCheck(View view) {
        Log.d("ApiConfig", Config.getBaseUrl());

        apiConfig.makeRequest((result, response, message) -> {
            Log.d("ApiCallResult: ", String.valueOf(result));
            Log.d("ApiCallResponse: ", response);
            Log.d("ApiCallMessage: ", message);
        }, Request.GET(),"app-home", new HashMap<>(), true);
    }
}