package com.egycps.abdulaziz.egycps.ui.news;

import com.egycps.abdulaziz.egycps.data.model.News;
import com.egycps.abdulaziz.egycps.ui.base.BaseView;

import java.util.List;

/**
 * Created by abdulaziz on 10/1/16.
 */
public interface NewsBaseView extends BaseView{

    void saveNewsCompleted();

    void saveNewsError(Throwable e);

    void syncNews(List<News> offers);

    void syncNewsError(Throwable e);

    void syncNewsCompleted();

    void showNews(List<News> offers);

    void showNewsError(Throwable e);

    void showNewsEmpty();

}
