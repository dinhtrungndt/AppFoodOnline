package com.example.appfoodv2.Activity.FragMent;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.appfoodv2.Activity.Bill.CartActivity;
import com.example.appfoodv2.Activity.ContactActivity;
import com.example.appfoodv2.Activity.SearchActivity;
import com.example.appfoodv2.Activity.ThongKeDanhMucActivity;
import com.example.appfoodv2.Activity.XemthemActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.example.appfoodv2.Adapter.BannerAdapter;
import com.example.appfoodv2.Adapter.SanPhamAdapter;
import com.example.appfoodv2.Model.SanPhamModels;
import com.example.appfoodv2.Model.SanPhamPreSenter;
import com.example.appfoodv2.Interface.SanPhamView;
import com.example.appfoodv2.R;

import java.util.ArrayList;

public class FragMent_Home extends Fragment implements SanPhamView {
    View view;
    private ArrayList<String> arrayList;
    private ViewPager viewPager;
    private FirebaseFirestore db;
    private BannerAdapter bannerAdapter;
    private SanPhamPreSenter sanPhamPreSenter;
    private ArrayList<SanPhamModels> arr_sp, arr_sp_nb, arr_sp_tu, arr_sp_hq, arr_sp_mc, arr_sp_yt, arr_sp_lau, arr_sp_gy;
    private SanPhamAdapter sanPhamAdapter, sanPhamNBAdapter, sanPhamTUAdapter, sanPhamHQAdapter, sanPhamMCAdapter, sanPhamYTAdapter, sanPhamLauAdapter, sanPhamGYAdapter;
    private RecyclerView rcvSP, rcvSpNoiBat, rcvSPThucUong, rcvSPHQ, rcvSPMC, rcvSPYT, rcvSPLau, rcvSPGY;
    private ImageButton imgBtnDanhMuc;
    private ImageView btn_category_home;
    private TextView category_text;
    private FloatingActionButton fab;
    private BottomNavigationView bottomNavigationView;

    FragMent_HomeListener activityCallback;

    public interface FragMent_HomeListener {
        void onButtonClick();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            activityCallback = (FragMent_HomeListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " You must implement FirstFragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        Anhxa();
        onClick();
        Init();
        InitSanPham();

        imgBtnDanhMuc.setOnClickListener(view -> {
            activityCallback.onButtonClick();
        });

        return view;
    }

    private void InitSanPham() {
        arr_sp = new ArrayList<>();
        arr_sp_nb = new ArrayList<>();
        arr_sp_tu = new ArrayList<>();
        arr_sp_hq = new ArrayList<>();
        arr_sp_mc = new ArrayList<>();
        arr_sp_yt = new ArrayList<>();
        arr_sp_lau = new ArrayList<>();
        arr_sp_gy = new ArrayList<>();
        sanPhamPreSenter = new SanPhamPreSenter(this);
        sanPhamPreSenter.HandlegetDataSanPham();
        sanPhamPreSenter.HandlegetDataSanPhamNB();
        sanPhamPreSenter.HandlegetDataSanPhamTU();
        sanPhamPreSenter.HandlegetDataSanPhamHQ();
        sanPhamPreSenter.HandlegetDataSanPhamMC();
        sanPhamPreSenter.HandlegetDataSanPhamYT();
        sanPhamPreSenter.HandlegetDataSanPhamLau();
        sanPhamPreSenter.HandlegetDataSanPhamGY();
    }

