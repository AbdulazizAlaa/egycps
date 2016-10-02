package com.egycps.abdulaziz.egycps.ui.library.list;

import com.egycps.abdulaziz.egycps.data.model.Book;
import com.egycps.abdulaziz.egycps.ui.base.BaseView;

import java.util.List;

/**
 * Created by abdulaziz on 10/2/16.
 */
public interface LibraryListBaseView extends BaseView{

    void saveBooksCompleted();

    void saveBooksError(Throwable e);

    void syncBooks(List<Book> books);

    void syncBooksError(Throwable e);

    void syncBooksCompleted();

    void showBooks(List<Book> books);

    void showBooksError(Throwable e);

    void showBooksEmpty();

}
