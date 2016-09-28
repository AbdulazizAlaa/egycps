package com.egycps.abdulaziz.egycps.ui.splash;

import com.egycps.abdulaziz.egycps.data.model.OffersCategory;
import com.egycps.abdulaziz.egycps.ui.base.BaseView;

import java.util.List;

/**
 * Created by abdulaziz on 9/27/16.
 */
public interface SplashBaseView extends BaseView{

    void saveCategories(List<OffersCategory> categories);

    void showError();

}
