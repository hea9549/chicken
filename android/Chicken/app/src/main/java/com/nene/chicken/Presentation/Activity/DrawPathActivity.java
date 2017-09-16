package com.nene.chicken.Presentation.Activity;

import com.nene.chicken.Model.TransPosition;
import com.nene.chicken.Presentation.Presenter.MainPresenter;
import com.nhn.android.maps.overlay.NMapPathData;
import com.nhn.android.mapviewer.overlay.NMapPathDataOverlay;

import java.util.List;

/**
 * Created by 관리자 on 2017-09-16.
 */

public class DrawPathActivity extends ChickenBaseActivity implements MainPresenter.View{

    @Override
    public void drawPath(List<TransPosition> positions){
        // 화면그리기 작성해야함
        NMapPathData pathData = new NMapPathData(positions.size());

        pathData.initPathData();
        for(int i=0;i<positions.size();i++)
        {
            pathData.addPathPoint(positions.get(i).getLongitude(), positions.get(i).getLatitude(), 0);
        }
        pathData.endPathData();

        //NMapPathDataOverlay pathDataOverlay = mOverlayManager.createPathDataOverlay(pathData);
    }
}
