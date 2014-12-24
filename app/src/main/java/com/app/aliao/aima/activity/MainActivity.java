package com.app.aliao.aima.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;

import com.app.aliao.aima.R;
import com.app.aliao.aima.activity.news.fragment.NewsViewPagerFragment;

/**
 * Created by sf on 2014/12/24.
 * desc:
 */
public class MainActivity extends BaseActivity {

    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(),android.R.id.tabcontent);
        mTabHost.addTab(mTabHost.newTabSpec(getString(R.string.tab_name_article)).setIndicator(getString(R.string.tab_name_article), getResources().getDrawable(R.drawable.tab_icon_new)), NewsViewPagerFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(getString(R.string.tab_name_question)).setIndicator(getString(R.string.tab_name_question), getResources().getDrawable(R.drawable.tab_icon_question)), NewsViewPagerFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(getString(R.string.tab_name_tweet)).setIndicator(getString(R.string.tab_name_tweet), getResources().getDrawable(R.drawable.tab_icon_tweet)), NewsViewPagerFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(getString(R.string.tab_name_me)).setIndicator(getString(R.string.tab_name_me), getResources().getDrawable(R.drawable.tab_icon_me)), NewsViewPagerFragment.class, null);
    }
}
