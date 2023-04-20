package com.example.assignment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public class CircularLayoutManager extends RecyclerView.LayoutManager {

    private int mRadius;
    private int mScrollOffset;

    public CircularLayoutManager(Context context, int radius) {
        mRadius = radius;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
        );
    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int scrolled = dx;
        mScrollOffset += dx;
        updateItemPositions(recycler);
        return scrolled;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        updateItemPositions(recycler);
    }

    private void updateItemPositions(RecyclerView.Recycler recycler) {
        int itemCount = getItemCount();
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        for (int i = 0; i < itemCount; i++) {
            View view = recycler.getViewForPosition(i);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            int viewWidth = getDecoratedMeasuredWidth(view);
            int viewHeight = getDecoratedMeasuredHeight(view);
            double angle = 2 * Math.PI * i / itemCount + mScrollOffset * 2 * Math.PI / (itemCount * getWidth());
            int x = (int) (centerX + mRadius * Math.cos(angle) - viewWidth / 2);
            int y = (int) (centerY + mRadius * Math.sin(angle) - viewHeight / 2);
            layoutDecorated(view, x, y, x + viewWidth, y + viewHeight);
        }
    }
}

