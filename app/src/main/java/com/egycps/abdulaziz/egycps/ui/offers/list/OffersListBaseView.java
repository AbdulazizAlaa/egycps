package com.egycps.abdulaziz.egycps.ui.offers.list;

import com.egycps.abdulaziz.egycps.data.model.Offer;
import com.egycps.abdulaziz.egycps.ui.base.BaseView;

import java.util.List;

/**
 * Created by abdulaziz on 10/1/16.
 */
public interface OffersListBaseView extends BaseView{

    void saveOffersListCompleted();

    void saveOffersListError(Throwable e);

    void syncOffersList(List<Offer> offers);

    void syncOffersListError(Throwable e);

    void syncOffersListCompleted();

    void showOffersList(List<Offer> offers);

    void showOffersListError(Throwable e);

    void showOffersListEmpty();

}
