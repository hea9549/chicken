package com.nene.chicken.Presentation.Activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.nene.chicken.AppApplication;
import com.nene.chicken.Model.TransPosition;
import com.nene.chicken.Presentation.Fragment.MapFragment;
import com.nene.chicken.Presentation.Presenter.BaseViewPresenter;
import com.nene.chicken.Presentation.Presenter.MainPresenter;
import com.nene.chicken.Presentation.Presenter.MainPresenterImpl;
import com.nene.chicken.R;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGPoint;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.maplib.NMapConverter;
import com.nhn.android.maps.overlay.NMapPathData;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends ChickenBaseActivity implements MainPresenter.View {
    MainPresenter presenter;
    private NMapView mMapView;// 지도 화면 View

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MapFragment fragment = new MapFragment();
        fragment.setArguments(new Bundle());
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.mapContainer, fragment);
        fragmentTransaction.commit();
        Log.e("좌표", "" + NMapConverter.utmK2Grs(350111810, 149774298).getLatitude() + "," + NMapConverter.utmK2Grs(350111810, 149774298).getLongitude());


    }

    @Override
    public void drawPath(List<TransPosition> positions) {
        // 화면그리기 작성해야함
    }
}
