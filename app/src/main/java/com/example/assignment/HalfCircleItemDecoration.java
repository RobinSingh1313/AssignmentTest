package com.example.assignment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.TypedValue;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
public class HalfCircleItemDecoration extends RecyclerView.ItemDecoration {
    private final Paint paint;
    private final float radius;

    public HalfCircleItemDecoration(int color, int radiusDp, Context context) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);

        radius = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                radiusDp,
                context.getResources().getDisplayMetrics()
        );
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int pos = parent.getChildAdapterPosition(view);
        int itemCount = state.getItemCount();
        int radiusPx = (int) radius;

        // Calculate the horizontal offset for each view
        float step = 180f / (itemCount - 1);
        float angle = step * pos;
        float dx = radiusPx * (float) Math.cos(Math.toRadians(angle));

        outRect.left = (int) dx;
        outRect.right = (int) dx;

        // Set the bottom offset to the radius
        outRect.bottom = radiusPx;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        int itemCount = state.getItemCount();
        int radiusPx = (int) radius;

        for (int i = 0; i < itemCount; i++) {
            View view = parent.getChildAt(i);

            int left = view.getLeft();
            int top = view.getBottom() - radiusPx;
            int right = view.getRight();
            int bottom = view.getBottom();

            // Calculate the horizontal offset for each view
            float step = 180f / (itemCount - 1);
            float angle = step * i;
            float dx = radiusPx * (float) Math.cos(Math.toRadians(angle));

            c.save();
            c.translate(dx, 0);
            c.drawArc(new RectF(left, top, right, bottom), 180, 180, true, paint);
            c.restore();
        }
    }

}
