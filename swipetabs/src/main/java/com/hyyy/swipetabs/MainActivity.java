package com.hyyy.swipetabs;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity implements ActionBar.TabListener{

    private ViewPager mViewPager;
    private AppTabsPagerAdapter mAppTabsPagerAdapter;
    private List<Fragment> tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragment();
        mAppTabsPagerAdapter = new AppTabsPagerAdapter(getSupportFragmentManager(),tabs);
        final ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                getActionBar().setSelectedNavigationItem(position);
            }
        });
        mViewPager.setAdapter(mAppTabsPagerAdapter);

        //加上tabs
        for (int i = 0; i < mAppTabsPagerAdapter.getCount(); i++){
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mAppTabsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    private void initFragment() {
        tabs = new ArrayList<>();
        tabs.add(new FirstFragment());
        tabs.add(new SecondFragment());
        tabs.add(new SecondFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem shareItem = menu.findItem(R.id.action_share);
        ShareActionProvider provider = (ShareActionProvider) shareItem.getActionProvider();
        provider.setShareIntent(getDefaultIntent());
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                Toast.makeText(MainActivity.this, "Add", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_edit:
                Toast.makeText(MainActivity.this, "Edit", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_delete:
                Toast.makeText(MainActivity.this, "Delete", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Intent getDefaultIntent(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        return intent;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}