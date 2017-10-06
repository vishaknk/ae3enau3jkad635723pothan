package info.vnk.billex.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.shawnlin.preferencesmanager.PreferencesManager;

import info.vnk.billex.R;
import info.vnk.billex.utilities.Constants;
import info.vnk.billex.utilities.Preferences;
import info.vnk.billex.base.BaseActivity;

public class SplashScreenActivity extends BaseActivity {


    private PreferencesManager preferencesManager;
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Preferences mPreference = new Preferences();
        preferencesManager = mPreference.initSharedPreference(this, Constants.sp_login);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app ic_logo_white / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent intent = null;
                if(preferencesManager.getBoolean(Constants.IS_LOGGED_IN, false))
                    intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                else
                    intent = new Intent(SplashScreenActivity.this, LoginActivity.class);

                startActivity(intent);
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}