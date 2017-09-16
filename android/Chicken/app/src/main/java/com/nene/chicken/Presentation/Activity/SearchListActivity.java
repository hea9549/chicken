package com.nene.chicken.Presentation.Activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ListView;
import android.widget.TextView;

import com.nene.chicken.R;

public class SearchListActivity extends Activity {

    ListView searchListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);
    }
}
