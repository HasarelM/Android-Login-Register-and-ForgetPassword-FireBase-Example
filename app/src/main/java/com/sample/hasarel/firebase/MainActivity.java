package com.sample.hasarel.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Ui Components
    private EditText mEtUserName,mEtPassword;
    private TextView mTvForgetPassword;
    private Button mBtnLogin,mBtnRegister;
    private ProgressBar mPbProgressLogin;

    public FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        initView();
    }

    private void initView() {
        mEtUserName = findViewById(R.id.main_activity_et_userName);
        mEtPassword = findViewById(R.id.main_activity_et_password);
        mBtnLogin = findViewById(R.id.main_activity_btn_login);
        mBtnRegister = findViewById(R.id.main_activity_btn_register);
        mTvForgetPassword = findViewById(R.id.main_activity_tv_forgetPassword);
        mPbProgressLogin = findViewById(R.id.main_activity_pb_progressBar);
        mBtnRegister.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
        mTvForgetPassword.setOnClickListener(this);
    }

    private void userLogin() {

        mPbProgressLogin.setVisibility(View.VISIBLE);

        String email, password;
        email = mEtUserName.getText().toString();
        password = mEtPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            mPbProgressLogin.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            mPbProgressLogin.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, "Please Enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                          mPbProgressLogin.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this, "Successfully Login", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Faild", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void moveToRegisterActivity(){
        Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    private void moveToForgetPasswordActivity(){
        Intent intent = new Intent(MainActivity.this,ForgetPasswordActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_activity_btn_login:
                userLogin();
                break;
            case R.id.main_activity_btn_register:
                moveToRegisterActivity();
                break;
            case R.id.main_activity_tv_forgetPassword:
                moveToForgetPasswordActivity();
                break;
            default:
                break;
        }
    }
}