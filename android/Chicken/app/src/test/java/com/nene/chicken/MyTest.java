package com.nene.chicken;

import com.nene.chicken.Model.TransPosition;
import com.nene.chicken.Util.DistanceUtil;
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

}
