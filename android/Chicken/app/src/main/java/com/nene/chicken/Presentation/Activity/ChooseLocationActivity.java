package com.nene.chicken.Presentation.Activity;

import android.app.Activity;
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

import com.nene.chicken.Model.TransPosition;
import com.nene.chicken.R;
import com.nene.chicken.Util.GeoTrans;
import com.nene.chicken.Util.GeoTransPoint;
import com.nhn.android.maps.maplib.NMapConverter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ChooseLocationActivity extends Activity {

    private EditText fromEditText;
    private EditText toEditText;
    private Button fromButton;
    private Button toButton;
    private Button findButton;

    private int mapxFrom;
    private int mapyFrom;
    private int mapxTo;
    private int mapyTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location);

        setLayout();
    }


    private double convertLatitude(int x, int y){
        return NMapConverter.utmK2Grs(x, y).getLatitude(); // 카텍좌표계 변환(9자리)
    }

    private double convertLongitude(int x, int y){
        return NMapConverter.utmK2Grs(x, y).getLongitude();
    }



    public static final int FROM_ACTIVITY_RESULT = 1;
    public static final int TO_ACTIVITY_RESULT = 2;

    //SearchListActivity가 종료된 이후 아래 함수에서 데이터를 수신
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode)           // Request code send from "startActivityForResult" function
            {
                case FROM_ACTIVITY_RESULT: {
                    mapxFrom = data.getIntExtra("mapxFrom", -1);
                    mapyFrom = data.getIntExtra("mapyFrom", -1);
                    break;
                }
                case TO_ACTIVITY_RESULT: {
                    mapxTo = data.getIntExtra("mapxTo", -1);
                    mapyTo = data.getIntExtra("mapyTo", -1);
                    break;
                }
            }
        }
    }

    private void setLayout(){
        fromEditText = (EditText)findViewById(R.id.from_EditText);
        toEditText = (EditText)findViewById(R.id.to_EditText);

        fromButton = (Button) findViewById(R.id.button2);
        fromButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = fromEditText.getText().toString();

                hideKeyboard();
                Intent intent = new Intent(ChooseLocationActivity.this, SearchListActivity.class);
                intent.putExtra("query",query);
                intent.putExtra("type",FROM_ACTIVITY_RESULT);
                startActivityForResult(intent, FROM_ACTIVITY_RESULT);
            }
        });

        toButton = (Button) findViewById(R.id.button);
        toButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = toEditText.getText().toString();

                hideKeyboard();
                Intent intent = new Intent(ChooseLocationActivity.this, SearchListActivity.class);
                intent.putExtra("query",query);
                intent.putExtra("type",TO_ACTIVITY_RESULT);
                startActivityForResult(intent, TO_ACTIVITY_RESULT);
            }
        });

        findButton = (Button)findViewById(R.id.fine_Button);
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findRoute();
            }
        });
    }

    private void findRoute(){
//        카텍좌표계 변환(6자리)
        GeoTransPoint oKAFrom = new GeoTransPoint(mapxFrom, mapyFrom);
        GeoTransPoint oGeoFrom = GeoTrans.convert(GeoTrans.KATEC, GeoTrans.GEO, oKAFrom);
        GeoTransPoint oKATo = new GeoTransPoint(mapxTo, mapyTo);
        GeoTransPoint oGeoTo = GeoTrans.convert(GeoTrans.KATEC, GeoTrans.GEO, oKATo);
        double startLa = oGeoFrom.getY();
        double startLo = oGeoFrom.getX();
        double destLa = oGeoTo.getY();
        double destLo = oGeoTo.getX();
//
        String url = "start=" + startLo + "," + startLa + "&destination=" + destLo + "," + destLa;

        Communicator.getHttp(1, url, new Handler() {
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

                    Toast.makeText(ChooseLocationActivity.this, toastString , Toast.LENGTH_SHORT).show();

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
