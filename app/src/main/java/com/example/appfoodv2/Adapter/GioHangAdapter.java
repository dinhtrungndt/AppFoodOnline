package com.example.appfoodv2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.example.appfoodv2.Model.SanPhamModels;
import com.example.appfoodv2.Interface.SetOnItemClick;
import com.example.appfoodv2.R;

import java.text.NumberFormat;
import java.util.ArrayList;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.ViewHodler> {
    private Context context;
    private ArrayList<SanPhamModels> arrayList;
    private int type = 0;

    public GioHangAdapter(Context context, ArrayList<SanPhamModels> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public GioHangAdapter(Context context, ArrayList<SanPhamModels> arrayList, int type) {
        this.context = context;
        this.arrayList = arrayList;
        this.type = type;
    }

    @Override
    public GioHangAdapter.ViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (type == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.dong_sanpham, parent, false);
        } else if (type == 2) {
            view = LayoutInflater.from(context).inflate(R.layout.dong_sanpham_noibat, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.dong_giohang, parent, false);
        }


        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(GioHangAdapter.ViewHodler holder, int position) {

        SanPhamModels sanPhamModels = arrayList.get(position);

        holder.txttensp.setText(sanPhamModels.getTensp());

        holder.txtgiasp.setText(NumberFormat.getInstance().format(sanPhamModels.getGiatien()) + " Đ");
        Picasso.get().load(sanPhamModels.getHinhanh()).into(holder.hinhanh);
        holder.SetOnItem(new SetOnItemClick() {
            @Override
            //chi tiet san phẩm
            public void SetItemClick(View view, int pos) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txttensp, txtgiasp;
        ImageView hinhanh;
        SetOnItemClick itemClick;

        public ViewHodler(View itemView) {
            super(itemView);
            txtgiasp = itemView.findViewById(R.id.txtgiatien);
            txttensp = itemView.findViewById(R.id.txttensp);
            hinhanh = itemView.findViewById(R.id.hinhanh);
            itemView.setOnClickListener(this);
        }

        public void SetOnItem(SetOnItemClick itemClick) {
            this.itemClick = itemClick;
        }

        @Override
        public void onClick(View v) {
            itemClick.SetItemClick(v, getAdapterPosition());
        }
    }
}
