package com.example.appfoodv2.Activity.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.appfoodv2.R;

public class HomeAdminActivity extends AppCompatActivity {
    CardView cvHoaDon, cvThongKe, cvSignOut, cvSanpham;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        cvHoaDon = findViewById(R.id.cvHoaDon);
        cvThongKe = findViewById(R.id.cvThongKe);
        cvSignOut = findViewById(R.id.cvSignOut);
        cvSanpham = findViewById(R.id.cvSanpham);

        cvHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeAdminActivity.this, BillADMiNActivity.class);
                startActivity(intent);
            }
        });
        cvThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeAdminActivity.this, ChartBillActivity.class);
                startActivity(intent);
            }
        });
        cvSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeAdminActivity.this, SignInAdminActivity.class);
                startActivity(intent);
                finish();
            }
        });
        cvSanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeAdminActivity.this, ProductActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cvHoaDon:
                startActivity(new Intent(HomeAdminActivity.this, BillADMiNActivity.class));
                break;

            case R.id.cvThongKe:
                startActivity(new Intent(HomeAdminActivity.this, ChartBillActivity.class));
                break;
            case R.id.cvSignOut:
                finish();
                break;
            case R.id.cvSanpham:
                startActivity(new Intent(HomeAdminActivity.this, ProductActivity.class));
                break;

        }
    }
}
