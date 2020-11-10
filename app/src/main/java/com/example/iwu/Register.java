package com.example.iwu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity implements View.OnClickListener{

    EditText et_name, et_email, et_pw, et_pwChk, et_address;
    Button btn_join;
    ImageView btn_back;
    String sName, sEmail, sPw, sPwChk, sAd;
    //private FirebaseUser user;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();

        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_pw = findViewById(R.id.et_pw);
        et_pwChk = findViewById(R.id.et_pwChk);
        et_address = findViewById(R.id.et_address);
        btn_back = findViewById(R.id.btn_back);
        btn_join = findViewById(R.id.bt_Join);

        firebaseAuth =  FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        btn_back.setOnClickListener(this);
        btn_join.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==btn_back){
            finish();
        }
        else if(view == btn_join) {
            JoinClick();
        }
    }

    public void JoinClick()
    {
        sName = et_name.getText().toString();
        sEmail = et_email.getText().toString();
        sPw = et_pw.getText().toString();
        sPwChk = et_pwChk.getText().toString();
        sAd = et_address.getText().toString();

        if(sPw.equals(sPwChk)){
            final ProgressDialog mDialog = new ProgressDialog(Register.this);
            mDialog.setMessage("가입중입니다...");
            mDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(sEmail,sPw).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //가입 성공시
                    if (task.isSuccessful()) {
                        mDialog.dismiss();
                        //가입이 이루어져을시 가입 화면을 빠져나감.
                        Intent intent = new Intent(Register.this, Login.class);
                        startActivity(intent);
                        finish();
/*
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        String email = user.getEmail();
                        String uid = user.getUid();
                        String name = et_name.getText().toString().trim();

                        //해쉬맵 테이블을 파이어베이스 데이터베이스에 저장
                        HashMap<Object,String> hashMap = new HashMap<>();

                        hashMap.put("name",sName);
                        hashMap.put("email",sEmail);
                        hashMap.put("password",sPw);
                        hashMap.put("address",sAd);

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference reference = database.getReference("Users");
                        reference.child(uid).setValue(hashMap);
*/

                    } else {
                        mDialog.dismiss();
                        Toast.makeText(Register.this, "이미 존재하는 이메일 입니다.", Toast.LENGTH_SHORT).show();
                        return;  //해당 메소드 진행을 멈추고 빠져나감.
                    }

                }
            });
        }

        else{
            Toast.makeText(Register.this, "비밀번호가 틀렸습니다. 다시 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}