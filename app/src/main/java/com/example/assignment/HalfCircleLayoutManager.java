package com.example.assignment;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HalfCircleLayoutManager extends LinearLayoutManager {

    private int radius; // radius of the circle
    private float anglePerItem; // angle occupied by each item
    private float tilt; // tilt angle for the half circle

    public HalfCircleLayoutManager(Context context, int radius, float tilt) {
        super(context);
        this.radius = radius;
        this.tilt = tilt;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);

        // calculate angle per item based on the number of items and the total arc length
        int itemCount = state.getItemCount();
        anglePerItem = (float) Math.toRadians(180) / (float) itemCount;

        // layout each item along the circumference of the circle
        for (int i = 0; i < itemCount; i++) {
            View child = recycler.getViewForPosition(i);
            float angle = tilt + (i * anglePerItem);
            int x = (int) (radius * Math.sin(angle));
            int y = (int) (radius * Math.cos(angle));
            layoutDecorated(child, x, y, x + getDecoratedMeasuredWidth(child), y + getDecoratedMeasuredHeight(child));
        }
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        // calculate the angle by which to rotate the RecyclerView based on the amount of scrolling
        float angle = dy * anglePerItem / 2;

        // rotate the RecyclerView by the calculated angle
        View centerView = findViewByPosition(0);
        if (centerView != null) {
            centerView.setRotation(centerView.getRotation() + angle);
        }

        // recycle views that are no longer visible
        detachAndScrapAttachedViews(recycler);

        // layout items again with updated positions and rotations
        onLayoutChildren(recycler, state);

        return dy;
    }
}

