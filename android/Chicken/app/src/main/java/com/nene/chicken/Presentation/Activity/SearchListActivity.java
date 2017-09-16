package com.nene.chicken.Presentation.Activity;

import android.app.Activity;
import android.app.SearchableInfo;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nene.chicken.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.ArrayList;

public class SearchListActivity extends ChickenBaseActivity {

    private String query;
    private int type;
    private SearchListAdapter searchListAdapter;
    private ListView searchListView;
    private ArrayList<SearchResultInfo> searchResultInfoList = new ArrayList<SearchResultInfo>();
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        Intent intent = getIntent();
        query = intent.getStringExtra("query");
        type = intent.getIntExtra("type",0);

        setLayout();

    }

    private void setLayout(){
        searchListView = (ListView) findViewById(R.id.search_listview);
        searchListAdapter = new SearchListAdapter(this, R.layout.list_item_search, searchResultInfoList);
        searchListView.setAdapter(searchListAdapter);
        searchListView.setOnItemClickListener(searchItemClickListener);
        searchListView.setDividerHeight(0);

        backButton = (ImageButton)findViewById(R.id.back_Button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        searchPosition();
    }

    private AdapterView.OnItemClickListener searchItemClickListener = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long l_position) {
            int mapx = searchResultInfoList.get(position).getMapx();
            int mapy = searchResultInfoList.get(position).getMapy();
            String title = searchResultInfoList.get(position).getTitle();

            Intent intent = new Intent();
            intent.putExtra("title",title);
            if(type == 1){
                intent.putExtra("mapxFrom",mapx);
                intent.putExtra("mapyFrom",mapy);
            }else{
                intent.putExtra("mapxTo",mapx);
                intent.putExtra("mapyTo",mapy);
            }
            setResult(RESULT_OK, intent);

            finish();

        }
    };

    private void searchPosition(){
        searchResultInfoList.clear();

        try {
            query = URLEncoder.encode(query,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = "query=" + query + "&display=10&start=1&sort=random";

        Communicator.getHttp(2, url, new Handler() {
            public void handleMessage(Message msg) {

                searchResultInfoList.clear();
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
                        title = title.replace("<b>","");
                        title = title.replace("</b>","");

                        String roadAddress = tempObject.getString("roadAddress");
                        int mapx = tempObject.getInt("mapx");
                        int mapy = tempObject.getInt("mapy");

                        searchResultInfoList.add(new SearchResultInfo(title, roadAddress, mapx, mapy));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                searchListAdapter.notifyDataSetChanged();
            }
        });
    }

}
