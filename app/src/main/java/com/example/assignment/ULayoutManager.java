package com.example.assignment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
public class ULayoutManager extends RecyclerView.LayoutManager {
     int mRadius; // radius of the circle
     int mAngle; // angle between each item
     int mStartAngle; // starting angle for the items
     int mItemCount; // total number of items in the adapter
    int mScrollOffset; // current scroll offset
     int mItemWidth; // width of each item
     int mItemHeight; // height of each item

    public ULayoutManager(int radius, int itemCount, int startAngle) {
        mRadius = radius;
        mItemCount = itemCount;
        mAngle = 90 / (mItemCount - 1); // calculate angle between each item
        mStartAngle = startAngle;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        // remove all existing views
        removeAllViews();

        // calculate item size
        View view = recycler.getViewForPosition(0);
        measureChildWithMargins(view, 0, 0);
        mItemWidth = getDecoratedMeasuredWidth(view);
        mItemHeight = getDecoratedMeasuredHeight(view);

        // calculate half circle center
        int centerX = getWidth() / 2;
        int centerY = getHeight() - mRadius;

        // layout each item in a half circle shape
        for (int i = 0; i < mItemCount; i++) {
            View child = recycler.getViewForPosition(i);
            addView(child);

            // calculate item position
            int angle = mStartAngle + mAngle * i;
            double radians = Math.toRadians(angle);
            int x = (int) (centerX + mRadius * Math.sin(radians) - mItemWidth / 2);
            int y = (int) (centerY + mRadius * (1 - Math.cos(radians)) - mItemHeight / 2);

            // layout item view
            layoutDecorated(child, x, y, x + mItemWidth, y + mItemHeight);
        }
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        // update scroll offset
        mScrollOffset += dy;

        // constrain scroll offset within valid range
        int maxOffset = (mItemCount - 1) * mAngle;
        mScrollOffset = Math.min(Math.max(mScrollOffset, 0), maxOffset);

        // calculate angle and position for each item
        for (int i = 0; i < mItemCount; i++) {
            View child = getChildAt(i);
            int angle = mStartAngle + mAngle * i - mScrollOffset;
            double radians = Math.toRadians(angle);
            int x = (int) (getWidth() / 2 + mRadius * Math.sin(radians) - mItemWidth / 2);
            int y = (int) (getHeight() - mRadius * (1 - Math.cos(radians)) - mItemHeight / 2);

            // layout item view
            layoutDecorated(child, x, y, x + mItemWidth, y + mItemHeight);
        }

        return dy;
    }
}


