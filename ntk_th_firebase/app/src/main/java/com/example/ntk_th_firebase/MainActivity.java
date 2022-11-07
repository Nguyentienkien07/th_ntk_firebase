package com.example.ntk_th_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView signup;
    private EditText edtemail, edtpassword;
    private Button btndangnhap;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //tắt thanh trên
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signup = (TextView) findViewById(R.id.signup);
        signup.setOnClickListener(this);

        btndangnhap = (Button) findViewById(R.id.btndangnhap);
        btndangnhap.setOnClickListener(this);

        edtemail = (EditText) findViewById(R.id.edtemail);
        edtpassword =(EditText) findViewById(R.id.edtpassword);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signup:
                startActivity(new Intent(this,Signup.class));
                break;
            case R.id.btndangnhap:
                userLogin();
                break;
        }
    }

    private void userLogin() {

        String email = edtemail.getText().toString().trim();
        String password = edtpassword.getText().toString().trim();

        if(email.isEmpty()){
            edtemail.setError("Vui lòng nhập email ");
            edtemail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtemail.setError("vui lòng cung cấp email hợp lệ!");
            edtemail.requestFocus();
            return;
        }


        if(password.isEmpty()){
            edtpassword.setError("Vui Lòng Nhập Password");
            edtpassword.requestFocus();
        }

        if(password.length() < 6){
            edtpassword.setError("Password của bạn nhỏ hơn 6");
            edtpassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this, ListSinhVien.class));

                }else {
                    Toast.makeText(MainActivity.this, "Đăng nhập thất bại! Vui lòng kiểm tra thông tin đăng nhập của bạn", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}