    ///Tạo banner
    private void Init() {
        arrayList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        db.collection("Banner").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot d : queryDocumentSnapshots) {
                    arrayList.add(d.getString("hinhanh"));
                }
                bannerAdapter = new BannerAdapter(getContext(), arrayList);
                viewPager.setAdapter(bannerAdapter);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    //3s sang 1 banner khác
                    public void run() {
                        int k = viewPager.getCurrentItem();
                        if (k >= arrayList.size() - 1) {
                            k = 0;
                        } else {
                            k++;
                        }
                        handler.postDelayed(this, 3000);
                        viewPager.setCurrentItem(k, true);

                    }
                }, 3000);

            }
        });

    }

    private void Anhxa() {
        viewPager = view.findViewById(R.id.viewpager);
        rcvSP = view.findViewById(R.id.rcvSP);
        rcvSpNoiBat = view.findViewById(R.id.rcvNB);
        fab = view.findViewById(R.id.fab);
        rcvSPThucUong = view.findViewById(R.id.rcvTU);
        rcvSPHQ = view.findViewById(R.id.rcvHQ);
        rcvSPMC = view.findViewById(R.id.rcvMC);
        rcvSPYT = view.findViewById(R.id.rcvYT);
        rcvSPLau = view.findViewById(R.id.rcvLau);
        rcvSPGY = view.findViewById(R.id.rcvGY);
        imgBtnDanhMuc = view.findViewById(R.id.home_danhmuc);
        btn_category_home = view.findViewById(R.id.btn_category_home);
        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        category_text = view.findViewById(R.id.category_text);

    }

    private void onClick() {
        NestedScrollView nestedScrollView = view.findViewById(R.id.NestedScrollView);
        BottomAppBar bottomAppBar = view.findViewById(R.id.bottomAppBar);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY < oldScrollY) {
                    // Scroll Up
                    bottomAppBar.setElevation(0);
                } else if (scrollY > oldScrollY) {
                    // Scroll Down
                    bottomAppBar.setElevation(getResources().getDimensionPixelSize(R.dimen.bottom_app_bar_elevation));
                }
            }
        });

        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Xử lý tìm kiếm ở đây
                Intent intent = new Intent(getContext(), SearchActivity.class);
                intent.putExtra("query", query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        btn_category_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Bạn đang bên Trang chủ !!!", Toast.LENGTH_SHORT).show();
            }
        });

        category_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), XemthemActivity.class));
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        if (item.getItemId() == R.id.home) {
                            Toast.makeText(getContext(), "Bạn đang ở Trang Chủ !!!", Toast.LENGTH_SHORT).show();
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

    }

    @Override
    public void getDataSanPham(String id, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong,
                               String nhasanxuat, Long type, String trongluong) {
        arr_sp.add(new SanPhamModels(id, tensp, giatien, hinhanh, loaisp, mota, soluong, nhasanxuat, type, trongluong));
        sanPhamAdapter = new SanPhamAdapter(getContext(), arr_sp);
        rcvSP.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rcvSP.setAdapter(sanPhamAdapter);

    }

    @Override
    public void OnEmptyList() {

    }

    @Override
    public void getDataSanPhamNB(String id, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong, String nhasanxuat, Long type, String trongluong) {
        arr_sp_nb.add(new SanPhamModels(id, tensp, giatien, hinhanh, loaisp, mota, soluong, nhasanxuat, type, trongluong));
        sanPhamNBAdapter = new SanPhamAdapter(getContext(), arr_sp_nb, 2);
        rcvSpNoiBat.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rcvSpNoiBat.setAdapter(sanPhamNBAdapter);
    }

    @Override
    public void getDataSanPhamTU(String id, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong, String nhasanxuat, Long type, String trongluong) {
        arr_sp_tu.add(new SanPhamModels(id, tensp, giatien, hinhanh, loaisp, mota, soluong, nhasanxuat, type, trongluong));
        sanPhamTUAdapter = new SanPhamAdapter(getContext(), arr_sp_tu, 3);
        rcvSPThucUong.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rcvSPThucUong.setAdapter(sanPhamTUAdapter);
    }

    @Override
    public void getDataSanPhamHQ(String id, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong, String nhasanxuat, Long type, String trongluong) {
        arr_sp_hq.add(new SanPhamModels(id, tensp, giatien, hinhanh, loaisp, mota, soluong, nhasanxuat, type, trongluong));
        sanPhamHQAdapter = new SanPhamAdapter(getContext(), arr_sp_hq, 4);
        rcvSPHQ.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rcvSPHQ.setAdapter(sanPhamHQAdapter);
    }

    @Override
    public void getDataSanPhamMC(String id, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong, String nhasanxuat, Long type, String trongluong) {
        arr_sp_mc.add(new SanPhamModels(id, tensp, giatien, hinhanh, loaisp, mota, soluong, nhasanxuat, type, trongluong));
        sanPhamMCAdapter = new SanPhamAdapter(getContext(), arr_sp_mc, 5);
        rcvSPMC.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rcvSPMC.setAdapter(sanPhamMCAdapter);
    }

    @Override
    public void getDataSanPhamYT(String id, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong, String nhasanxuat, Long type, String trongluong) {
        arr_sp_yt.add(new SanPhamModels(id, tensp, giatien, hinhanh, loaisp, mota, soluong, nhasanxuat, type, trongluong));
        sanPhamYTAdapter = new SanPhamAdapter(getContext(), arr_sp_yt, 6);
        rcvSPYT.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rcvSPYT.setAdapter(sanPhamYTAdapter);
    }

    @Override
    public void getDataSanPhamLau(String id, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong, String nhasanxuat, Long type, String trongluong) {
        arr_sp_lau.add(new SanPhamModels(id, tensp, giatien, hinhanh, loaisp, mota, soluong, nhasanxuat, type, trongluong));
        sanPhamLauAdapter = new SanPhamAdapter(getContext(), arr_sp_lau, 7);
        rcvSPLau.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rcvSPLau.setAdapter(sanPhamLauAdapter);
    }

    @Override
    public void getDataSanPhamGY(String id, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong, String nhasanxuat, Long type, String trongluong) {
        arr_sp_gy.add(new SanPhamModels(id, tensp, giatien, hinhanh, loaisp, mota, soluong, nhasanxuat, type, trongluong));
        sanPhamGYAdapter = new SanPhamAdapter(getContext(), arr_sp_gy, 8);
        rcvSPGY.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rcvSPGY.setAdapter(sanPhamGYAdapter);
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
                FragMent_Bill newFragment = new FragMent_Bill();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
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
}
