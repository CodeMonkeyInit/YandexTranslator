package deniskuliev.yandextranslator.customViews;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by deniskuliev on 15.04.17.
 */

public class CustomViewPager extends ViewPager
{
    private SwipeDirection _directionAllowed;
    private float initialXValue;

    public CustomViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        _directionAllowed = SwipeDirection.all;
    }

    private boolean isTouchAllowed(MotionEvent event)
    {
        if (_directionAllowed == SwipeDirection.all)
        {
            return true;
        }

        if (_directionAllowed == SwipeDirection.none)
        {
            return false;
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            initialXValue = event.getX();
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE)
        {
            float differenceX = event.getX() - initialXValue;

            if (differenceX > 0 && _directionAllowed == SwipeDirection.right)
            {
                // swipe from left to right detected
                return false;
            }
            else if (differenceX < 0 && _directionAllowed == SwipeDirection.left)
            {
                // swipe from right to left detected
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (isTouchAllowed(event))
        {
            return super.onTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    {
        if (isTouchAllowed(event))
        {
            return super.onInterceptTouchEvent(event);
        }

        return false;

    }

    public void setAllowedDirection(SwipeDirection direction)
    {
        _directionAllowed = direction;
    }
}
