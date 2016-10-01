package com.egycps.abdulaziz.egycps.ui.offers.categories;

import android.graphics.Bitmap;

import com.egycps.abdulaziz.egycps.data.model.OffersCategory;
import com.egycps.abdulaziz.egycps.ui.base.BaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdulaziz on 9/30/16.
 */
public interface OffersCategoriesBaseView extends BaseView{

    void saveOffersCategoriesCompleted();

    void syncOffersCategoriesCompleted();

    void syncOffersCategories(ArrayList<OffersCategory> category);

    void syncOffersCategoriesError(Throwable e);

    void showOffersCategories(List<OffersCategory> categories);

    void showOffersCategoriesEmpty();

    void showOffersCategoriesError(Throwable e);

}
