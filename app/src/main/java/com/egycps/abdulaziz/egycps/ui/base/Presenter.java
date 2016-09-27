package com.egycps.abdulaziz.egycps.ui.base;

/**
 * Created by abdulaziz on 9/27/16.
 *
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MvpView type that wants to be attached with.
 *
 */
public interface Presenter<V extends BaseView> {

    void attachView(V baseView);

    void detachView();

}
