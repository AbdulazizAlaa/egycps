package com.egycps.abdulaziz.egycps.ui.offers.branches;

import com.egycps.abdulaziz.egycps.data.model.Branch;
import com.egycps.abdulaziz.egycps.ui.base.BaseView;

import java.util.List;

/**
 * Created by abdulaziz on 10/2/16.
 */
public interface BranchesBaseView extends BaseView {

    void saveBranchesListCompleted();

    void saveBranchesListError(Throwable e);

    void syncBranchesList(List<Branch> branches);

    void syncBranchesListError(Throwable e);

    void syncBranchesListCompleted();

    void showBranchesList(List<Branch> branches);

    void showBranchesListError(Throwable e);

    void showBranchesListEmpty();

}
