package com.crazy.gy.ui.home;


import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.crazy.gy.BaseFragment;
import com.crazy.gy.R;
import com.crazy.gy.bean.Article;
import com.crazy.gy.bean.BannerBean;
import com.crazy.gy.util.Constant;
import com.crazy.gy.util.DialogText;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.crazy.gy.R.id.banner;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View, SwipeRefreshLayout.OnRefreshListener, HomeAdapter.RequestLoadMoreListener {


    @BindView(R.id.tv_titlecontent)
    TextView tvTitlecontent;
    @BindView(R.id.homerecyclerview)
    RecyclerView homerecyclerview;
    @BindView(R.id.refreshdata)
    SwipeRefreshLayout swipeRefreshData;

    private DialogText mDialog;
    private LinearLayoutManager linearLayoutManager;
    private View bannerView;
    private Banner mBanner;
    private HomeAdapter mhomeAdapter;
    private List<Article.DatasBean> marticle = new ArrayList<>();
    private int PAGE_SIZE = 20;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initInjector() {
        mDialog = new DialogText(getActivity(), R.style.MyDialog);
        mPresenter = new HomePresenter();
    }

    @Override
    protected void initView(View view) {
        tvTitlecontent.setText("首页");
        //下拉刷新监听
        swipeRefreshData.setOnRefreshListener(this);
        //设置加载进度的颜色变化值
        swipeRefreshData.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        homerecyclerview.setLayoutManager(linearLayoutManager);
        //设置适配器
        homerecyclerview.setAdapter(mhomeAdapter = new HomeAdapter(R.layout.homefragment_item, marticle));
        //头布局
        bannerView = LayoutInflater.from(getActivity()).inflate(R.layout.bannerview, null);
        mBanner = bannerView.findViewById(banner);
        mhomeAdapter.addHeaderView(bannerView);
        mhomeAdapter.setOnLoadMoreListener(this);
        //数据请求
        mPresenter.getBannerdate();
        mPresenter.gethomedate();
        //子控件的点击事件
        mhomeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.ivCollect:
                        //判断有没有登录
                        if (SPUtils.getInstance(Constant.SPname).getBoolean(Constant.LOGIN)) {
                            //判断之前是否有收藏
                            if (mhomeAdapter.getItem(position).isCollect()) {
                                //取消收藏
                                mPresenter.removeCollectArticle(mhomeAdapter.getItem(position).getId(), SPUtils.getInstance(Constant.SPname).getInt("loginid"), position);
                            } else {
                                //收藏
                                mPresenter.collectArticle(mhomeAdapter.getItem(position).getId(), position);
                            }
                        } else {
                            mDialog.show();
                            showInfo("未登录", 0);
                        }
                        break;
                }
            }
        });
    }


    @Override
    public void setBannerdate(List<BannerBean> bannerers) {
        List<String> imagesPaths = new ArrayList();
        for (BannerBean banner : bannerers) {
            imagesPaths.add(banner.getImagePath());
        }
        List<String> imagesTitles = new ArrayList<>();
        for (BannerBean banner : bannerers) {
            imagesPaths.add(banner.getTitle());
        }
        mBanner.setBannerStyle(BannerConfig.NOT_INDICATOR)
                .setBannerTitles(imagesTitles)
                .setDelayTime(2000)
                .setImages(imagesPaths).setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load((String) path).into(imageView);
            }

        }).setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                //跳转到相应的网页
//                Intent intent = new Intent();
//                intent.setData(Uri.parse(bannerList.get(position).getUrl()));
//                intent.setAction(Intent.ACTION_VIEW);
//                startActivity(intent);
                //为了学习九宫格的图片展示，这里用来展示九宫格图片 新建一个activity
//                Intent intent = new Intent(getActivity(), ShowNineGrideActivity.class);
//                intent.putStringArrayListExtra("pathList", pathList);
//                startActivity(intent);
            }
        }).start();
    }

    @Override
    public void sethomedate(Article articles, int type) {
        final List mData = articles.getDatas();
        final int size = mData == null ? 0 : mData.size();
        if (type == 0) {//刷新
            mhomeAdapter.setNewData(articles.getDatas());
            swipeRefreshData.setRefreshing(false);
            if (size < PAGE_SIZE) {
                //第一页如果不够一页就不显示没有更多数据布局
                mhomeAdapter.loadMoreEnd(true);

            } else {
                mhomeAdapter.loadMoreComplete();
            }
        } else if (type == 1) {//加载
            if (size > 0) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (size < PAGE_SIZE) {
                            //第一页如果不够一页就不显示没有更多数据布局(比如只有十三条数据)
                            mhomeAdapter.loadMoreEnd(false);

                        } else {
                            mhomeAdapter.loadMoreComplete();
                        }
                        mhomeAdapter.addData(mData);
                    }
                }, 1000);

            }
        }
    }

    /**
     * 收藏成功
     */
    @Override
    public void collectArticleSuccess(int position) {
        mhomeAdapter.getItem(position).setCollect(!mhomeAdapter.getItem(position).isCollect());
        mhomeAdapter.notifyDataSetChanged();
    }


    /**
     * 收藏失败
     */
    @Override
    public void collectArticleFail(String error) {
        mDialog.show();
        showInfo(error, 0);
    }

    /**
     * 取消收藏成功
     */
    @Override
    public void removeCollectArticleSuccess(int position) {
        mhomeAdapter.getItem(position).setCollect(!mhomeAdapter.getItem(position).isCollect());
        mhomeAdapter.notifyDataSetChanged();
    }

    /**
     * 取消收藏失败
     */
    @Override
    public void removeCollectArticleFail(String error) {
        mDialog.show();
        showInfo(error, 0);
    }

    @Override
    public void onRefresh() {
        mPresenter.refresh();
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadMore();
    }

    /**
     * @param errorString
     * @param is
     */
    public void showInfo(final String errorString, final int is) {
        String isString = "";
        if (is == 1) {
            isString = "success";
        }
        if (is == 0) {
            isString = "fail";
        }
        if (mDialog != null) {
            mDialog.setText(errorString, isString);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mDialog.dismiss();
                }
            }, 1000);
        }
    }
}
