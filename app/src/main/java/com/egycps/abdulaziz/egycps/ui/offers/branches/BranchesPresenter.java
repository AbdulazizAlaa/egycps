package com.egycps.abdulaziz.egycps.ui.offers.branches;

import android.content.Context;
import android.util.Log;

import com.egycps.abdulaziz.egycps.data.DataManager;
import com.egycps.abdulaziz.egycps.data.model.Branch;
import com.egycps.abdulaziz.egycps.ui.base.BasePresenter;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by abdulaziz on 10/2/16.
 */
public class BranchesPresenter extends BasePresenter<BranchesBaseView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;
    private final Context mContext;

    public BranchesPresenter(Context mContext, DataManager mDataManager) {
        this.mContext = mContext;
        this.mDataManager = mDataManager;
    }

    @Override
    public void attachView(BranchesBaseView baseView) {
        super.attachView(baseView);
    }

    @Override
    public void detachView() {
        super.detachView();

        if(mSubscription!=null) mSubscription.unsubscribe();
    }


    public void syncBranches(String offer_id){
        Log.i(GlobalEntities.BRANCHES_PRESENTER_TAG, "syncBranches: ");
        checkViewAttached();
        mSubscription = mDataManager.syncBranches(offer_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ArrayList<Branch>>() {
                    @Override
                    public void onCompleted() {
                        Log.i(GlobalEntities.BRANCHES_PRESENTER_TAG, "syncBranches: Completed");
                        getBaseView().syncBranchesListCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(GlobalEntities.BRANCHES_PRESENTER_TAG, "syncBranches: Error: "+e.getMessage());
                        getBaseView().syncBranchesListError(e);
                    }

                    @Override
                    public void onNext(ArrayList<Branch> branches) {
                        Log.i(GlobalEntities.BRANCHES_PRESENTER_TAG, "syncBranches: onNext: "+branches.size());
                        for(Branch b : branches){
                            Log.i(GlobalEntities.BRANCHES_PRESENTER_TAG, "syncBranches: "+b.getName());

                        }
                        getBaseView().syncBranchesList(branches);
                    }
                });

    }

    public void saveBranches(ArrayList<Branch> branches){
        Log.i(GlobalEntities.BRANCHES_PRESENTER_TAG, "saveBranches: ");
        checkViewAttached();
        mSubscription = mDataManager.setBranches(branches)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Branch>() {
                    @Override
                    public void onCompleted() {
                        Log.i(GlobalEntities.BRANCHES_PRESENTER_TAG, "saveBranches: Completed");
                        getBaseView().saveBranchesListCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(GlobalEntities.BRANCHES_PRESENTER_TAG, "saveBranches: Error: "+e.getMessage());
                        getBaseView().saveBranchesListError(e);
                    }

                    @Override
                    public void onNext(Branch branch) {
                        Log.i(GlobalEntities.BRANCHES_PRESENTER_TAG, "saveBranches: onNext: "+branch.getId());

                    }
                });
    }

    public void loadBranches(String offer_id){
        Log.i(GlobalEntities.BRANCHES_PRESENTER_TAG, "loadBranches: ");
        checkViewAttached();
        mSubscription = mDataManager.getBranches(offer_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Branch>>() {
                    @Override
                    public void onCompleted() {
                        Log.i(GlobalEntities.BRANCHES_PRESENTER_TAG, "loadBranches: ");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(GlobalEntities.BRANCHES_PRESENTER_TAG, "loadBranches: Error: "+e.getMessage());
                        getBaseView().showBranchesListError(e);
                    }

                    @Override
                    public void onNext(List<Branch> branches) {
                        Log.i(GlobalEntities.BRANCHES_PRESENTER_TAG, "loadBranches: onNext");
                        if(branches.isEmpty()){
                            getBaseView().showBranchesListEmpty();
                        }else{
                            getBaseView().showBranchesList(branches);
                        }
                    }
                });
    }
}