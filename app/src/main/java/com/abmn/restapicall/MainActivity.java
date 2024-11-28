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

        Log.d("ApiConfigFirst", Config.getMessage());
    }

    public void clickToCheck(View view) {
        Log.d("ApiConfig", Config.getBaseUrl());

        apiConfig.makeRequest((result, response, message, key) -> {
            Log.d("ApiCallResult: ", String.valueOf(result));
            Log.d("ApiCallResponse: ", response);
            Log.d("ApiCallMessage: ", message);
            Log.d("ApiCallKey: ", key);
        }, Request.GET(),"app-home", new HashMap<>(), true);
    }
}