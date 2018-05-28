package com.record.jinwen.viewpagertab;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class ViewPagerTabStrip extends LinearLayout {


    private Paint mPaint;
    private float mSelectionOffset;
    private int mIndexForSelection;
    private int mSelectedUnderlineThickness;
    private boolean underLineVisible = false;


    public ViewPagerTabStrip(Context context) {
        super(context);
        final Resources res = context.getResources();
        mSelectedUnderlineThickness = res.getDimensionPixelSize(R.dimen.tab_selected_underline_height);
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        setWillNotDraw(false);
    }

    public void setUnderLineColor(int color) {
        mPaint.setColor(color);
    }

    public void setUnderLineVisible(boolean visible) {
        this.underLineVisible = visible;
    }


    public ViewPagerTabStrip(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        setBackgroundColor(Color.WHITE);
        setWillNotDraw(false);
    }

    /**
     * Notifies this view that view pager has been scrolled. We save the tab index and selection
     * offset for interpolating the position and width of selection underline.
     */
    void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (underLineVisible) {
            mIndexForSelection = position;
            mSelectionOffset = positionOffset;
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //获取到当前所添加的Tab的数量。
        int childCount = getChildCount();

        // 大于零才进行绘画
        if (childCount > 0 && underLineVisible && mSelectionOffset >= 0.0f && mSelectionOffset <= 1.0f) {
            //获取到当前在哪个Tab上。
            View selectedTitle = getChildAt(mIndexForSelection);

            if (selectedTitle == null) {
                return;
            }
            int selectedLeft = selectedTitle.getLeft();
            int selectedRight = selectedTitle.getRight();

            // 是否有下一个Tab
            final boolean hasNextTab =
                    (mIndexForSelection < (getChildCount() - 1));
            if ((mSelectionOffset > 0.0f) && hasNextTab) {
                View nextTitle = getChildAt(mIndexForSelection + 1);
                int nextLeft = nextTitle.getLeft();
                int nextRight = nextTitle.getRight();
                selectedLeft =
                        (int) (mSelectionOffset * nextLeft + (1.0f - mSelectionOffset) * selectedLeft);
                selectedRight =
                        (int) (mSelectionOffset * nextRight + (1.0f - mSelectionOffset) * selectedRight);
            }

            int height = getHeight();

            canvas.drawRect(
                    selectedLeft + 20,
                    height - mSelectedUnderlineThickness,
                    selectedRight - 20,
                    height,
                    mPaint);
        }
    }
}
