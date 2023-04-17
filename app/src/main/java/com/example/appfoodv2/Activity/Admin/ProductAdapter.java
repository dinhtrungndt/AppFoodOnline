package com.example.appfoodv2.Activity.Admin;

import android.app.Activity;
import android.content.Intent;
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
import com.example.appfoodv2.Activity.upSPActivity;

import java.text.NumberFormat;
import java.util.ArrayList;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Activity context;
    private ArrayList<SanPhamModels> arrayList;

    public ProductAdapter(Activity context, ArrayList<SanPhamModels> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_giohang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        SanPhamModels sanPhamModels = arrayList.get(position);

        holder.txttensp.setText(sanPhamModels.getTensp());

        holder.txtgiasp.setText(NumberFormat.getInstance().format(sanPhamModels.getGiatien()) + " Đ");
        Picasso.get().load(sanPhamModels.getHinhanh()).into(holder.hinhanh);
        holder.SetOnItem(new SetOnItemClick() {
            @Override
            //chi tiet san phẩm
            public void SetItemClick(View view, int pos) {
                Intent intent = new Intent(context, upSPActivity.class);
                intent.putExtra("SP", sanPhamModels);
                context.startActivityForResult(intent, 100);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txttensp, txtgiasp;
        ImageView hinhanh;
        SetOnItemClick itemClick;

        public ViewHolder(View itemView) {
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
