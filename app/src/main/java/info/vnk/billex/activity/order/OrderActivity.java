package info.vnk.billex.activity.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import info.vnk.billex.R;
import info.vnk.billex.adapter.order.OrderListAdapter;
import info.vnk.billex.adapter.order.listener.ListOrderListener;
import info.vnk.billex.base.BaseActivity;
import info.vnk.billex.model.order.OrderListModel;
import info.vnk.billex.model.order.OrderResultModel;
import info.vnk.billex.network.ApiClient;
import info.vnk.billex.network.ApiInterface;
import info.vnk.billex.utilities.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderActivity extends BaseActivity {
    private Context mContext;
    private RecyclerView recyclerView;
    private OrderListAdapter adapter;
    private FloatingActionButton mCreateOrder;
    private EditText mSearch;
    private List<OrderListModel>mOrderList;
    private static ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        init();
        mContext = OrderActivity.this;
        setToolbar();
    }



    // call api to fetch data
    private void getOrderList() {
        setProgressBarVisible();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<OrderResultModel> call = apiService.getOrderList(preferencesManager.getString(Constants.mUserId));
        call.enqueue(new Callback<OrderResultModel>() {
            @Override
            public void onResponse(Call<OrderResultModel> call, Response<OrderResultModel> response) {
                mOrderList = response.body().getResult();
                setRecyclerAdapter();
                setProgressBarHide();
            }

            @Override
            public void onFailure(Call<OrderResultModel> call, Throwable t) {
                setProgressBarHide();
                Toast.makeText(OrderActivity.this, "error"+t.getLocalizedMessage(), Toast.LENGTH_LONG);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getOrderList();
        setRecyclerAdapter();
        mCreateOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddOrderActivity.class);
                startActivity(intent);
            }
        });
        mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() != 0) {
                    if (mOrderList != null)
                        adapter.getFilter().filter(s.toString());
                }
                if (mOrderList != null)
                    adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void init() {
        mOrderList = new ArrayList<>();
        mCreateOrder = (FloatingActionButton)findViewById(R.id.fab_create_message);
        mSearch = (EditText) findViewById(R.id.et_search);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_login);
    }

    private void setRecyclerAdapter() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_prospect);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OrderListAdapter(mOrderList, R.layout.item_order_list, mContext, mProgressBar);
        adapter.setListOrderListener(new ListOrderListener() {
            @Override
            public void onEditClick(OrderListModel model) {
                Intent intent = new Intent(OrderActivity.this, AddOrderActivity.class);
                intent.putExtra(Constants.IS_EDIT, true);
                intent.putExtra(Constants.CUSTOMER_ID, "" + model.getCustomerId());
                intent.putExtra(Constants.CUSTOMER_NAME, "" + model.getCustomerName());
                intent.putExtra(Constants.ORDER_ID, "" + model.getOrderId());
                //Date of Delivery
                intent.putExtra(Constants.DOD, "" + model.getDeliveryDate());
                //Date of Order
                intent.putExtra(Constants.DOO, "" + model.getDateOfOrder());
                startActivity(intent);
            }

            @Override
            public void onDeleteClick() {
            }
        });
        recyclerView.setAdapter(adapter);
    }


    public static void setProgressBarVisible() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public static void setProgressBarHide() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

}
