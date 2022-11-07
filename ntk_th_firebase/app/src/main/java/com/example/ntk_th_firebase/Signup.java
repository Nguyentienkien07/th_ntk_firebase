package com.example.ntk_th_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Signup extends AppCompatActivity implements View.OnClickListener{
    private TextView tvsignin;
    private Button btnSignup;
    private EditText edtEmail, edtPassword;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //tắt thanh trên
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        tvsignin = (TextView) findViewById(R.id.tvsignin);
        tvsignin.setOnClickListener(this);
        btnSignup = (Button) findViewById(R.id.btnsignup);
        btnSignup.setOnClickListener(this);

        edtEmail = (EditText) findViewById(R.id.email);
        edtPassword =(EditText) findViewById(R.id.edtPassword);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvsignin:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.btnsignup:
                btnSignup();

        }
    }

    private void btnSignup() {
        String email= edtEmail.getText().toString().trim();
        String password= edtPassword.getText().toString().trim();

        if(email.isEmpty()){
            edtEmail.setError("Vui lòng nhập email ");
            edtEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmail.setError("vui lòng cung cấp email hợp lệ!");
            edtEmail.requestFocus();
            return;
        }


        if(password.isEmpty()){
            edtPassword.setError("Vui Lòng Nhập Password");
            edtPassword.requestFocus();
        }

        if(password.length() < 6){
            edtPassword.setError("Password của bạn nhỏ hơn 6");
            edtPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(email);

                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(Signup.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

                                            }else {
                                                Toast.makeText(Signup.this, "không đăng ký được, hãy thử lại!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }else {
                            Toast.makeText(Signup.this, "không đăng ký được, hãy thử lại!", Toast.LENGTH_SHORT).show();
                        }
                    }

                });

    }
}