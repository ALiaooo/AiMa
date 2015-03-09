package com.app.aliao.aima.activity.news.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.aliao.aima.R;
import com.app.aliao.aima.news.adapter.NewsViewPagerAdapter;
import com.app.aliao.aima.utils.L;

import org.json.JSONObject;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsViewPagerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsViewPagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 * 资讯
 */
public class NewsViewPagerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "myTag";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ViewPager mViewPager;
    private PagerTabStrip mPagerTabStrip;

    private RequestQueue mQueue;
    private StringRequest mStringRequest;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsViewPagerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsViewPagerFragment newInstance(String param1, String param2) {
        NewsViewPagerFragment fragment = new NewsViewPagerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public NewsViewPagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_view_pager, container, false);
        initViews(view);
//        initDatas();
        return view;
    }


    /**
     * 定义的枚举Color就是一个类，所有枚举值是Color类的对象
     */
    public enum Color{
        RED,BULE,YELLOW
    };


    private void initViews(View view) {
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager_news);
        //viewpager，每个item对应的fragment，每个页面的标题
        mViewPager.setAdapter(new NewsViewPagerAdapter(getActivity().getSupportFragmentManager(), getActivity()));
//        mViewPager.setAdapter(new SectionsPagerAdapter(getActivity().getSupportFragmentManager()));
        mPagerTabStrip = (PagerTabStrip) view.findViewById(R.id.pagertab_news);
        mPagerTabStrip.setTabIndicatorColorResource(R.color.indicator_underline);
        mPagerTabStrip.setTextColor(getResources().getColor(R.color.tab_strip_text_selected));


    }



    private void initDatas() {

        mQueue = Volley.newRequestQueue(getActivity());

        mStringRequest = new StringRequest("http://www.baidu.com", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                L.d("response = "+response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                L.d("errormsg = "+error.getMessage());
            }
        });

        mStringRequest.setTag(TAG);
        mQueue.add(mStringRequest);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://m.weather.com.cn/data/101010100.html",null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        L.d("json response = "+response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                L.d("json errormsg = "+error.getMessage());
            }
        });
        jsonObjectRequest.setTag(TAG);
        mQueue.add(jsonObjectRequest);

    }

    @Override
    public void onStop() {
        super.onStop();
        if (null != mQueue){
            mQueue.cancelAll(TAG);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

 /*   @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


    public class pagerAdapter extends FragmentPagerAdapter{

        public pagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return 0;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.tab_title_news_lastest).toUpperCase(l);
                case 1:
                    return getString(R.string.tab_title_news_blog).toUpperCase(l);
                case 2:
                    return getString(R.string.tab_title_news_recommend).toUpperCase(l);
            }
            return null;
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_test, container, false);
            return rootView;
        }
    }

}
