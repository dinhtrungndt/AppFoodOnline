package com.example.appfoodv2.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.appfoodv2.R;
import com.example.appfoodv2.View.FragMent.FragMent_Bill;

public class XemthemActivity extends AppCompatActivity {
private TextView test1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xemthem);
        test1 = findViewById(R.id.test1);

        test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(XemthemActivity.this, ThongKeDanhMucActivity.class));
            }
        });
    }
}