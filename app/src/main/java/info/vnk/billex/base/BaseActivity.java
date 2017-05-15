package info.vnk.billex.base;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.shawnlin.preferencesmanager.PreferencesManager;

import info.vnk.billex.R;
import info.vnk.billex.utilities.Constants;
import info.vnk.billex.utilities.Preferences;

/**
 * Created by Visak on 14/04/17.
 */

public class BaseActivity extends AppCompatActivity {
    protected PreferencesManager preferencesManager;
    private Toolbar mToolBar;

    protected Toolbar setToolbar(){
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mToolBar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //toolbar.setNavigationIcon(R.drawable.ic_action_back);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check whether any input is added before leaving the page and allow leave
                finish();
            }
        });
        return mToolBar;
    }

    protected PreferencesManager setPreference(Context mContext){
        preferencesManager = Preferences.initSharedPreference(mContext, Constants.sp_login);
        return preferencesManager;
    }
}
