package com.hyyy.wechat;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;

import com.hyyy.wechat.fragment.TabFragment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private ViewPager mViewPager;

    private List<Fragment> mFragments = new ArrayList<Fragment>();

    private String[] mTitles = new String[]{
            "First Fragment", "Second Fragment", "Third Fragment", "Fourth Fragment"
    };

    private FragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setOverflowButtonAlways();
        getActionBar().setDisplayShowHomeEnabled(false);//去掉actionbar的logo

        initView();
        initData();
        mViewPager.setAdapter(mAdapter);

    }

    private void initData() {
        for (String title : mTitles){
            TabFragment tabFragment = new TabFragment();
            Bundle bundle = new Bundle();
            bundle.putString(TabFragment.TITLE, title);
            tabFragment.setArguments(bundle);
            mFragments.add(tabFragment);
        }

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.wechat_viewpager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * 设置menu的icon显示
     * @param featureId
     * @param menu
     * @return
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {

        if (featureId == Window.FEATURE_ACTION_BAR && menu != null){
            if(menu.getClass().getSimpleName().equals("MenuBuilder")){
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    /**
     * 让overflow按钮永远显示
     */
    private void setOverflowButtonAlways(){

        ViewConfiguration viewConfiguration = ViewConfiguration.get(this);

        try {
            Field menuKey = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            menuKey.setAccessible(true);
            menuKey.setBoolean(viewConfiguration, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
