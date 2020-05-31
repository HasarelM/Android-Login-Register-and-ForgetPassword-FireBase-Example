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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    //Ui Components
    private EditText mEtEmail,mEtPassword;
    private Button mBtnRegister;
    private ProgressBar mPbProgressBarRegister;

    public FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        initView();
    }

    private void initView() {
        mEtEmail = findViewById(R.id.register_activity_et_email);
        mEtPassword = findViewById(R.id.register_activity_et_password);
        mBtnRegister = findViewById(R.id.register_activity_btn_register);
        mPbProgressBarRegister = findViewById(R.id.register_activity_pb_progressBar);
        mBtnRegister.setOnClickListener(this);
    }

    public void userRegister(){

        mPbProgressBarRegister.setVisibility(View.VISIBLE);

        String email,password;

        email = mEtEmail.getText().toString();
        password = mEtPassword.getText().toString();

        if (TextUtils.isEmpty(email)){
            mPbProgressBarRegister.setVisibility(View.GONE);
            Toast.makeText(RegisterActivity.this,"please Enter Email....",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            mPbProgressBarRegister.setVisibility(View.GONE);
            Toast.makeText(RegisterActivity.this,"please Enter Password....",Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            mPbProgressBarRegister.setVisibility(View.GONE);

                            Toast.makeText(RegisterActivity.this,"Successfull Register !!! ",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(RegisterActivity.this,"Faild !!! ",Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_activity_btn_register:
                userRegister();
                break;
            default:
                break;
        }
    }
}