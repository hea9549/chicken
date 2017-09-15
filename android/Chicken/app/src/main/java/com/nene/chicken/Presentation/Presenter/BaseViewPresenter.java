package com.nene.chicken.Presentation.Presenter;

import java.io.Serializable;

/**
 * Created by ParkHaeSung on 2017-09-16.
 */

public interface BaseViewPresenter {
    void makeToast(String message);
    void navigateActivity(Class navigateClass);

    void navigateActivity(Class navigateClass, Serializable... datas);

    void popActivity(Class popClass);

    void popActivity(Class popClass, Serializable... datas);
}
