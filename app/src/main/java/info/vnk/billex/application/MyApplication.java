package info.vnk.billex.application;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

/**
 * Created by Visak on 14/04/17.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
