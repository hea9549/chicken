package com.nene.chicken.Presentation.Presenter;

import com.nene.chicken.Model.TransPosition;
import com.nene.chicken.Service.DistanceService;
import com.nene.chicken.Service.DistanceServiceImpl;
import com.nene.chicken.Util.DistanceUtil;
import com.nhn.android.maps.maplib.NGeoPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ParkHaeSung on 2017-09-16.
 */

public class MainPresenterImpl implements MainPresenter {
    private View view;
    private DistanceService distanceService;
    private List<TransPosition> savePositions;
    private double curSpeed;
    public MainPresenterImpl(){
        distanceService = new DistanceServiceImpl();
        savePositions = new ArrayList<>();
    }
    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public double getSpeed(NGeoPoint point){
        TransPosition transPosition = new TransPosition(point.getLatitude(),point.getLongitude());
        savePositions.add(transPosition);
        if(savePositions.size()==0) return 0;
        double totalMovedistance = 0;
        for(int i = 0 ; i < savePositions.size()-1;i++){
            totalMovedistance += DistanceUtil.calDistance(savePositions.get(i).getLatitude(),savePositions.get(i).getLongitude(),
                    savePositions.get(i+1).getLatitude(),savePositions.get(i+1).getLongitude());
        }
        return totalMovedistance/(savePositions.size()-1);
    }
}
