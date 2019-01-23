package com.crazy.gy.ui.home;

import com.crazy.gy.base.BasePresenter;
import com.crazy.gy.bean.Article;
import com.crazy.gy.bean.BannerBean;
import com.crazy.gy.bean.DataResponse;
import com.crazy.gy.net.ApiServer;
import com.crazy.gy.net.RetrofitManager;
import com.crazy.gy.net.RxSchedulers;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * 作者：Administrator
 * 时间：2019/1/22
 * 功能：
 */
public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {
    private int mPage = 0;

    private boolean isRefresh = true;

    @Override
    public void getBannerdate() {
        RetrofitManager.create(ApiServer.class)
                .getHomeBanners()
                .compose(RxSchedulers.<DataResponse<List<BannerBean>>>applySchedulers())
                .compose(mView.<DataResponse<List<BannerBean>>>bindToLife())
                .subscribe(new Consumer<DataResponse<List<BannerBean>>>() {
                    @Override
                    public void accept(DataResponse<List<BannerBean>> listDataResponse) throws Exception {
                        mView.setBannerdate(listDataResponse.getData());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    @Override
    public void gethomedate() {
        RetrofitManager.create(ApiServer.class)
                .getHomeArticles(mPage)
                .compose(RxSchedulers.<DataResponse<Article>>applySchedulers())
                .compose(mView.<DataResponse<Article>>bindToLife())
                .subscribe(new Consumer<DataResponse<Article>>() {
                    @Override
                    public void accept(DataResponse<Article> articleDataResponse) throws Exception {
                        if (isRefresh) {
                            mView.sethomedate(articleDataResponse.getData(), 0);
                        } else {
                            mView.sethomedate(articleDataResponse.getData(), 1);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    @Override
    public void collectArticle(int id, final int position) {
        RetrofitManager.create(ApiServer.class)
                .collectArticle(id)
                .compose(RxSchedulers.<DataResponse>applySchedulers())
                .compose(mView.<DataResponse>bindToLife())
                .subscribe(new Consumer<DataResponse>() {
                    @Override
                    public void accept(DataResponse listDataResponse) throws Exception {
                        if (listDataResponse.getErrorCode() == 0) {
                            mView.collectArticleSuccess(position);
                        } else {
                            mView.collectArticleFail(String.valueOf(listDataResponse.getErrorMsg()));
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.collectArticleFail(String.valueOf(throwable.getMessage()));
                    }
                });

    }

    @Override
    public void removeCollectArticle(int id, int originId,final int position) {
        RetrofitManager.create(ApiServer.class)
                .removeCollectArticle(id,originId)
                .compose(RxSchedulers.<DataResponse>applySchedulers())
                .compose(mView.<DataResponse>bindToLife())
                .subscribe(new Consumer<DataResponse>() {
                    @Override
                    public void accept(DataResponse listDataResponse) throws Exception {
                        if (listDataResponse.getErrorCode() == 0) {
                            mView.removeCollectArticleSuccess(position);
                        } else {
                            mView.removeCollectArticleFail(String.valueOf(listDataResponse.getErrorMsg()));
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.removeCollectArticleFail(String.valueOf(throwable.getMessage()));
                    }
                });
    }


    @Override
    public void refresh() {
        mPage = 0;
        isRefresh = true;
        getBannerdate();
        gethomedate();
    }

    @Override
    public void loadMore() {
        mPage++;
        isRefresh = false;
        gethomedate();
    }
}
