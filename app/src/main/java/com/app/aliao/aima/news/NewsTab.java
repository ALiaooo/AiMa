package com.app.aliao.aima.news;

import com.app.aliao.aima.R;
import com.app.aliao.aima.blog.fragment.BlogFragment;
import com.app.aliao.aima.news.fragment.NewsFragment;

/**
 * Created by 丽双 on 2015/3/9.
 */
public enum NewsTab {

    LASTEST(R.string.frame_title_news_lastest, NewsFragment.class),
    BLOG(R.string.frame_title_news_blog, BlogFragment.class),
    RECOMMEND(R.string.frame_title_news_recommend, BlogFragment.class);

    private int title;
    private Class<?> clz;


    //自定义
    private NewsTab( int title,  Class<?> clz){//定义枚举类型的值
        this.title = title;
        this.clz = clz;
    }

    public Class<?> getClz() {
        return clz;
    }

    public int getTitle() {
        return title;
    }

    public static NewsTab getNewsTab(int position){

        for (NewsTab t: values()){
            if (t.ordinal() ==  position){
                return t;
            }
        }

        return LASTEST;
    }

}
