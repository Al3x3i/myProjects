package nl.vandoren.app.uraandroid.Fragment.WeekPlanning;

import android.support.v4.view.ViewPager;
import android.util.Log;

import nl.vandoren.app.uraandroid.Fragment.WorkedHours.CalendarFragment.Calendar_fragment;

/**
 * Created by Alex on 29-6-2015.
 */
public class CalendarChangeListener implements ViewPager.OnPageChangeListener {

    final String TAG = "myLogs";

    WeekPlanning_fragment fragment;
    PagerAdapter myPagerAdapter;
    ViewPager pager;

    int mCurrentFragmentPosition = 0;

    public CalendarChangeListener(PagerAdapter adapter,
                                  WeekPlanning_fragment fragment){
        this.myPagerAdapter = adapter;
        this.fragment = fragment;
    }

    public void setPager (ViewPager pager){this.pager = pager;}

    @Override
    public void onPageScrolled(int position, float positionOffset, int i2) {

        if (position == mCurrentFragmentPosition && positionOffset > 0) {
            Log.d(TAG, "onPageSelected, MoveChanged = " + position);

        } else if (positionOffset > 0) {
            Log.d(TAG, "onPageSelected, MoveChanged = " + position);
        }
        mCurrentFragmentPosition = position;
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 2) {
            ((Calendar_fragment) myPagerAdapter.getItem(0)).setNextWeek();
            ((Calendar_fragment) myPagerAdapter.getItem(1)).setNextWeek();
            ((Calendar_fragment) myPagerAdapter.getItem(0)).refreshCalendar();
            ((Calendar_fragment) myPagerAdapter.getItem(1)).refreshCalendar();

            Log.d(TAG, "onPageSelected, SwipePosition = " + position);
        } else if (position == 0) {
            ((Calendar_fragment) myPagerAdapter.getItem(1)).setPreviousWeek();
            ((Calendar_fragment) myPagerAdapter.getItem(2)).setPreviousWeek();
            ((Calendar_fragment) myPagerAdapter.getItem(1)).refreshCalendar();
            ((Calendar_fragment) myPagerAdapter.getItem(2)).refreshCalendar();
            Log.d(TAG, "onPageSelected, SwipePosition = " + position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.d(TAG, "onPageSelected, StateChanged = " + state);
        if (state == ViewPager.SCROLL_STATE_IDLE) {

            if (mCurrentFragmentPosition == 0) {
                ((Calendar_fragment) myPagerAdapter.getItem(0)).setPreviousWeek();
                ((Calendar_fragment) myPagerAdapter.getItem(0)).refreshCalendar();

                Log.d(TAG, "onPageSelected, Move = back");
            } else if (mCurrentFragmentPosition == 2) {
                ((Calendar_fragment) myPagerAdapter.getItem(2)).setNextWeek();
                ((Calendar_fragment) myPagerAdapter.getItem(2)).refreshCalendar();

                Log.d(TAG, "onPageSelected, Move = forward");


            }
            fragment.getWeekPlanning(((Calendar_fragment) myPagerAdapter.getItem(1)).adapter.dayString);
            /**
             * Settings to set 1 screen
             */
            pager.setCurrentItem(1, false);

            Log.d(TAG, "onPageSelected, LAST CHANGE");
        }
    }
}