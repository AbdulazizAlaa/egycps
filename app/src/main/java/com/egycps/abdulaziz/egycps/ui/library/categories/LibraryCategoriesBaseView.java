package com.egycps.abdulaziz.egycps.ui.library.categories;

import com.egycps.abdulaziz.egycps.data.model.Category;
import com.egycps.abdulaziz.egycps.ui.base.BaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdulaziz on 10/2/16.
 */
public interface LibraryCategoriesBaseView extends BaseView{

    void saveLibraryCategoriesCompleted();

    void syncLibraryCategoriesCompleted();

    void syncLibraryCategories(ArrayList<Category> category);

    void syncLibraryCategoriesError(Throwable e);

    void showLibraryCategories(List<Category> categories);

    void showLibraryCategoriesEmpty();

    void showLibraryCategoriesError(Throwable e);

}
