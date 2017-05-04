package info.vnk.billex.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import info.vnk.billex.R;
import info.vnk.billex.activity.customer.CustomerActivity;
import info.vnk.billex.activity.order.OrderActivity;
import info.vnk.billex.activity.product.ProductListingActivity;
import info.vnk.billex.base.BaseActivity;

public class HomeActivity extends BaseActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = this;
        setToolbar();
        setPreference(context);

        LinearLayout customerLayout = (LinearLayout) findViewById(R.id.ll_customer);
        LinearLayout orderLayout = (LinearLayout) findViewById(R.id.ll_orders);
        LinearLayout productLayout = (LinearLayout) findViewById(R.id.ll_product);
        LinearLayout paymentLayout = (LinearLayout) findViewById(R.id.ll_payment);
        LinearLayout aboutLayout = (LinearLayout) findViewById(R.id.ll_about);
        LinearLayout logoutLayout = (LinearLayout) findViewById(R.id.ll_logout);

        customerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CustomerActivity.class);
                startActivity(intent);
            }
        });

        orderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });

        productLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProductListingActivity.class);
                startActivity(intent);
            }
        });

        paymentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        aboutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferencesManager.clear();
                finish();
            }
        });
    }
}
