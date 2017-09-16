package com.nene.chicken.Presentation.Activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nene.chicken.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by YURIM on 2017-09-16.
 */

public class SearchListAdapter extends ArrayAdapter<SearchResultInfo> {

    private ArrayList<SearchResultInfo> items;
    private Context mContext;
    private ViewHolder viewHolder;

    public SearchListAdapter(Context context, int textViewResourceId, ArrayList<SearchResultInfo> items) {
        super(context, textViewResourceId, items);

        this.items = items;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_item_search, null);

//            viewHolder.distTextView = (TextView)v.findViewById(R.id.dist_TextView);
            viewHolder.titleTextView = (TextView)v.findViewById(R.id.title_TextView);
            viewHolder.addressTextView = (TextView)v.findViewById(R.id.address_TextView);

            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) v.getTag();
        }

        final SearchResultInfo searchResultInfo = items.get(position);

        if (searchResultInfo != null) {
            viewHolder.titleTextView.setText(searchResultInfo.getTitle());
            viewHolder.addressTextView.setText(searchResultInfo.getRoadAddress());

            final int mapx = searchResultInfo.getMapx();
            final int mapy = searchResultInfo.getMapy();

            LinearLayout selectLayout = (LinearLayout)v.findViewById(R.id.select_Layout);
            selectLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,"this x : " + mapx,Toast.LENGTH_SHORT);
//                    Intent intent = new Intent(getContext(), SendMessagePopupActivity.class);
//                    intent.putExtra("friendsId",friendsId);
//                    intent.putExtra("username",username);
//                    MLog.d(username);
//                    MLog.d(friendsId);
//                    mContext.startActivity(intent);
                }
            });
        }
        return v;
    }

    class ViewHolder{
//        public  TextView distTextView;
        public  TextView titleTextView;
        public  TextView addressTextView;
    }
}
