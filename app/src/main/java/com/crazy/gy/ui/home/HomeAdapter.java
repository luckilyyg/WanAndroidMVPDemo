package com.crazy.gy.ui.home;

import android.support.annotation.Nullable;
import android.text.Html;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.crazy.gy.R;
import com.crazy.gy.bean.Article;

import java.util.List;

/**
 * 作者：Administrator
 * 时间：2019/1/23
 * 功能：
 */
public class HomeAdapter extends BaseQuickAdapter<Article.DatasBean,BaseViewHolder> {

    public HomeAdapter(int layoutResId, @Nullable List<Article.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Article.DatasBean item) {
        helper.setImageResource(R.id.img_authorheadpic, R.drawable.icon_user);
        helper.setText(R.id.tv_author, item.getAuthor());
        helper.setText(R.id.tv_publishdate, item.getNiceDate());
        helper.setText(R.id.tv_contenttitle, Html.fromHtml(item.getTitle()));
        helper.setText(R.id.tvChapterName, item.getChapterName());
        helper.setImageResource(R.id.ivCollect, item.isCollect() ? R.drawable.ic_collect : R.drawable.ic_nocollect);
        helper.addOnClickListener(R.id.ivCollect);
    }
}
