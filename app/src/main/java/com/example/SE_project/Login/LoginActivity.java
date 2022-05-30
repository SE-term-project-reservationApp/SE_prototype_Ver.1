package com.example.SE_project.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.SE_project.MainActivity;
import com.example.SE_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//

public class LoginActivity extends AppCompatActivity {
    private View login_K, logout_K;

    private static final String TAG = "LoginActivity";
    private FirebaseAuth mAuth;
    //private CallbackManager mCallbackManager;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button loginE = findViewById(R.id.loginB);

        Button FindPW = (Button) findViewById(R.id.findPW);
        FindPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(LoginActivity.this, FindPW.class);
                startActivity(intent2);
            }
        });

        mAuth = FirebaseAuth.getInstance(); //파이어베이스어스객체의 공유 인스턴스를 가져옴
        findViewById(R.id.loginB).setOnClickListener(onClickListener);
        findViewById(R.id.sb).setOnClickListener(onClickListener);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop(){
        super.onStop();
        mAuth.removeAuthStateListener(authStateListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.sb:
                    startSignupActivity();
                case R.id.loginB:
                    login();
                    break;
            }
        }
    };

    private void login() {
        String email = ((EditText) findViewById(R.id.ID2)).getText().toString();
        String password = ((EditText) findViewById(R.id.pw2)).getText().toString();

        if (email.length() > 0 && password.length() > 0) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                startToast("로그인 성공");
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                updateUI(user);
                            } else {
                                if (task.getException() != null) {
                                    startToast(task.getException().toString());
                                    updateUI(null);
                                    startToast("아이디 또는 비밀번호를 확인하세요.");
                                }
                            }
                        }
                    });
        } else {
            startToast("이메일 또는 비밀번호를 입력해주세요");
        }


    }//파베관련

    private void startSignupActivity() {
        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(intent);
    }//파베관련

    //파베관련
    private void updateUI(FirebaseUser user) {
    }

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }


}


