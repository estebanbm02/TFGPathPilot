package com.example.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }, 3000);
    }

    public static MainActivity getInstance(){
        return instance;
    }
}
