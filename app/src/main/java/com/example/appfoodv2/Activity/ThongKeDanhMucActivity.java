package com.example.appfoodv2.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfoodv2.Activity.Bill.CartActivity;
import com.example.appfoodv2.Activity.FragMent.FragMent_Bill;
import com.example.appfoodv2.Activity.FragMent.FragMent_Home;
import com.example.appfoodv2.Activity.FragMent.FragMent_ProFile;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.example.appfoodv2.Adapter.SanPhamAdapter;
import com.example.appfoodv2.Model.SanPhamModels;
import com.example.appfoodv2.Model.SanPhamPreSenter;
import com.example.appfoodv2.Interface.SanPhamView;
import com.example.appfoodv2.R;

import java.util.ArrayList;
import java.util.List;

public class ThongKeDanhMucActivity extends AppCompatActivity implements SanPhamView {
    private Spinner spinerthongke;
    private FirebaseFirestore db;
    private Toolbar toolbar;
    private List<String> list;
    private FloatingActionButton fab;
    private ArrayList<SanPhamModels> arrayList;
    private SanPhamPreSenter sanPhamPreSenter;
    private RecyclerView rCvSP;
    private SanPhamAdapter sanPhamAdapter;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongke);
        Anhxa();
        onClick();
        Init();
    }

    private void Init() {
        Intent intent = getIntent();
        sanPhamPreSenter = new SanPhamPreSenter(this);
        arrayList = new ArrayList<>();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Back");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        list = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        list.add("Chọn Danh Mục");
        db.collection("LoaiSP").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot q : queryDocumentSnapshots) {
                    list.add(q.getString("tenloai"));
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(ThongKeDanhMucActivity.this, android.R.layout.simple_list_item_1, list);
                spinerthongke.setAdapter(arrayAdapter);

            }
        });

        if (intent.hasExtra("KEY")) {
            arrayList.clear();
            if (sanPhamAdapter != null) {
                sanPhamAdapter.notifyDataSetChanged();
            }
            String key = intent.getStringExtra("KEY");
            sanPhamPreSenter.HandlegetDataSanPham(key, 2);
        }
        spinerthongke.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    arrayList.clear();
                    if (sanPhamAdapter != null) {
                        sanPhamAdapter.notifyDataSetChanged();
                    }
                    sanPhamPreSenter.HandlegetDataSanPham(spinerthongke.getSelectedItem().toString(), 1);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void Anhxa() {
        spinerthongke = findViewById(R.id.spinner);
        toolbar = findViewById(R.id.toolbar);
        rCvSP = findViewById(R.id.rcvDanhMuc);
        fab = findViewById(R.id.fab);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);


    }

    private void onClick() {
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Toast.makeText(ThongKeDanhMucActivity.this, "Vui lòng nhấn back ở trên !", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ThongKeDanhMucActivity.this, "Vui lòng nhấn back ở trên !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void getDataSanPham(String id, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong, String hansudung, Long type, String trongluong) {
        arrayList.add(new SanPhamModels(id, tensp, giatien, hinhanh, loaisp, mota, soluong, hansudung, type, trongluong));
        sanPhamAdapter = new SanPhamAdapter(this, arrayList, 1);
        rCvSP.setLayoutManager(new LinearLayoutManager(this));
        rCvSP.setAdapter(sanPhamAdapter);


    }

    @Override
    public void getDataSanPhamTU(String id, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong, String hansudung, Long type, String trongluong) {
        arrayList.add(new SanPhamModels(id, tensp, giatien, hinhanh, loaisp, mota, soluong, hansudung, type, trongluong));
        sanPhamAdapter = new SanPhamAdapter(this, arrayList, 3);
        rCvSP.setLayoutManager(new LinearLayoutManager(this));
        rCvSP.setAdapter(sanPhamAdapter);


    }

    @Override
    public void getDataSanPhamHQ(String id, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong, String hansudung, Long type, String trongluong) {
        arrayList.add(new SanPhamModels(id, tensp, giatien, hinhanh, loaisp, mota, soluong, hansudung, type, trongluong));
        sanPhamAdapter = new SanPhamAdapter(this, arrayList, 4);
        rCvSP.setLayoutManager(new LinearLayoutManager(this));
        rCvSP.setAdapter(sanPhamAdapter);


    }

    @Override
    public void getDataSanPhamMC(String id, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong, String hansudung, Long type, String trongluong) {
        arrayList.add(new SanPhamModels(id, tensp, giatien, hinhanh, loaisp, mota, soluong, hansudung, type, trongluong));
        sanPhamAdapter = new SanPhamAdapter(this, arrayList, 5);
        rCvSP.setLayoutManager(new LinearLayoutManager(this));
        rCvSP.setAdapter(sanPhamAdapter);


    }

    @Override
    public void getDataSanPhamYT(String id, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong, String hansudung, Long type, String trongluong) {
        arrayList.add(new SanPhamModels(id, tensp, giatien, hinhanh, loaisp, mota, soluong, hansudung, type, trongluong));
        sanPhamAdapter = new SanPhamAdapter(this, arrayList, 6);
        rCvSP.setLayoutManager(new LinearLayoutManager(this));
        rCvSP.setAdapter(sanPhamAdapter);


    }

    @Override
    public void getDataSanPhamLau(String id, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong, String hansudung, Long type, String trongluong) {
        arrayList.add(new SanPhamModels(id, tensp, giatien, hinhanh, loaisp, mota, soluong, hansudung, type, trongluong));
        sanPhamAdapter = new SanPhamAdapter(this, arrayList, 7);
        rCvSP.setLayoutManager(new LinearLayoutManager(this));
        rCvSP.setAdapter(sanPhamAdapter);


    }

    @Override
    public void getDataSanPhamGY(String id, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong, String hansudung, Long type, String trongluong) {
        arrayList.add(new SanPhamModels(id, tensp, giatien, hinhanh, loaisp, mota, soluong, hansudung, type, trongluong));
        sanPhamAdapter = new SanPhamAdapter(this, arrayList, 8);
        rCvSP.setLayoutManager(new LinearLayoutManager(this));
        rCvSP.setAdapter(sanPhamAdapter);


    }

    @Override
    public void OnEmptyList() {
        Toast.makeText(this, "Không tìm thấy sản phẩm nào trong danh mục : " + spinerthongke.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getDataSanPhamNB(String id, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong, String hansudung, Long type, String trongluong) {

    }

}
