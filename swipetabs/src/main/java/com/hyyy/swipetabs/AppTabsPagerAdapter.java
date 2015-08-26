package com.hyyy.swipetabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2015/8/26.
 */
public class AppTabsPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mTabs;

    public AppTabsPagerAdapter(FragmentManager fm, List<Fragment> tabs) {
        super(fm);
        this.mTabs = tabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                /**
                 * 返回第一个Fragment
                 */
                Fragment firstFragment = new FirstFragment();
                Bundle firstBundle = new Bundle();
                firstBundle.putInt(FirstFragment.ARG_SECTION_NUMBER, 1);
                firstFragment.setArguments(firstBundle);
                return firstFragment;

            case 1:
                /**
                 * 返回第二个Fragment
                 */
                Fragment secFragment = new FirstFragment();
                Bundle secBundle = new Bundle();
                secBundle.putInt(FirstFragment.ARG_SECTION_NUMBER, 2);
                secFragment.setArguments(secBundle);
                return secFragment;

            case 2:
                /**
                 * 返回第三个Fragment
                 */
                Fragment thirdFragment = new FirstFragment();
                Bundle thirdBundle = new Bundle();
                thirdBundle.putInt(FirstFragment.ARG_SECTION_NUMBER, 3);
                thirdFragment.setArguments(thirdBundle);
                return thirdFragment;

            default:
                break;

        }
        return null;
    }

    @Override
    public int getCount() {
        return mTabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Section" + (position + 1);
    }
}
