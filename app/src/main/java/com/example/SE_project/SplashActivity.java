package com.example.SE_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.SE_project.Login.MainActivity3;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            //Thread.sleep(1000); //대기 초 설정
            startActivity(new Intent(SplashActivity.this, MainActivity3.class));
            finish();
        } catch (Exception e) {
            Log.e("Error", "SplashActivity ERROR", e);
        }
    }
}
