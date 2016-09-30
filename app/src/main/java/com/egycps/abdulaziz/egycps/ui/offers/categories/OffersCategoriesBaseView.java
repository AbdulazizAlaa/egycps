package com.egycps.abdulaziz.egycps.ui.offers.categories;

import com.egycps.abdulaziz.egycps.data.model.OffersCategory;
import com.egycps.abdulaziz.egycps.ui.base.BaseView;

import java.util.List;

/**
 * Created by abdulaziz on 9/30/16.
 */
public interface OffersCategoriesBaseView extends BaseView{

    void syncCompleted();

    void syncOffersCategories(OffersCategory category);

    void syncError(Throwable e);

    void showOffersCategories(List<OffersCategory> categories);

    void showOffersCategoriesEmpty();

    void showError(Throwable e);

}
