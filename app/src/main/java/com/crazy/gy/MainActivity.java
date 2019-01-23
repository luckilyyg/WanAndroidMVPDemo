package com.crazy.gy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.crazy.gy.adapter.MyPageAdapter;
import com.crazy.gy.ui.home.HomeFragment;
import com.crazy.gy.ui.knowledge.KnowledgeFragment;
import com.crazy.gy.ui.project.ProjectFragment;
import com.crazy.gy.ui.todo.ToDoFragment;
import com.crazy.gy.view.NoScrollViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    @BindView(R.id.vp_homepage_content)
    NoScrollViewPager vpHomePager;
    @BindView(R.id.id_homepage_tablayout)
    TabLayout mBottomNavigationBar;
    @BindView(R.id.drawer_navigation)
    NavigationView drawerNavigation;
    private ArrayList<Fragment> mFragmentList;


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initInjector() {
    }

    @Override
    public void initView() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new HomeFragment());
        mFragmentList.add(new KnowledgeFragment());
        mFragmentList.add(new ProjectFragment());
        mFragmentList.add(new ToDoFragment());
        mBottomNavigationBar.addTab(mBottomNavigationBar.newTab());
        mBottomNavigationBar.addTab(mBottomNavigationBar.newTab());
        mBottomNavigationBar.addTab(mBottomNavigationBar.newTab());
        mBottomNavigationBar.addTab(mBottomNavigationBar.newTab());
        //缓存3个Fragment
        vpHomePager.setOffscreenPageLimit(3);
        //通过适配器把Fragment添加到主界面上
        vpHomePager.setAdapter(new MyPageAdapter(getSupportFragmentManager(), mFragmentList));
        //把tab和ViewPager关联起来,并会将文字清空
        mBottomNavigationBar.setupWithViewPager(vpHomePager);
        mBottomNavigationBar.getTabAt(0).setCustomView(getCustomView(0));
        mBottomNavigationBar.getTabAt(1).setCustomView(getCustomView(1));
        mBottomNavigationBar.getTabAt(2).setCustomView(getCustomView(2));
        mBottomNavigationBar.getTabAt(3).setCustomView(getCustomView(3));
        //默认设置显示页面
        vpHomePager.setCurrentItem(0);
    }

    private View getCustomView(int position) {
        switch (position) {
            case 0:
                return LayoutInflater.from(this).inflate(R.layout.fragment_home_tab, null);
            case 1:
                return LayoutInflater.from(this).inflate(R.layout.fragment_knowledge_tab, null);
            case 2:
                return LayoutInflater.from(this).inflate(R.layout.fragment_project_tab, null);
            case 3:
                return LayoutInflater.from(this).inflate(R.layout.fragment_todo_tab, null);

        }
        return null;
    }

    private void initData() {
        View drawheadview = drawerNavigation.getHeaderView(0);
        TextView nick_name = drawheadview.findViewById(R.id.nick_name);
//       name.setText(Sharedpreferences_Utils.getInstance(MainActivity.this).getString("niName"));
        drawerNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.collect:
//                        startActivity(new Intent(MainActivity.this, MyCollectionsActivity.class));
                        break;
                    case R.id.setting:
                        break;
                    case R.id.about:
                        break;
                    case R.id.logout:
//                        Sharedpreferences_Utils.getInstance(MainActivity.this).clear();
//                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        break;
                }
                return true;
            }
        });
    }

    private void initAdapter() {

    }

}
