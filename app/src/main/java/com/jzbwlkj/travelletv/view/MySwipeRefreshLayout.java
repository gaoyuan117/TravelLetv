package com.jzbwlkj.travelletv.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.orhanobut.logger.Logger;

/**
 * Created by dn on 2017/2/13.
 */

/**
 * 解决SwipeRefreshLayout与Banner冲突的问题
 */

public class MySwipeRefreshLayout extends SwipeRefreshLayout {
    private int scaleTouchSlop;
    private float preX;


    public MySwipeRefreshLayout(Context context) {
        super(context);
    }

    public MySwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        scaleTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                preX = ev.getX();
                break;

            case MotionEvent.ACTION_MOVE:
                float moveX = ev.getX();
                float instanceX = Math.abs(moveX - preX);
                // 容差值大概是24，再加上20,加的越小倾斜度就可以越大
                if (instanceX > scaleTouchSlop + 20) {
                    return false;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }


}
