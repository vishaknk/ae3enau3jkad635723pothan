package info.vnk.billex.activity.main;

import android.content.Context;
import android.os.Bundle;

import info.vnk.billex.R;
import info.vnk.billex.base.BaseActivity;

public class AboutActivity extends BaseActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        context = this;
        setToolbar();
    }
}
