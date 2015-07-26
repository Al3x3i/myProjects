package nl.vandoren.vandorencrm.Fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import nl.vandoren.vandorencrm.R;


/**
 * Created by Alex on 4/10/2015.
 */
public class MyPreferenceFragment extends PreferenceFragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref1);
    }
}
