package com.crazy.gy.ui.home;

import com.crazy.gy.base.BaseContract;
import com.crazy.gy.bean.Article;
import com.crazy.gy.bean.BannerBean;

import java.util.List;

/**
 * 作者：Administrator
 * 时间：2019/1/22
 * 功能：
 */
public interface HomeContract {
    interface View extends BaseContract.BaseView {
        void setBannerdate(List<BannerBean> bannerers);

        void sethomedate(Article articles, int type);

        void collectArticleSuccess(int position);

        void collectArticleFail(String error);

        void removeCollectArticleSuccess(int position);

        void removeCollectArticleFail(String error);

    }

    interface Presenter extends BaseContract.Basepresenter<View> {
        void getBannerdate();

        void gethomedate();

        void collectArticle(int id,int position);

        void removeCollectArticle(int id,int originId,int position);

        void refresh();

        void loadMore();
    }
}
