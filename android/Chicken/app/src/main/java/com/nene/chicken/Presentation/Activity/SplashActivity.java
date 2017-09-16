package com.nene.chicken.Presentation.Activity;
import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.nene.chicken.R;

import java.util.ArrayList;

public class SplashActivity extends ChickenBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TedPermission.with(this)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                navigateActivity(ChooseLocationActivity.class);
                                finish();
                            }
                        }, 3000);// 3 초
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        Toast.makeText(SplashActivity.this, "권한 거부시 앱을 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .setDeniedMessage("권한없이 앱을 사용할 수 없습니다\n\n설정창에 가서 권한을 켜주세요[Setting] > [Permission]")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .check();
    }
}
