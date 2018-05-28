package com.record.jinwen.viewpagertab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Attr;
import org.w3c.dom.Text;

import java.util.regex.Matcher;

public class ViewPagerTab extends HorizontalScrollView implements ViewPager.OnPageChangeListener {
    // ScrollView 自动滚动的时间
    private static final int SCROLLTIME = 800;

    private PagerAdapter adapter;
    private ViewPagerTabStrip viewPagerTabStrip;
    private ViewPager viewPager;

    // 记录前一次Position
    private int prePosition = -1;
    // 屏幕宽度
    private final int width;
    // 下划线颜色
    private final int colorStateList;
    // 是否显示下划线
    private final boolean underlineVisible;
    // ScrollView 滑动的偏移量
    private int OFFSETTABSCROLL;


    @SuppressLint("ResourceType")
    public ViewPagerTab(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerTab);

        underlineVisible = attr.getBoolean(0, false);
        colorStateList = attr.getColor(1, 0);
        attr.recycle();

        // 关闭滚动条
        setHorizontalScrollBarEnabled(false);
        // 添加TabView载体
        viewPagerTabStrip = new ViewPagerTabStrip(context);
        viewPagerTabStrip.setUnderLineColor(colorStateList);
        viewPagerTabStrip.setUnderLineVisible(underlineVisible);
        addView(viewPagerTabStrip);
        // 获取屏幕数据
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        width = dm.widthPixels;
        OFFSETTABSCROLL = width / 3;


    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        this.adapter = viewPager.getAdapter();
        addTabs();

        // 自定义滑动控制
        ViewPagerScroller scroller = new ViewPagerScroller(getContext());
        scroller.setScrollDuration(SCROLLTIME);//这个是设置切换过渡时间
        scroller.initViewPagerScroll(viewPager);
    }

    private void addTabs() {
        if (adapter != null) {
            for (int i = 0; i < adapter.getCount(); i++) {
                addTab(i);
            }
        }
    }

    private void addTab(final int position) {
        View tabView = LayoutInflater.from(getContext()).inflate(R.layout.tab_view, null);
        TextView tabText = tabView.findViewById(R.id.tab_text);
        if (position % 2 == 0) {
            tabText.setText("新闻");
        } else {
            tabText.setText("体育");
        }

        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                if (position > (prePosition + 1) || position < (prePosition - 1)) {
                    viewPager.setCurrentItem(position, false);
                    View slectedTab = viewPagerTabStrip.getChildAt(position);
                    int selectedLeft = slectedTab.getLeft();
                    int scrollX = selectedLeft - OFFSETTABSCROLL;
                    smoothScrollTo(scrollX, 0);
                } else {
                    viewPager.setCurrentItem(position);
                }
            }
        });
        viewPagerTabStrip.addView(tabView, position, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT, 1));
        if (position == 0) {
            tabView.setSelected(true);
            prePosition = 0;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset > 0.0f && positionOffset < 1.0f) {
            View slectedTab = viewPagerTabStrip.getChildAt(position);
            int selectedLeft = slectedTab.getLeft();
            int scrollX = 0;
            // 是否有下一个Tab
            final boolean hasNextTab = position < (viewPagerTabStrip.getChildCount() - 1);
            if (hasNextTab) {
                View nextTitle = viewPagerTabStrip.getChildAt(position + 1);
                int nextLeft = nextTitle.getLeft();
                selectedLeft =
                        (int) (positionOffset * nextLeft + (1.0f - positionOffset) * selectedLeft);
                scrollX = selectedLeft - OFFSETTABSCROLL;
            }
            smoothScrollTo(scrollX, 0);
        }
        viewPagerTabStrip.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        View childView = viewPagerTabStrip.getChildAt(position);
        int tabStripChildCount = viewPagerTabStrip.getChildCount();

        if (prePosition >= 0 && prePosition < tabStripChildCount) {
            viewPagerTabStrip.getChildAt(prePosition).setSelected(false);
        }
        childView.setSelected(true);
        prePosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
