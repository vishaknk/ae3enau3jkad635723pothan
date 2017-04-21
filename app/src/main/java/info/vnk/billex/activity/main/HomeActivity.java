package info.vnk.billex.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import info.vnk.billex.R;
import info.vnk.billex.activity.order.OrderActivity;
import info.vnk.billex.base.BaseActivity;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        LinearLayout customerLayout = (LinearLayout) findViewById(R.id.ll_customer);
        LinearLayout orderLayout = (LinearLayout) findViewById(R.id.ll_orders);
        LinearLayout productLayout = (LinearLayout) findViewById(R.id.ll_products);
        LinearLayout paymentLayout = (LinearLayout) findViewById(R.id.ll_payment);

        customerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

            }
        });

        paymentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
