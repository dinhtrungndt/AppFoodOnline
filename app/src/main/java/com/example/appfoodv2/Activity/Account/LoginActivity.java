package com.example.appfoodv2.Activity.Account;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.example.appfoodv2.Model.UserPreSenter;
import com.example.appfoodv2.Interface.UserView;
import com.example.appfoodv2.R;
import com.example.appfoodv2.Activity.Admin.LoginAdminActivity;
import com.example.appfoodv2.Activity.HomeActivity;

public class LoginActivity extends AppCompatActivity implements UserView, View.OnClickListener {
    private Button btndangnhap,btnDangky;
    private EditText editemail, editpass;
    private TextView txtAdmin,forgotUser;
    private UserPreSenter userPreSenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Anhxa();
        onClick();
    }

    private void onClick() {
        userPreSenter = new UserPreSenter(this);
        btndangnhap.setOnClickListener(this);
        btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        txtAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, LoginAdminActivity.class));
            }
        });

        forgotUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,Forgot_Activity.class));
            }
        });

    }

    private void Anhxa() {
        btndangnhap = findViewById(R.id.btndangnhap);
        editemail = findViewById(R.id.editEmail);
        editpass = findViewById(R.id.editmatkhau);
        btnDangky = findViewById(R.id.btnDangky);
        forgotUser = findViewById(R.id.forgotUser);
        txtAdmin = findViewById(R.id.txtAdmin);
    }

    @Override
    public void OnLengthEmail() {
        Toast.makeText(this, "Email không để trống", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnValidEmail() {
        Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnPass() {
        Toast.makeText(this, "Mật khẩu không để trống", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void OnSucess() {
        startActivity(new Intent(this, HomeActivity.class));
        Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
        finish();

    }

    @Override
    public void OnAuthEmail() {
        FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification();
        Toast.makeText(this, "Làm ơn hãy vào gmail xác thực !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnFail() {
        Toast.makeText(this, "Sai tài khoản / Mật khẩu", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnPassNotSame() {
        Toast.makeText(this, "Tài khoản mật khẩu không khớp", Toast.LENGTH_SHORT).show();
    }

    /// ẤN đang nhap
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btndangnhap:
                String email = editemail.getText().toString();
                String pass = editpass.getText().toString().trim();
                userPreSenter.HandleLoginUser(email, pass);
        }
    }
}
