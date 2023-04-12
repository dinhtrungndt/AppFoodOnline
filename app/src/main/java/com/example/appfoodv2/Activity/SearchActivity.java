package com.example.appfoodv2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appfoodv2.Adapter.SanPhamAdapter;
import com.example.appfoodv2.Model.SanPhamModels;
import com.example.appfoodv2.Model.SanPhamPreSenter;
import com.example.appfoodv2.R;

import java.util.ArrayList;
import java.util.List;
public class SearchActivity extends AppCompatActivity {

    private ListView listView;
    private List<String> resultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Lấy query từ Intent
        Intent intent = getIntent();
        String query = intent.getStringExtra("query");

        // Thực hiện tìm kiếm
        resultList = performSearch(query);

        // Hiển thị kết quả tìm kiếm
        listView = findViewById(R.id.list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, resultList);
        listView.setAdapter(adapter);
    }

    // Thực hiện tìm kiếm và trả về danh sách kết quả
    private List<String> performSearch(String query) {
        // Thực hiện tìm kiếm ở đây
        // Trả về danh sách kết quả
        List<String> resultList = new ArrayList<>();
        resultList.add("Kết quả 1");
        resultList.add("Kết quả 2");
        resultList.add("Kết quả 3");
        return resultList;
    }
}
