package com.record.jinwen.viewpagertab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPagerTab viewPagerTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new Fragment1());
        fragments.add(new Fragment2());
        fragments.add(new Fragment3());
        fragments.add(new Fragment1());
        fragments.add(new Fragment2());
        fragments.add(new Fragment3());
        fragments.add(new Fragment1());
        fragments.add(new Fragment2());
        fragments.add(new Fragment3());
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);
        //设定适配器
        ViewPager vp = findViewById(R.id.view_pager);
        vp.addOnPageChangeListener(this);
        vp.setAdapter(adapter);
        viewPagerTab = findViewById(R.id.pager_tab);
        viewPagerTab.setViewPager(vp);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        viewPagerTab.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        viewPagerTab.onPageSelected(position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        viewPagerTab.onPageScrollStateChanged(state);

    }
}
