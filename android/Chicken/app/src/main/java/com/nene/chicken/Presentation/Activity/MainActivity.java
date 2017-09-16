package com.nene.chicken.Presentation.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends ChickenBaseActivity implements MainPresenter.View {
    MainPresenter presenter;
    private NMapView mMapView;// 지도 화면 View

    private EditText fromEditText;
    private EditText toEditText;
    private Button fromButton;
    private Button toButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setLayout();
        Log.e("좌표","" + NMapConverter.utmK2Grs(350111810, 149774298).getLatitude() + "," + NMapConverter.utmK2Grs(350111810, 149774298).getLongitude());
    }

    @Override
    public void drawPath(List<TransPosition> positions){
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
}
