package com.nene.chicken.Presentation.Fragment;

import android.support.v4.app.Fragment;

import com.nene.chicken.Presentation.Presenter.BaseViewPresenter;

import java.io.Serializable;

/**
 * Created by ParkHaeSung on 2017-09-16.
 */

public class ChickenBaseFragment extends Fragment implements BaseViewPresenter{

    @Override
    public void makeToast(String message) {

    }

    @Override
    public void navigateActivity(Class navigateClass) {

    }

    @Override
    public void navigateActivity(Class navigateClass, Serializable... datas) {

    }

    @Override
    public void popActivity(Class popClass) {

    }

    @Override
    public void popActivity(Class popClass, Serializable... datas) {

    }
}
