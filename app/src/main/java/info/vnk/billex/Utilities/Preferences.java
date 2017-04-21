package info.vnk.billex.Utilities;

import android.content.Context;

import com.shawnlin.preferencesmanager.PreferencesManager;

/**
 * Created by Visak on 31-12-2016.
 */

public class Preferences {

    public static PreferencesManager initSharedPreference(Context context, String name){

        PreferencesManager preferencesManager = new PreferencesManager(context);
        preferencesManager.setName(name);
        preferencesManager.init();
        return preferencesManager;
    }
}
