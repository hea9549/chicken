package com.nene.chicken.Presentation.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.nene.chicken.AppApplication;
import com.nene.chicken.Model.TransPosition;
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

public class MainActivity extends ChickenBaseActivity implements MainPresenter.View {
    MainPresenter presenter;
    private NMapView mMapView;// 지도 화면 View
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("좌표","" + NMapConverter.utmK2Grs(350111810, 149774298).getLatitude() + "," + NMapConverter.utmK2Grs(350111810, 149774298).getLongitude());
    }

    @Override
    public void drawPath(List<TransPosition> positions){
        // 화면그리기 작성해야함
    }
}
