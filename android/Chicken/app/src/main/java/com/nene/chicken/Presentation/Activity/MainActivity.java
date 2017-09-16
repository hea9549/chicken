package com.nene.chicken.Presentation.Activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nene.chicken.Model.TransPosition;
import com.nene.chicken.Presentation.Fragment.MapFragment;
import com.nene.chicken.Presentation.Presenter.MainPresenter;
import com.nene.chicken.R;
import com.nene.chicken.Service.DistanceService;
import com.nene.chicken.Service.DistanceServiceImpl;
import com.nene.chicken.Util.DistanceUtil;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.maplib.NMapConverter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends ChickenBaseActivity implements MainPresenter.View {
    MainPresenter presenter;
    private NMapView mMapView;// 지도 화면 View
    private TextView fromTextView;
    private TextView toTextView;

    private double totalDistance = 0;
    private List<TransPosition> positions;
    @BindView(R.id.mapContainer)
    LinearLayout mapContainer;
    @BindView(R.id.tv_startTime)
    TextView tv_startTime;
    @BindView(R.id.tv_endTime)
    TextView tv_endTime;
    @BindView(R.id.tv_speed)
    TextView tv_speed;
    @BindView(R.id.tv_takeTime)
    TextView tv_takeTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MapFragment fragment = new MapFragment();
        fragment.setArguments(new Bundle());
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.mapContainer, fragment);
        fragmentTransaction.commit();

        Intent intent =  getIntent();

        fromTextView = (TextView)findViewById(R.id.from_TextView);
        fromTextView.setText(intent.getStringExtra("fromTitle"));
        toTextView = (TextView)findViewById(R.id.to_TextView);
        toTextView.setText(intent.getStringExtra("toTitle"));

        List<MarkInfo> markInfoes = (List)getIntent().getSerializableExtra("markInfoList");
        positions = new ArrayList<>();
        for(int i = 0 ; i<markInfoes.size();i++){
            NGeoPoint n = NMapConverter.utmK2Grs(markInfoes.get(i).getMapx(),markInfoes.get(i).getMapy());
            positions.add(new TransPosition(n.getLatitude(),n.getLongitude()));
        }
        DistanceService distanceService = new DistanceServiceImpl();
        for(int i = 0 ; i < positions.size();i++){
            distanceService.getHeight(positions.get(i)).subscribe();
        }

        Observable.just("start!!")
                .delay(5,TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s->{
                    Toast.makeText(this, "모두계산햇찌롱!", Toast.LENGTH_SHORT).show();
                    double totalHeightSum = 0;
                    for(int i = 0 ; i < positions.size()-1;i++){
                        totalHeightSum +=Math.abs(positions.get(i).getHeight()-positions.get(i+1).getHeight());
                    }
                    List<TransPosition> allPathPositions = new ArrayList<>();
                    for(int i = 0 ; i <markInfoes.size();i++){
                        if (markInfoes.get(i).getPathPositions() == null)continue;
                        allPathPositions.addAll(markInfoes.get(i).getPathPositions());
                    }
                    for(int i = 0 ; i<allPathPositions.size()-1;i++){
                        allPathPositions.get(i).setHeight(totalHeightSum/markInfoes.size());
                        allPathPositions.get(i+1).setHeight(totalHeightSum/markInfoes.size());
                        totalDistance+=DistanceUtil.calDistance(allPathPositions.get(i),allPathPositions.get(i+1));
                    }


                    for (int i = 0 ; i < positions.size()-1;i++){
                        double floorDistance = DistanceUtil.calcDistance(positions.get(i).getLatitude(),positions.get(i).getLongitude()
                                ,positions.get(i+1).getLatitude(),positions.get(i+1).getLongitude());
                        if ((positions.get(i+1).getHeight()-positions.get(i).getHeight())/floorDistance>0.0699268)markInfoes.get(i).inclineType = MarkInfo.INCLINE_HARD_ASCENT;
                        else if ((positions.get(i+1).getHeight()-positions.get(i).getHeight())/floorDistance>0.0261859)markInfoes.get(i).inclineType = MarkInfo.INCLINE_ASCENT;
                        else if ((positions.get(i+1).getHeight()-positions.get(i).getHeight())/floorDistance<-0.0699268)markInfoes.get(i).inclineType = MarkInfo.INCLINE_HARD_DESCENT;
                        else if ((positions.get(i+1).getHeight()-positions.get(i).getHeight())/floorDistance<-0.0261859)markInfoes.get(i).inclineType = MarkInfo.INCLINE_ASCENT;
                        else markInfoes.get(i).inclineType = MarkInfo.INCLINE_FLAT;
                        Log.e("탄젠트값",""+i+"번째,"+((positions.get(i+1).getHeight()-positions.get(i).getHeight())/floorDistance));
                        Log.e("탄젠트값",""+i+"번째 바닥,"+(floorDistance));
                        Log.e("탄젠트값",""+i+"번째 높아치,"+((positions.get(i+1).getHeight()-positions.get(i).getHeight())));

                        fragment.drawPath(markInfoes.get(i));
                    }
                    double floorDistance = DistanceUtil.calcDistance(positions.get(positions.size()-1).getLatitude(),positions.get(positions.size()-1).getLongitude()
                            ,positions.get(positions.size()-2).getLatitude(),positions.get(positions.size()-2).getLongitude());
                    if ((positions.get(positions.size()-1).getHeight()-positions.get(positions.size()-2).getHeight())/floorDistance>0.57735)markInfoes.get(positions.size()-1).inclineType = MarkInfo.INCLINE_ASCENT;
                    else if ((positions.get(positions.size()-1).getHeight()-positions.get(positions.size()-2).getHeight())/floorDistance<-0.57735)markInfoes.get(positions.size()-1).inclineType = MarkInfo.INCLINE_DESCENT;
                    else markInfoes.get(positions.size()-1).inclineType = MarkInfo.INCLINE_FLAT;

                    Toast.makeText(this, "총 거리 : "+totalDistance, Toast.LENGTH_SHORT).show();
                    fragment.setTotalDistance(totalDistance);
                });
    }


    @Override
    public void drawPath(List<TransPosition> positions) {
        // 화면그리기 작성해야함
    }

    public void setStartTime(String time){
        tv_startTime.setText(time+" ~");
    }
    public void setEndTime(String endTime){
        tv_endTime.setText(endTime);
    }

    public void setSpeed(double meterPerSec){
        tv_speed.setText(String.format("%.2f m/s",meterPerSec));
    }

    public void setTakeTime(int min){
        tv_takeTime.setText(""+min+"Min");
        Date date = new Date();
        Date calcDate = new Date(date.getTime()+(min*60*1000));
        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
        tv_endTime.setText(""+format.format(calcDate));
    }

}
