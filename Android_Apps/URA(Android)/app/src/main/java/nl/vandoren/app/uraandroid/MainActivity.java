package nl.vandoren.app.uraandroid;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

import nl.vandoren.app.uraandroid.Fragment.NavigationDrawer.NavigationDrawer_fragment;
import nl.vandoren.app.uraandroid.Fragment.WeekPlanning.WeekPlanning_fragment;
import nl.vandoren.app.uraandroid.Fragment.WorkedHours.Dialog_notification;
import nl.vandoren.app.uraandroid.Fragment.WorkedHours.WorkedHours_fragment;
import nl.vandoren.app.uraandroid.Model.User;


public class MainActivity extends AppCompatActivity
        implements NavigationDrawer_fragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawer_fragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    Fragment objFragment=null;
    boolean firstStart = true; // set fragment for the first run

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Locale.setDefault(Locale.UK);
        setContentView(R.layout.activity_main); // xml has fragment, so navigationDrawer will be created automatically

        mNavigationDrawerFragment = (NavigationDrawer_fragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), User.getInstance().fullName);
    }


    //I start from first because "0" is used for  Navigation header
    @Override
    public void onNavigationDrawerItemSelected(int position) {

        boolean changeFlag = false;

        switch (position) {
            case 1:
                if(objFragment !=null && objFragment instanceof WorkedHours_fragment) {
                    if (((WorkedHours_fragment) objFragment).timerFlag == false) {
                        //((WorkedHours_fragment)objFragment).showDialogTimer();
                        objFragment = new WorkedHours_fragment();
                        changeFlag =true;
                    }
                }else {
                    objFragment = new WorkedHours_fragment();
                    changeFlag =true;
                }
                break;
            case 2:
                if(objFragment !=null && objFragment instanceof WorkedHours_fragment) {
                    if (((WorkedHours_fragment) objFragment).timerFlag) {

                        ((WorkedHours_fragment) objFragment).pDialog = new Dialog_notification();
                        String h = ((WorkedHours_fragment) objFragment).editText_hour.getText().toString();
                        String m = ((WorkedHours_fragment) objFragment).editText_minute.getText().toString();
                        ((WorkedHours_fragment) objFragment).pDialog.setSettings((WorkedHours_fragment) objFragment,"Timer Alert!", "Save timer time: " + h + ":" + m);
                        ((WorkedHours_fragment) objFragment).pDialog.show(((WorkedHours_fragment) objFragment).getFragmentManager(), "Timer dialog");
                    }
                }
                objFragment = new WeekPlanning_fragment();
                changeFlag =true;
                break;
        }
        if((objFragment!=null && changeFlag)||firstStart) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            firstStart = false;
            if (fragmentManager.getBackStackEntryCount() > 0) {

                for(int entry = 0; entry < fragmentManager.getBackStackEntryCount(); entry++){
                    fragmentManager.getBackStackEntryAt(entry).getId();
                };

                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
            //fragmentManager.getFragments().remove(1);
            FragmentTransaction tr = fragmentManager.beginTransaction();
            //tr.(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            tr.replace(R.id.container, objFragment);
            //tr.addToBackStack(null);
            tr.commit();
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            SharedPreferences settings = this.getSharedPreferences("URA", MODE_PRIVATE);
            settings.edit().clear().commit();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
