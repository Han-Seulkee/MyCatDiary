package com.hsk.mycatdiary.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hsk.mycatdiary.R;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    public SharedPreferences pref;

    //view
    Button btnLogin;
    EditText userEmail, userPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //초기화작업
        btnLogin = findViewById(R.id.btnLogin);


        //앱 처음 실행인지 확인
        pref = getSharedPreferences("Pref",MODE_PRIVATE);
        this.checkFirstRun();

        mAuth = FirebaseAuth.getInstance();

        Intent returnIntent = new Intent();

        /* Firebase 로그인 상태 확인
        로그인중 --> 액티비티 종료
        로그인X --> 로그인 실행
        */
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            setResult(RESULT_OK,returnIntent);
            finish();
        }
    }

    public void onLogin(View v) {
        userEmail = findViewById(R.id.userEmail);
        userPw = findViewById(R.id.userPw);

    }

    public void onJoin(View v) {
        Intent joinIntent = new Intent(LoginActivity.this, JoinActivity.class);
//        ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//            @Override
//            public void onActivityResult(ActivityResult result) {
//                if(result.getResultCode() == RESULT_OK) {
//
//                }
//            }
//        });
        startActivity(joinIntent);
    }

    private void checkFirstRun() {
        boolean isFirstRun = pref.getBoolean("isFirstRun", true);
        if(isFirstRun){
            //권한확인
            if(this.checkPermission()) {}
            else
                this.requestPermission();
            pref.edit().putBoolean("isFirstRun",false).apply();//실행 후 상태를 false로 바꿔 첫실행이 아님을 나타냄
        }
    }

    //권한확인
    private boolean checkPermission() {
        boolean result = false;
        int permission = ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.CALL_PHONE);
        if(permission == PackageManager.PERMISSION_GRANTED) { //이전에 요청 허락
            result = true;
        }
        return result;
    }

    //권한요청
    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,android.Manifest.permission.CALL_PHONE)) {
            Toast.makeText(LoginActivity.this, "일부 기능을 사용 하실 수 없습니다",Toast.LENGTH_SHORT).show();
        }
        else {
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{android.Manifest.permission.CALL_PHONE},1);
        }
    }

    //권한요청 결과
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(LoginActivity.this, "권한동의 완료", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
