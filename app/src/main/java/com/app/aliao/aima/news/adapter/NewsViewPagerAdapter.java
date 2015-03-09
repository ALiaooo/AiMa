package com.app.aliao.aima.news.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.app.aliao.aima.news.NewsTab;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 丽双 on 2015/3/9.
 */
public class NewsViewPagerAdapter extends FragmentPagerAdapter{

    private Context mContext;
    private List<Fragment> mFragmentList;

    public NewsViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mFragmentList = new ArrayList<>();

        mContext = context;

        for (NewsTab newsTab:NewsTab.values()){

            try {
                mFragmentList.add((Fragment) newsTab.getClz().newInstance());

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }


    @Override
    public int getCount() {
        return NewsTab.values().length;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return mContext.getString(NewsTab.getNewsTab(position).getTitle());
    }
}
