package nl.vandoren.vandorencrm;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import nl.vandoren.vandorencrm.Fragment.CompanyResultFragment;
import nl.vandoren.vandorencrm.Fragment.FindCompanyFragment;
import nl.vandoren.vandorencrm.Fragment.FindCompany_EmployeesResultFragment;
import nl.vandoren.vandorencrm.Fragment.FindEmployeeFragment;
import nl.vandoren.vandorencrm.Fragment.NavigationDrawerFragment;
import nl.vandoren.vandorencrm.Fragment.EmployeeResultFragment;



public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        FindCompanyFragment.FindCompanyFragmentCallbacks,
        FindEmployeeFragment.FindEmployeeListCallbacks,
        FindCompany_EmployeesResultFragment.FindCompany_EmployeeResultCallbacks,
        EmployeeResultFragment.FindCompany_EmployeeResultCallbacks
        {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    final String TAG = "myLogs";

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        try {
            ActivityInfo[] list = getPackageManager().getPackageInfo(getPackageName(),PackageManager.GET_ACTIVITIES).activities;

            for(int i = 0;i< list.length;i++)
            {
                System.out.println("List of running activities"+list[i].name);
            }
        }

        catch (PackageManager.NameNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    public void onSetTitleOnAttached(int number) {
        switch (number) {
            case 0:
                mTitle = getString(R.string.title_section1_item0);
                break;
            case 1:
                mTitle = getString(R.string.title_section1_item1);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            SharedPreferences settings = this.getSharedPreferences("MRC", MODE_PRIVATE);
            settings.edit().clear().commit();

            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * If FragmentManager doesn't have fragments in stack then app will be minimized (moved to background stack) not not go to Login Activity
     */
    @Override
    public void onBackPressed(){
        FragmentManager fm = this.getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fm.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            //super.onBackPressed();
            this.moveTaskToBack(true); //prevent previous activity call
        }
    }

     //BELOW FRAGMENTS CALL BACK IMPLEMENTATIONS

    /**
     * set fragments. For the startActivity() will be called case 0 and displayed FindCompany
     * @param position
     */
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments

        Fragment objFragment = null;
        switch (position) {
            case 0:
                objFragment = new FindCompanyFragment();
                break;
            case 1:
                objFragment = new FindEmployeeFragment();
                break;
        }

        if(objFragment ==null)
        {
            Toast.makeText(this,"Under Construction",Toast.LENGTH_SHORT).show();
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();

        //set actionbar title
        onSetTitleOnAttached(position);

        //Prevent screen overlapping. If user selects from navigation menu item, then fragment's history stack will be set to zero
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.container, objFragment);
        ft.commit();


    }


    @Override
    public void onFindCompany_EmployeeItemSelected(String selected_CompanyID, String selected_CompanyName,String selected_CompanyCountry, String requestResultEmployeeList) {

        Fragment objFragment = new FindCompany_EmployeesResultFragment();

        Bundle args = new Bundle();
        args.putString("CompanyID",selected_CompanyID);
        args.putString("CompanyName", selected_CompanyName);
        args.putString("CompanyCountry", selected_CompanyCountry);
        args.putString("selected_Company_EmployeeList", requestResultEmployeeList);
        objFragment.setArguments(args);

        FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.container, objFragment).addToBackStack("FindCompanyFragment").commit();
    }

    @Override
    public void onFindEmployeeItemSelected(String requestResultEmployeeAllData) {

        try {
            InputMethodManager input = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            input.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }catch(Exception e) {
            e.printStackTrace();
        }

        Fragment objFragment = new EmployeeResultFragment();

        Bundle args = new Bundle();

        args.putString("requestResultEmployeeFullData", requestResultEmployeeAllData);
        objFragment.setArguments(args);


        //Back button logic. Adds fragment's screen to stack queue for back button click
        FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.container, objFragment).addToBackStack("EmployeeResultFragment").commit();

    }


    @Override
    public void onFindCompany_EmployeeResult_ButtonCompanyInfoSelected(String companyData) {
        Fragment objFragment = new CompanyResultFragment();

        Bundle args = new Bundle();
        args.putString("requestResultCompanyFullData", companyData);
        objFragment.setArguments(args);


        FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                   .replace(R.id.container, objFragment).addToBackStack("CompanyResultFragment").commit();
    }
}
