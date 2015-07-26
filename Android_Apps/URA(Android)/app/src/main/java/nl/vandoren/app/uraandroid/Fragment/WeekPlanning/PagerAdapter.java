package nl.vandoren.app.uraandroid.Fragment.WeekPlanning;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import nl.vandoren.app.uraandroid.Fragment.WorkedHours.CalendarFragment.Calendar_fragment;

/** Holds 3 fragments of Calendar (noVisible - visible - notVisible)
 * Created by Alex on 29-6-2015.
 */
public class PagerAdapter extends FragmentPagerAdapter {
    ArrayList<Calendar_fragment> fragList;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
        fragList = new ArrayList<Calendar_fragment> ();

        fragList.add(new Calendar_fragment()); //noVisible
        fragList.add(new Calendar_fragment()); //visible
        fragList.add(new Calendar_fragment()); //notVisible
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
