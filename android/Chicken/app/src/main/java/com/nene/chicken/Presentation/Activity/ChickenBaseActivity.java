package com.nene.chicken.Presentation.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.nene.chicken.Presentation.Presenter.BaseViewPresenter;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.Serializable;

/**
 * Created by ParkHaeSung on 2017-09-16.
 */

public class ChickenBaseActivity extends AppCompatActivity implements BaseViewPresenter {


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }


    @Override
    public void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateActivity(Class navigateClass) {
        Intent intent = new Intent(this,navigateClass);
        startActivity(intent);
        finish();
    }

    @Override
    public void navigateActivity(Class navigateClass, Serializable... datas){
        Intent intent = new Intent(this,navigateClass);
        if (datas.length%2 == 1)throw new IllegalArgumentException("navigate params not match. please make pair...");
        for(int i = 0;i<datas.length;i+=2){
            intent.putExtra((String)datas[i],datas[i+1]);
        }
        startActivity(intent);
        finish();
    }


    @Override
    public void popActivity(Class popClass) {
        Intent intent = new Intent(this,popClass);
        startActivity(intent);
    }

    @Override
    public void popActivity(Class popClass, Serializable... datas){
        Intent intent = new Intent(this,popClass);
        if (datas.length%2 == 1)throw new IllegalArgumentException("popClass params not match. please make pair...");
        for(int i = 0;i<datas.length;i+=2){
            intent.putExtra((String)datas[i],datas[i+1]);
        }
        startActivity(intent);
    }
}
