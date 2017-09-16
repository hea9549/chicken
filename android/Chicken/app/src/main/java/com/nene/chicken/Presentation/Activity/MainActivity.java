package com.nene.chicken.Presentation.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nene.chicken.Model.TransPosition;
import com.nene.chicken.Presentation.Fragment.MapFragment;
import com.nene.chicken.Presentation.Presenter.MainPresenter;
import com.nene.chicken.R;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NMapConverter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends ChickenBaseActivity implements MainPresenter.View {
    MainPresenter presenter;
    private NMapView mMapView;// 지도 화면 View
    private EditText fromEditText;
    private EditText toEditText;
    private Button fromButton;
    private Button toButton;
    @BindView(R.id.mapContainer)
    LinearLayout mapContainer;
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

    }

    @Override
    public void drawPath(List<TransPosition> positions) {
        // 화면그리기 작성해야함
    }

}
