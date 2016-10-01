package com.egycps.abdulaziz.egycps.ui.magazine;

import com.egycps.abdulaziz.egycps.data.model.Magazine;
import com.egycps.abdulaziz.egycps.ui.base.BaseView;

import java.util.List;

/**
 * Created by abdulaziz on 10/1/16.
 */
public interface MagazineBaseView extends BaseView {

    void saveMagazinesCompleted();

    void saveMagazinesError(Throwable e);

    void syncMagazines(List<Magazine> offers);

    void syncMagazinesError(Throwable e);

    void syncMagazinesCompleted();

    void showMagazines(List<Magazine> offers);

    void showMagazinesError(Throwable e);

    void showMagazinesEmpty();
}
