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
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    //Ui Components
    private EditText mEtEmail;
    private Button mBtnRecoverEmail;
    private ProgressBar mPbForgetPasswordBar;

    public FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        mAuth = FirebaseAuth.getInstance();

        initView();
    }

    private void initView() {
        mEtEmail = findViewById(R.id.forgetPassword_activity_et_email);
        mBtnRecoverEmail = findViewById(R.id.forgetPassword_activity_btn_RecoverEmail);
        mPbForgetPasswordBar = findViewById(R.id.forgetpaswword_activity_pb_progressBar);
        mBtnRecoverEmail.setOnClickListener(this);
    }

    private void changePassword(){

        mPbForgetPasswordBar.setVisibility(View.VISIBLE);

        String email;
        email = mEtEmail.getText().toString();

        if (TextUtils.isEmpty(email)){
            mPbForgetPasswordBar.setVisibility(View.GONE);
            Toast.makeText(ForgetPasswordActivity.this,"Please Enter Email",Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            mPbForgetPasswordBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),"Password reset link was sent your email address",Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(ForgetPasswordActivity.this,MainActivity.class);
                                startActivity(intent);

                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Mail sending error",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.forgetPassword_activity_btn_RecoverEmail:
                changePassword();
                break;
            default:
                break;
        }
    }
}