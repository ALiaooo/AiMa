package com.app.aliao.aima.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.app.aliao.aima.R;
import com.app.aliao.aima.activity.base.BaseActivity;
import com.app.aliao.aima.activity.news.fragment.NewsViewPagerFragment;

/**
 * Created by ALiao on 2014/12/24.
 * desc:
 */
public class MainActivity extends BaseActivity {

    private FragmentTabHost mTabHost;
    private int mIconId[] = {R.drawable.tab_icon_new, R.drawable.tab_icon_question, R.drawable.tab_icon_tweet, R.drawable.tab_icon_me};
    private int mTitleId[] = {R.string.tab_name_article, R.string.tab_name_question, R.string.tab_name_tweet, R.string.tab_name_me};
    private Class mContentFragment[] = {NewsViewPagerFragment.class, NewsViewPagerFragment.class, NewsViewPagerFragment.class, NewsViewPagerFragment.class};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(),android.R.id.tabcontent);

        for (int i = 0; i<mTitleId.length; i++){
            View view = LayoutInflater.from(this).inflate(R.layout.tab_indicator, null);
            ImageView icon = (ImageView) view.findViewById(R.id.tab_icon);
            TextView title = (TextView) view.findViewById(R.id.tab_title);
            icon.setImageResource(mIconId[i]);
            title.setText(mTitleId[i]);
            mTabHost.addTab(mTabHost.newTabSpec(getString(mTitleId[i])).setIndicator(view), mContentFragment[i], null);
        }

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

            }
        });
    }


}
