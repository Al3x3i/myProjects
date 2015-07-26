package nl.vandoren.app.uraandroid.Fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import nl.vandoren.app.uraandroid.R;

/**
 * Created by Alex on 5/26/2015.
 */
public class MyPreference_fragment extends PreferenceFragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref1);
    }
}