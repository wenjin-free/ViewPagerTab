package com.record.jinwen.viewpagertab;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;

    public MyPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        // TODO Auto-generated constructor stub
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        return mFragments.get(arg0);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mFragments.size();
    }

}
