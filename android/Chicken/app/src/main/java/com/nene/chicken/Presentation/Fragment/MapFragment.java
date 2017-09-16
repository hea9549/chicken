package com.nene.chicken.Presentation.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nene.chicken.AppApplication;
import com.nene.chicken.NMap.NMapViewerResourceProvider;
import com.nene.chicken.Presentation.Activity.MainActivity;
import com.nene.chicken.Presentation.Activity.MarkInfo;
import com.nene.chicken.Presentation.Presenter.MainMapPresenter;
import com.nene.chicken.Presentation.Presenter.MainPresenter;
import com.nene.chicken.Presentation.Presenter.MainPresenterImpl;
import com.nene.chicken.R;
import com.nhn.android.maps.NMapCompassManager;
import com.nhn.android.maps.NMapContext;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.maplib.NMapConverter;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPathData;
import com.nhn.android.mapviewer.overlay.NMapMyLocationOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPathDataOverlay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ParkHaeSung on 2017-09-16.
 */

public class MapFragment extends ChickenBaseFragment implements MainMapPresenter.View {

    private NMapContext mMapContext;
    private NMapLocationManager locationManager;
    private NMapView mapView;
    private MainPresenter presenter;
    private NMapController mapController;
    private NMapViewerResourceProvider mMapViewerResourceProvider;
    private NMapOverlayManager mOverlayManager;
    private NMapMyLocationOverlay mMyLocationOverlay;
    private NMapCompassManager mMapCompassManager;
    private double totalDistance;
    private double mySpeed;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapContext = new NMapContext(super.getActivity());
        mMapContext.onCreate();
        presenter = new MainPresenterImpl();
        locationManager = new NMapLocationManager(getContext());
        locationManager.enableMyLocation(true);
        mMapViewerResourceProvider = new NMapViewerResourceProvider(getContext());
        mMapCompassManager = new NMapCompassManager(getActivity());

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
        ((MainActivity)getActivity()).setStartTime(format.format(date));
        // create overlay manager
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapView = (NMapView) getView().findViewById(R.id.fragment_map);
        mapView.setClientId(AppApplication.NAVER_CLIENT_ID);// 클라이언트 아이디 설정
        mapView.setClickable(true);
        mapView.setEnabled(true);
        mapView.setFocusable(true);
        mapView.setFocusableInTouchMode(true);
        mapView.requestFocus();
        mMapContext.setupMapView(mapView);
        mapController = mapView.getMapController();
        mOverlayManager = new NMapOverlayManager(getContext(), mapView, mMapViewerResourceProvider);
        mMyLocationOverlay = mOverlayManager.createMyLocationOverlay(locationManager, mMapCompassManager);
        mapView.setOnMapStateChangeListener(new NMapView.OnMapStateChangeListener() {
            @Override
            public void onMapInitHandler(NMapView nMapView, NMapError nMapError) {

            }

            @Override
            public void onMapCenterChange(NMapView nMapView, NGeoPoint nGeoPoint) {
            }

            @Override
            public void onMapCenterChangeFine(NMapView nMapView) {

            }

            @Override
            public void onZoomLevelChange(NMapView nMapView, int i) {

            }

            @Override
            public void onAnimationStateChange(NMapView nMapView, int i, int i1) {

            }
        });


        Observable.interval(1, TimeUnit.SECONDS)
                .delay(5,TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(tick -> {
                    Log.e("와이", "??:" + locationManager.isMyLocationEnabled());
                    if (locationManager.isMyLocationEnabled()) {
                        mySpeed = presenter.getSpeed(locationManager.getMyLocation());
                        Log.e("속도당", "내 평균 속도 : " + mySpeed);
                        ((MainActivity) getActivity()).setSpeed(mySpeed);
                    }
                }, fail -> Log.e("ERROR IN TICK", "error in tick =" + fail.toString()));
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapContext.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapContext.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapContext.onPause();
    }

    @Override
    public void onStop() {
        mMapContext.onStop();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        mMapContext.onDestroy();
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_map, container, false);
        return view;
    }

    @Override
    public void drawPath(List<MarkInfo> positions) {

    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
        Observable.just("gogo")
                .delay(30,TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(s->{
                    ((MainActivity) getActivity()).setTakeTime((int) (totalDistance / mySpeed / 60));

                });
    }

    public void drawPath(MarkInfo markInfo) {
        int posCount = 0;
        String pathJson = markInfo.getPathJson();
        String[] coorJson = pathJson.split(" ");
        if (coorJson[0].isEmpty()) return;
        posCount += (coorJson.length);

        NMapPathData pathData = new NMapPathData(posCount);
        pathData.initPathData();
        if (pathJson.isEmpty()) return;
        for (int j = 0; j < coorJson.length; j++) {
            NGeoPoint np = NMapConverter.utmK2Grs(Integer.valueOf(coorJson[j].split(",")[0]), Integer.valueOf(coorJson[j].split(",")[1]));
            pathData.addPathPoint(np.getLongitude(), np.getLatitude(), 0 );
        }
        pathData.endPathData();

        NMapPathDataOverlay pathDataOverlay = mOverlayManager.createPathDataOverlay(pathData);
        if (markInfo.inclineType == MarkInfo.INCLINE_ASCENT){
            pathDataOverlay.setLineColor(Color.rgb(234,158,55),255);
        }
        if (markInfo.inclineType == MarkInfo.INCLINE_DESCENT){
            pathDataOverlay.setLineColor(Color.rgb(51,219,238),255);
        }
        if (markInfo.inclineType == MarkInfo.INCLINE_FLAT){
            pathDataOverlay.setLineColor(Color.rgb(108,231,244),255);
        }
        if (markInfo.inclineType == MarkInfo.INCLINE_HARD_ASCENT){
            pathDataOverlay.setLineColor(Color.rgb(255,0,0),255);
        }
        if (markInfo.inclineType == MarkInfo.INCLINE_HARD_DESCENT){
            pathDataOverlay.setLineColor(Color.rgb(0,0,255),255);
        }
        pathDataOverlay.setLineWidth(4.0f);
        pathDataOverlay.showAllPathData(10);
    }
}
