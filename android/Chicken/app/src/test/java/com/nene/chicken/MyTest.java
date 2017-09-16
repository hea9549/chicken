package com.nene.chicken;

import com.nene.chicken.Model.TransPosition;
import com.nene.chicken.Util.DistanceUtil;
import com.nene.chicken.Util.GeoTrans;
import com.nene.chicken.Util.GeoTransPoint;
import com.nhn.android.maps.maplib.NMapConverter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ParkHaeSung on 2017-09-16.
 */

public class MyTest {
    @Test
    public void haha(){
        DistanceUtil distanceUtil = new DistanceUtil();
        TransPosition a = new TransPosition();
        a.setHeight(1);
        a.setLatitude(1);
        a.setLongitude(4);

        TransPosition b = new TransPosition();
        b.setHeight(3);
        b.setLatitude(2);
        b.setLongitude(6);

        List<TransPosition> lists = new ArrayList<>();
        lists.add(a);
        lists.add(b);
        System.out.println(""+distanceUtil.getTotalDistance(lists));
    }

    @Test
    public void distanceTest(){
        TransPosition chungMain = new TransPosition(35.235805, 129.076234);
        chungMain.setHeight(184.9628753662109);
        TransPosition chungTop = new TransPosition(35.231686, 129.084002);
        chungTop.setHeight(30.41781044006348);

        System.out.println("높이있으면 거리 : "+DistanceUtil.calDistance(chungMain,chungTop));
        chungMain.setHeight(0f);
        chungTop.setHeight(0f);
        System.out.println("높이없으면 거리 : "+DistanceUtil.calDistance(chungMain,chungTop));
    }

    @Test
    public void geoTransTest(){
        GeoTransPoint oKA = new GeoTransPoint(309252, 550759);
        GeoTransPoint oGeo = GeoTrans.convert(GeoTrans.KATEC, GeoTrans.GEO, oKA);
        Double lat = oGeo.getY();
        Double lng = oGeo.getX();
        System.out.println("lat = "+lat+",lng = "+lng);
    }
}
