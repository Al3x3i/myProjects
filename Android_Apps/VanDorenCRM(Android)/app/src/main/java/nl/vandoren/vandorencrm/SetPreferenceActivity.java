package nl.vandoren.vandorencrm;

import android.app.Activity;
import android.os.Bundle;

import nl.vandoren.vandorencrm.Fragment.MyPreferenceFragment;

/**
 * Created by Alex on 4/10/2015.
 */
public class SetPreferenceActivity extends Activity {

    @Override
    protected void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);

        // set preference fragment with setting options
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }
}
