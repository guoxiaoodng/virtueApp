package com.gxd.woodfish;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        AppCompatImageView ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> finish());
    }
}
