package nl.vandoren.app.uraandroid.Fragment.WorkedHours;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import nl.vandoren.app.uraandroid.Fragment.WorkedHours.CalendarFragment.Calendar_fragment;

/**
 * Created by Alex on 6/11/2015.
 * Class is used to manage calendar objects
 */
public class WorkedHours_fragmentPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Calendar_fragment> fragList;

    public WorkedHours_fragmentPagerAdapter(FragmentManager fm, WorkedHours_fragment fragment) {
        super(fm);
        fragList = new ArrayList<Calendar_fragment> ();

        fragList.add(new Calendar_fragment());
        fragList.add(new Calendar_fragment());
        fragList.add(new Calendar_fragment());

        //second fragment should be subscribed for call back event
        fragList.get(1).subscribeForCallBackEvent(fragment);
    }


    @Override
    public Fragment getItem(int position) {
        return fragList.get(position);
    }

    @Override
    public int getCount() {
        return fragList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
