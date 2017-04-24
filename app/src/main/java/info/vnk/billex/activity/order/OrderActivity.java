package info.vnk.billex.activity.order;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import info.vnk.billex.R;
import info.vnk.billex.base.BaseActivity;

public class OrderActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_order);
        init();
    }

    private void init() {

        View viewOrder = findViewById(R.id.content_order_layout);
        RecyclerView recyclerOrder = (RecyclerView) viewOrder.findViewById(R.id.rv_order);
        FloatingActionButton btnAddFab = (FloatingActionButton) findViewById(R.id.fab_add);

        btnAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action

            }
        });
    }
}
