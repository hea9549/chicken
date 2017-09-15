package com.nene.chicken.Presentation.Presenter;

/**
 * Created by ParkHaeSung on 2017-09-16.
 */

public class MainPresenterImpl implements MainPresenter {
    private View view;
    @Override
    public void setView(View view) {
        this.view = view;
    }
}
