package com.example.custom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.badge.BadgeUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BadgeUtil.getInstance().showBadge(this, 12);
    }

}
