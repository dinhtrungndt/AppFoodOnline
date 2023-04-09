package com.example.appfoodv2.Activity.FragMent;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.appfoodv2.Activity.Bill.CartActivity;
import com.example.appfoodv2.Activity.ContactActivity;
import com.example.appfoodv2.Activity.ThongKeDanhMucActivity;
import com.example.appfoodv2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragMent_ProFile extends Fragment implements View.OnClickListener {
    View view;
    private FirebaseFirestore db;
    private TextView txtsdt, txthoten, txtdiachi, txtmail;
    private String key = "";
    private StorageReference storageReference;
    private CircleImageView avatar;
    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        txtdiachi = view.findViewById(R.id.txtdiachi);
        txthoten = view.findViewById(R.id.txthoten);
        txtsdt = view.findViewById(R.id.txtsdt);
        avatar = view.findViewById(R.id.avatar);
        txtmail = view.findViewById(R.id.txtemail);
        fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);

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
                            Toast.makeText(getContext(), "Trang chủ !!!", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getContext(), "Bạn đang cập nhập !!!", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        if (item.getItemId() == R.id.menu) {
                            startActivity(new Intent(getContext(), ThongKeDanhMucActivity.class));
                            return true;
                        }

                        return false;
                    }
                });

        db = FirebaseFirestore.getInstance();
        txtmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        db.collection("thongtinUser").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("Profile")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (queryDocumentSnapshots.size() > 0) {
                            DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                            if (documentSnapshot != null) {
                                key = documentSnapshot.getId();
                                try {
                                    txtdiachi.setText(documentSnapshot.getString("diachi").length() > 0 ?
                                            documentSnapshot.getString("diachi") : "");
                                    txthoten.setText(documentSnapshot.getString("hoten").length() > 0 ?
                                            documentSnapshot.getString("hoten") : "");
                                    txtsdt.setText(documentSnapshot.getString("sdt").length() > 0 ?
                                            documentSnapshot.getString("sdt") : "");
                                    if (documentSnapshot.getString("avatar").length() > 0) {
                                        Picasso.get().load(documentSnapshot.getString("avatar").trim()).into(avatar);
                                    }
                                } catch (Exception e) {


                                }

                            } else {
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("diachi", "");
                                hashMap.put("hoten", "");
                                hashMap.put("sdt", "");
                                hashMap.put("avatar", "");
                                db.collection("thongtinUser").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .collection("Profile").add(hashMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                key = documentReference.getId();
                                            }
                                        });

                            }
                        } else {
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("diachi", "");
                            hashMap.put("hoten", "");
                            hashMap.put("sdt", "");
                            hashMap.put("avatar", "");
                            db.collection("thongtinUser").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .collection("Profile").add(hashMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            key = documentReference.getId();
                                        }
                                    });
                        }
                    }
                });

        txtdiachi.setOnClickListener(this);
        txthoten.setOnClickListener(this);
        txtsdt.setOnClickListener(this);
        avatar.setOnClickListener(this);

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
                Toast.makeText(getContext(), "Bạn đang chỉnh thông tin!", Toast.LENGTH_SHORT).show();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtdiachi:
                DiaLog(3);
                break;
            case R.id.txthoten:
                DiaLog(1);
                break;
            case R.id.txtsdt:
                DiaLog(2);
                break;
            case R.id.avatar:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            || getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        getActivity().requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
                    } else {
                        PickGallary();
                    }
                } else {
                    PickGallary();
                }

        }
    }

    private void PickGallary() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 123);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 123 && resultCode == getActivity().RESULT_OK) {
            Uri uri = data.getData();
            Log.d("CHECKED", uri + " ");
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] datas = baos.toByteArray();
                String filename = FirebaseAuth.getInstance().getCurrentUser().getUid();
                storageReference = FirebaseStorage.getInstance("gs://doan-dc57a.appspot.com/").getReference();
                storageReference.child("Profile").child(filename + ".jpg").putBytes(datas).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        if (taskSnapshot.getTask().isSuccessful()) {
                            storageReference.child("Profile").child(filename + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    db.collection("thongtinUser").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .collection("Profile").document(key)
                                            .update("avatar", uri.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        avatar.setImageBitmap(bitmap);
                                                    }
                                                }
                                            });
                                }
                            });
                        }
                    }
                });
            } catch (FileNotFoundException e) {
                Log.d("CHECKED", e.getMessage());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void DiaLog(int i) {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_profile);
        dialog.show();
        EditText editvalue = dialog.findViewById(R.id.editvalue);
        Button btnxacnhan = dialog.findViewById(R.id.btnxacnhan);
        switch (i) {
            case 1:
                editvalue.setHint("Nhập họ tên");
                break;
            case 2:
                editvalue.setInputType(InputType.TYPE_CLASS_NUMBER);
                editvalue.setHint("Nhập số điện thoại");
                break;
            case 3:
                editvalue.setHint("Nhập dịa chỉ");
                break;
        }
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = editvalue.getText().toString().trim();
                String keys = "";
                if (value.length() > 0) {
                    switch (i) {
                        case 1:
                            keys = "hoten";
                            break;
                        case 2:
                            keys = "sdt";
                            break;
                        case 3:
                            keys = "diachi";
                            break;


                    }
                    db.collection("thongtinUser").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .collection("Profile").document(key)
                            .update(keys, value).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        switch (i) {
                                            case 1:
                                                txthoten.setText(value);
                                                break;
                                            case 2:
                                                txtsdt.setText(value);
                                                break;
                                            case 3:
                                                txtdiachi.setText(value);
                                                break;
                                        }

                                    }
                                    dialog.cancel();
                                }
                            });
                } else {
                    Toast.makeText(getContext(), "Không để trống !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
