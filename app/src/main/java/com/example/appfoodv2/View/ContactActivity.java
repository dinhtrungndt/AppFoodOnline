package com.example.appfoodv2.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.example.appfoodv2.R;

public class ContactActivity  extends AppCompatActivity implements OnMapReadyCallback {
    private Toolbar toolbar;
    private FirebaseFirestore db;
    private TextView txtdiachi,txtsdt,txtnoidung;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        toolbar = findViewById(R.id.toolbar);
        txtdiachi=findViewById(R.id.txtdiachi);
        txtsdt = findViewById(R.id.txtsdt);
        txtnoidung = findViewById(R.id.txtnoidung);

        db =FirebaseFirestore.getInstance();



        db.collection("ThongTinCuaHang").document("wObs1mNNJdVbQN19iYSv")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {

                txtdiachi.setText("Địa chỉ : "+documentSnapshot.getString("diachi"));
                txtsdt.setText("Liên hệ : "+documentSnapshot.getString("sdt"));
                txtnoidung.setText("Nội Dung : "+documentSnapshot.getString("noidung"));


            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Back");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // GG maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) { //đọc vị trí gg map
        LatLng latLng = new LatLng(10.8529444,106.6273561); // vĩ độ trên ggmaps
        MarkerOptions options = new MarkerOptions();
        options.position(latLng);
        options.title("food app FPOLY");
        options.snippet("food app FPOLY .du an 1 . ").icon(bitmapDescriptor(getApplicationContext(),R.drawable.icfood_baseline_fastfood_24)); // option hiển thị thông tin vị trí lấy từ gg map

        googleMap.addMarker(options);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,18)); // đọc camera


    }
    private BitmapDescriptor bitmapDescriptor (Context context, int vID){
        Drawable vD = ContextCompat.getDrawable(context,vID);
        vD.setBounds(0,0,vD.getIntrinsicWidth(),
                vD.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vD.getIntrinsicWidth(),
                vD.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vD.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

}
