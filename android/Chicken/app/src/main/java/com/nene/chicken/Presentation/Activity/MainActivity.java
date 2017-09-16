package com.nene.chicken.Presentation.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nene.chicken.AppApplication;
import com.nene.chicken.Model.HeightResponse;
import com.nene.chicken.Model.TransPosition;
import com.nene.chicken.Presentation.Fragment.MapFragment;
import com.nene.chicken.Presentation.Presenter.BaseViewPresenter;
import com.nene.chicken.Presentation.Presenter.MainPresenter;
import com.nene.chicken.Presentation.Presenter.MainPresenterImpl;
import com.nene.chicken.R;
import com.nene.chicken.Service.DistanceService;
import com.nene.chicken.Service.DistanceServiceImpl;
import com.nene.chicken.Util.DistanceUtil;
import com.nene.chicken.Util.GeoTrans;
import com.nhn.android.maps.NMapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends ChickenBaseActivity implements MainPresenter.View {
    MainPresenter presenter;
    private NMapView mMapView;// 지도 화면 View
    private EditText fromEditText;
    private EditText toEditText;
    private Button fromButton;
    private Button toButton;
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
    @BindView(R.id.wrapper_info)
    RelativeLayout wrapper_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setLayout();
        MapFragment fragment = new MapFragment();
        fragment.setArguments(new Bundle());
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.mapContainer, fragment);
        fragmentTransaction.commit();

        positions = (List<TransPosition>)getIntent().getSerializableExtra("positions");
        positions = new ArrayList<>();
        positions.add(new TransPosition(35.231686, 129.084002));
        positions.add(new TransPosition(35.238013, 129.086390));
        DistanceService distanceService = new DistanceServiceImpl();
        Observable<HeightResponse> heightObservable = distanceService.getHeight(positions.get(0));
        for(int i = 1 ; i < positions.size();i++){
            heightObservable.concatWith(
                    distanceService.getHeight(positions.get(i))
            );
        }
        heightObservable.doOnCompleted(()->{
            Toast.makeText(this, "모두계산햇찌롱!", Toast.LENGTH_SHORT).show();
            for(int i = 0 ; i <positions.size()-1;i++){
                totalDistance+=DistanceUtil.calDistance(positions.get(i),positions.get(i+1));
            }
            Toast.makeText(this, "총 거리 : "+totalDistance, Toast.LENGTH_SHORT).show();
            fragment.setTotalDistance(totalDistance);
        }).subscribe(success->{

        },fail->{
            Log.e("에러에러","왜? "+fail.toString());
        });
    }


    @Override
    public void drawPath(List<TransPosition> positions) {
        // 화면그리기 작성해야함
    }

    private void setLayout(){

        fromEditText = (EditText)findViewById(R.id.editText);
        toEditText = (EditText)findViewById(R.id.editText2);
        fromButton = (Button) findViewById(R.id.button2);
        fromButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                findRoute();
//                Intent intent = new Intent(MainActivity.this, SearchListActivity.class);
//                startActivity(intent);


            }
        });

        toButton = (Button) findViewById(R.id.button);
        toButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                Intent intent = new Intent(MainActivity.this, SearchListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void findRoute(){
        double startLa = 127.6433222;
        double startLo = 37.7965062;
        double destLa = 127.6423415;
        double destLo = 37.7944884;

        String url = "start=" + startLa + "," + startLo + "&destination=" + destLa + "," + destLo;

        Communicator.getHttp(url, new Handler() {
            public void handleMessage(Message msg) {

                String jsonString = msg.getData().getString("jsonString");
                Log.d("jsonString",jsonString);
                try {
                    JSONObject dataObject = new JSONObject(jsonString);
                    String result = dataObject.getString("result");

                    JSONObject dataObject2 = new JSONObject(result);
                    String summary = dataObject2.getString("summary");

                    Log.d("yurimmm summary", result);
                    JSONObject  tempObject = new JSONObject(summary);

                    int totalDistance = tempObject.getInt("totalDistance");
                    String toastString = totalDistance + " distance";

                    Toast.makeText(MainActivity.this, toastString , Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void hideKeyboard(){
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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
    }

    public void setInfo(boolean visible,String startTime,String endTime,int taskTime,double speed){
        if(visible)wrapper_info.setVisibility(View.VISIBLE);
        else {
            wrapper_info.setVisibility(View.INVISIBLE);
            return;
        }
        setStartTime(startTime);
        setEndTime(endTime);
        setTakeTime(taskTime);
        setSpeed(speed);
    }
}
