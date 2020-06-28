package com.hsk.mycatdiary;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;

    public SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //앱 처음 실행인지 확인
        pref = getSharedPreferences("Pref",MODE_PRIVATE);
        checkFirstRun();

        //네비게이션 뷰
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        //버전확인, 권한확인
        if(Build.VERSION.SDK_INT >= 23) {
            if(checkPermission()) {}
            else { requestPermission(); }
        }
    }

    public void checkFirstRun() {
        boolean isFirstRun = pref.getBoolean("isFirstRun", true);
        if(isFirstRun){ //첫 실행시 로그인(정보입력) 화면으로 넘겨줌
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            pref.edit().putBoolean("isFirstRun",false).apply();//실행 후 상태를 false로 바꿔 첫실행이 아님을 나타냄
        }
    }

    //권한확인
    private boolean checkPermission() {
        int result = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE);
        if(result == PackageManager.PERMISSION_GRANTED) { //이전에 요청 허락
            return true;
        }
        else { //요청 허락x
            return false;
        }
    }
    //권한요청
    void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,android.Manifest.permission.CALL_PHONE)) {
            Toast.makeText(MainActivity.this, "병원에 전화걸기 기능을 사용하실 수 없습니다",Toast.LENGTH_SHORT).show();
        }
        else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.CALL_PHONE},1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1){
            if(grantResults.length>0&&grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(MainActivity.this, "권한동의 완료", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
