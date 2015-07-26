package nl.vandoren.app.uraandroid;

import android.app.Activity;
import android.os.Bundle;

import nl.vandoren.app.uraandroid.Fragment.MyPreference_fragment;

/**
 * Created by Alex on 5/26/2015.
 */
public class SetPreferenceActivity extends Activity {

    @Override
    protected void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);

        // set preference fragment with setting's options
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new MyPreference_fragment()).commit();
    }
}
