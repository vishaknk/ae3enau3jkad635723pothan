package info.vnk.billex.activity.order;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import info.vnk.billex.R;
import info.vnk.billex.adapter.AddOrderListAdapter;
import info.vnk.billex.base.BaseActivity;
import info.vnk.billex.custom.FullScreenSearch;

public class AddOrderActivity extends BaseActivity {
    private Context mContext;
    private RecyclerView recyclerView;
    private AddOrderListAdapter adapter;
    private FloatingActionButton mCreateOrder;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        context = this;
        init();
    }

    private void init() {

        View viewOrder = findViewById(R.id.content_order_layout);
        FullScreenSearch fullScreenSearch = (FullScreenSearch) viewOrder.findViewById(R.id.custom_search);
        RecyclerView recyclerOrder = (RecyclerView) viewOrder.findViewById(R.id.rv_order);
        recyclerOrder.setLayoutManager(new LinearLayoutManager(context));
        FloatingActionButton btnAddFab = (FloatingActionButton) findViewById(R.id.fab_add);
        btnAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action

            }
        });
    }

}

