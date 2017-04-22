package info.vnk.billex.activity.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import info.vnk.billex.R;
import info.vnk.billex.activity.main.AddOrderActivity;
import info.vnk.billex.adapter.OrderListAdapter;
import info.vnk.billex.base.BaseActivity;
import info.vnk.billex.model.navigation.OrderModel;

public class OrderActivity extends BaseActivity {
    private Context mContext;
    private RecyclerView recyclerView;
    private OrderListAdapter adapter;
    private FloatingActionButton mCreateOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        init();
        mContext = OrderActivity.this;
        setToolbar();
        setRecyclerAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCreateOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddOrderActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        mCreateOrder = (FloatingActionButton)findViewById(R.id.fab_create_message);
    }

    private void setRecyclerAdapter() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_prospect);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OrderListAdapter(getAllOrders(), R.layout.item_order_list, mContext);
        recyclerView.setAdapter(adapter);
    }

    private List<OrderModel> getAllOrders() {
        List<OrderModel> Orders = new ArrayList<>();
        return Orders;
    }
}
