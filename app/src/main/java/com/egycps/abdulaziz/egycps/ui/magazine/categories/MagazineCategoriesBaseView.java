package com.egycps.abdulaziz.egycps.ui.magazine.categories;

import com.egycps.abdulaziz.egycps.data.model.Category;
import com.egycps.abdulaziz.egycps.ui.base.BaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdulaziz on 1/23/17.
 */

public interface MagazineCategoriesBaseView extends BaseView {

    void saveMagazineCategoriesCompleted();

    void syncMagazineCategoriesCompleted();

    void syncMagazineCategories(ArrayList<Category> category);

    void syncMagazineCategoriesError(Throwable e);

    void showMagazineCategories(List<Category> categories);

    void showMagazineCategoriesEmpty();

    void showMagazineCategoriesError(Throwable e);

}
