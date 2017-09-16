package com.nene.chicken.Presentation.Presenter;

import com.nene.chicken.Model.TransPosition;
import com.nhn.android.maps.maplib.NGeoPoint;

import java.util.List;

/**
 * Created by ParkHaeSung on 2017-09-16.
 */

public interface MainMapPresenter {
    void setView(View view);
    interface View extends BaseViewPresenter{
        void drawPath(List<TransPosition> positions);
    }
}
