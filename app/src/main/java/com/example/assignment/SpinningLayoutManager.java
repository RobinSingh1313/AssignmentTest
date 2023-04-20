package com.example.assignment;

import android.animation.ValueAnimator;

import androidx.recyclerview.widget.RecyclerView;

public class SpinningLayoutManager extends ULayoutManager {
    private float mLastOffset;
    private int mDuration = 5000; // duration of the spinning animation in milliseconds

    public SpinningLayoutManager(int radius, int itemCount, int startAngle) {
        super(radius, itemCount, startAngle);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        // calculate total scroll offset
        float totalOffset = mScrollOffset + dy;

        // constrain total offset within valid range
        int maxOffset = (mItemCount - 1) * mAngle;
        totalOffset = Math.min(Math.max(totalOffset, 0), maxOffset);

        // calculate spinning animation parameters
        float startOffset = mLastOffset;
        float endOffset = totalOffset;
        if (totalOffset < mLastOffset) {
            endOffset += maxOffset;
        }
        float deltaOffset = endOffset - startOffset;
        float totalDuration = mDuration * Math.abs(deltaOffset) / maxOffset;

        // start spinning animation
        ValueAnimator animator = ValueAnimator.ofFloat(startOffset, endOffset);
        animator.setDuration((long) totalDuration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mScrollOffset = (int) animation.getAnimatedValue();
                requestLayout();
            }
        });
        animator.start();

        // save last offset for next animation
        mLastOffset = totalOffset;

        return dy;
    }
}

