package com.nene.chicken.Presentation.Presenter;

/**
 * Created by ParkHaeSung on 2017-09-16.
 */

public interface MainPresenter {
    void setView(View view);
    interface View extends BaseViewPresenter{

    }
}
