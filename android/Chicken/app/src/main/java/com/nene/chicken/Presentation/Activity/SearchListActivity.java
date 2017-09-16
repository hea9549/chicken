package com.nene.chicken.Presentation.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ListView;
import android.widget.TextView;

import com.nene.chicken.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SearchListActivity extends Activity {

    private ListView searchListView;
    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        Intent intent = getIntent();
        query = intent.getStringExtra("query");

        searchPosition();
    }

    private void searchPosition(){

        try {
            query = URLEncoder.encode(query,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = "query=" + query + "&display=10&start=1&sort=random";

        Communicator.getHttp(2, url, new Handler() {
            public void handleMessage(Message msg) {

                String jsonString = msg.getData().getString("jsonString");
                Log.d("jsonString",jsonString);

                try {
                    JSONObject dataObject = new JSONObject(jsonString);

                    String itemList = dataObject.getString("items");
                    JSONArray itemArray = new JSONArray(itemList);
                    JSONObject tempObject;

                    for (int i = 0; i < itemArray.length(); i++) {
                        String tempString = itemArray.getString(i);
                        tempObject = new JSONObject(tempString);

                        String title = tempObject.getString("title");
                        String roadAddress = tempObject.getString("roadAddress");
                        int mapx = tempObject.getInt("mapx");
                        int mapy = tempObject.getInt("mapy");

                        Log.d("yurimmm", "title    "+title + "   roadAddress   "  + roadAddress + "  mapx  "  + mapx + "      ");

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
