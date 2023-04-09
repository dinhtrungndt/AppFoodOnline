package com.example.appfoodv2.Activity.FragMent;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.LinearLayout;
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
import com.example.appfoodv2.Activity.Bill.CartActivity;
import com.example.appfoodv2.Activity.ContactActivity;
import com.example.appfoodv2.Activity.ThongKeDanhMucActivity;
import com.example.appfoodv2.Adapter.HoaDonAdapter;
import com.example.appfoodv2.Model.HoaDonModels;
import com.example.appfoodv2.Model.HoaDonPreSenter;
import com.example.appfoodv2.Interface.HoaDonView;
import com.example.appfoodv2.R;
import com.example.appfoodv2.Activity.HomeActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FragMent_Bill extends Fragment implements HoaDonView {
    View view;
    private RecyclerView rcvBill;
    private HoaDonPreSenter hoaDonPreSenter;
    private HoaDonAdapter hoaDonAdapter;
    private ArrayList<HoaDonModels> arrayList;
    private ProgressBar progressBar;
    private FloatingActionButton fab;
    private BottomNavigationView bottomNavigationView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bill, container, false);


        rcvBill = view.findViewById(R.id.rcvBill);
        progressBar = view.findViewById(R.id.progressbar);
        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        fab = view.findViewById(R.id.fab);
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
                            return true;
                        }
                        if (item.getItemId() == R.id.profile) {
                            FragMent_ProFile newFragment = new FragMent_ProFile();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.framelayout, newFragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            return true;
                        }
                        if (item.getItemId() == R.id.menu) {
                            startActivity(new Intent(getContext(), ThongKeDanhMucActivity.class));
                            return true;
                        }

                        return false;
                    }
                });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        return view;
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_menu);

        LinearLayout lnGioHang = dialog.findViewById(R.id.lnGioHang);
        LinearLayout lnDonHang = dialog.findViewById(R.id.lnDonHang);
        LinearLayout lnThongTin = dialog.findViewById(R.id.lnThongTin);
        LinearLayout lnLienHe = dialog.findViewById(R.id.lnLienHe);
        LinearLayout lnShare = dialog.findViewById(R.id.lnShare);

        lnGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CartActivity.class));
                dialog.dismiss();
            }
        });
        lnDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Bạn đang ở đơn hàng !", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        lnThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragMent_ProFile newFragment = new FragMent_ProFile();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                dialog.dismiss();
            }
        });
        lnLienHe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ContactActivity.class));
            }
        });

        lnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showShare();
                Toast.makeText(getContext(), "Đã share thành công trên mạng !", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void showShare() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_share);

        WebView gifWebView = dialog.findViewById(R.id.gifWebView);
        gifWebView.getSettings().setLoadWithOverviewMode(true);
        gifWebView.getSettings().setUseWideViewPort(true);
        gifWebView.loadUrl("https://www.google.com/url?sa=i&url=https%3A%2F%2Fthtantai2.edu.vn%2Fanh-gif-cute%2F&psig=AOvVaw3FmkKWxZiUyNaiC_ON3dvM&ust=1681066453368000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCJCN49j6mv4CFQAAAAAdAAAAABAO");

        dialog.show();
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
