package nl.vandoren.app.uraandroid.Fragment.WorkedHours.CalendarFragment;

import android.support.v4.view.ViewPager;

import nl.vandoren.app.uraandroid.Fragment.WorkedHours.WorkedHours_fragment;
import nl.vandoren.app.uraandroid.Fragment.WorkedHours.WorkedHours_fragmentPagerAdapter;

/**
 * Created by Aleksei on 6/20/2015.
 */
public class CalendarPageChangeListener implements ViewPager.OnPageChangeListener {

    final String TAG = "myLogs";

    WorkedHours_fragment fragment;
    WorkedHours_fragmentPagerAdapter workedHoursfragmentPagerAdapter;
    ViewPager pager;

    int mCurrentFragmentPosition = 0;

    public CalendarPageChangeListener(WorkedHours_fragmentPagerAdapter workedHoursfragmentPagerAdapter,
                                      WorkedHours_fragment fragment){
        this.workedHoursfragmentPagerAdapter = workedHoursfragmentPagerAdapter;
        this.fragment = fragment;
    }

    public void setPager (ViewPager pager){this.pager = pager;}

    @Override
    public void onPageScrolled(int position, float positionOffset, int i2) {

        mCurrentFragmentPosition = position;
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 2) {
            ((Calendar_fragment) workedHoursfragmentPagerAdapter.getItem(0)).setNextWeek();
            ((Calendar_fragment) workedHoursfragmentPagerAdapter.getItem(1)).setNextWeek();
            ((Calendar_fragment) workedHoursfragmentPagerAdapter.getItem(0)).refreshCalendar();
            ((Calendar_fragment) workedHoursfragmentPagerAdapter.getItem(1)).refreshCalendar();
        } else if (position == 0) {
            ((Calendar_fragment) workedHoursfragmentPagerAdapter.getItem(1)).setPreviousWeek();
            ((Calendar_fragment) workedHoursfragmentPagerAdapter.getItem(2)).setPreviousWeek();
            ((Calendar_fragment) workedHoursfragmentPagerAdapter.getItem(1)).refreshCalendar();
            ((Calendar_fragment) workedHoursfragmentPagerAdapter.getItem(2)).refreshCalendar();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {

            if (mCurrentFragmentPosition == 0) {
                ((Calendar_fragment) workedHoursfragmentPagerAdapter.getItem(0)).setPreviousWeek();
                ((Calendar_fragment) workedHoursfragmentPagerAdapter.getItem(0)).refreshCalendar();

                fragment.controller.getWorkedHours(((Calendar_fragment) workedHoursfragmentPagerAdapter.getItem(1)).adapter.dayString.get(0),
                        ((Calendar_fragment) workedHoursfragmentPagerAdapter.getItem(1)).adapter.dayString.get(6));
            } else if (mCurrentFragmentPosition == 2) {
                ((Calendar_fragment) workedHoursfragmentPagerAdapter.getItem(2)).setNextWeek();
                ((Calendar_fragment) workedHoursfragmentPagerAdapter.getItem(2)).refreshCalendar();
                fragment.controller.getWorkedHours(((Calendar_fragment) workedHoursfragmentPagerAdapter.getItem(1)).adapter.dayString.get(0),
                        ((Calendar_fragment) workedHoursfragmentPagerAdapter.getItem(1)).adapter.dayString.get(6));
            }
            //After swiping week, below value should be null
            fragment.calendarSelectedDate = null;

            //remove an allocation in calendar
            ((Calendar_fragment) workedHoursfragmentPagerAdapter.getItem(1)).adapter.highlightSelectedDay(-1);

            /**
             * set 1 screen
             */
            pager.setCurrentItem(1, false);
        }
    }
}
