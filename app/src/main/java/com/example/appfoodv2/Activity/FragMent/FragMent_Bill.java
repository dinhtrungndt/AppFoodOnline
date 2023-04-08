package com.example.appfoodv2.Activity.FragMent;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfoodv2.Activity.Admin.ChatActivity;
import com.example.appfoodv2.Activity.ThongKeDanhMucActivity;
import com.example.appfoodv2.Adapter.HoaDonAdapter;
import com.example.appfoodv2.Model.HoaDonModels;
import com.example.appfoodv2.Model.HoaDonPreSenter;
import com.example.appfoodv2.Interface.HoaDonView;
import com.example.appfoodv2.R;
import com.example.appfoodv2.Activity.HomeActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class FragMent_Bill extends Fragment implements HoaDonView {
    View view;
    private RecyclerView rcvBill;
    private HoaDonPreSenter hoaDonPreSenter;
    private HoaDonAdapter hoaDonAdapter;
    private ArrayList<HoaDonModels> arrayList;
    private ProgressBar progressBar;
    private BottomNavigationView bottomNavigationView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bill, container, false);


        rcvBill = view.findViewById(R.id.rcvBill);
        progressBar = view.findViewById(R.id.progressbar);
        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        hoaDonPreSenter = new HoaDonPreSenter(this);
        arrayList = new ArrayList<>();

        hoaDonPreSenter.HandleReadDataHD();
        HomeActivity.countDownTimer = new CountDownTimer(1, 1) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                arrayList.clear();
                ;
                if (hoaDonAdapter != null) {
                    hoaDonAdapter.notifyDataSetChanged();
                }
                hoaDonPreSenter.HandleReadDataHD();
                HomeActivity.countDownTimer.cancel();

            }
        };

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        if (item.getItemId() == R.id.home) {
                            FragMent_Home newFragment = new FragMent_Home();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.framelayout, newFragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            Toast.makeText(getContext(), "Trang Chủ !!!", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        if (item.getItemId() == R.id.chat) {
                            Fragment_Message newFragment = new Fragment_Message();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.framelayout, newFragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            Toast.makeText(getContext(), "Chat !!!", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        if (item.getItemId() == R.id.profile) {
                            FragMent_ProFile newFragment = new FragMent_ProFile();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.framelayout, newFragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            Toast.makeText(getContext(), "Cập nhập thông tin !!!", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        if (item.getItemId() == R.id.menu) {
                            startActivity(new Intent(getContext(), ThongKeDanhMucActivity.class));
                            Toast.makeText(getContext(), "Danh mục !!!", Toast.LENGTH_SHORT).show();
                            return true;
                        }

                        return false;
                    }
                });
        return view;
    }

    @Override
    public void getDataHD(String id, String uid, String diachi, String hoten, String ngaydat, String phuongthuc, String sdt, Long tongtien, Long type) {
        arrayList.add(new HoaDonModels(id, uid, diachi, hoten, ngaydat, phuongthuc, sdt, tongtien, type));
        hoaDonAdapter = new HoaDonAdapter(getContext(), arrayList);
        rcvBill.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvBill.setAdapter(hoaDonAdapter);
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void OnFail() {

    }

    @Override
    public void OnSucess() {

    }
}
