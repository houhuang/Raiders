package com.jd.raiders.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jd.raiders.fragment.HomeFragment;

import java.util.List;

/**
 * Created by houhuang on 18/1/8.
 */
public class HomeFragmentAdapter extends FragmentPagerAdapter {

    private List<HomeFragment> mList;

    public HomeFragmentAdapter(FragmentManager fm, List<HomeFragment> list) {
        super(fm);
        mList = list;
    }


    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
