package com.example.appfoodv2.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.appfoodv2.R;

public class XemthemActivity extends AppCompatActivity {
private LinearLayout test1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xemthem);
        test1 = findViewById(R.id.SupportCourse);

        test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(XemthemActivity.this, ThongKeDanhMucActivity.class));
            }
        });
    }
}