package info.vnk.billex.activity.customer;

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
import info.vnk.billex.adapter.customer.CustomerAdapter;
import info.vnk.billex.base.BaseActivity;
import info.vnk.billex.model.customer.CustomerModel;
import info.vnk.billex.model.customer.CustomerResultModel;
import info.vnk.billex.network.ApiClient;
import info.vnk.billex.network.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerActivity extends BaseActivity {
    private Context mContext;
    private RecyclerView recyclerView;
    private CustomerAdapter adapter;
    private FloatingActionButton mCreateOrder;
    private EditText mSearch;
    private List<CustomerModel> mOrderList;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        init();
        mContext = this;
        setToolbar();
        setRecyclerAdapter();
    }
    // call api to fetch data
    private void getOrderList() {
        setProgressBarVisible();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CustomerResultModel> call = apiService.getCustomer();
        call.enqueue(new Callback<CustomerResultModel>() {
            @Override
            public void onResponse(Call<CustomerResultModel> call, Response<CustomerResultModel> response) {
                mOrderList = response.body().getResult();
                setRecyclerAdapter();
                setProgressBarHide();
            }

            @Override
            public void onFailure(Call<CustomerResultModel> call, Throwable t) {
                setProgressBarHide();
                Toast.makeText(mContext, "error"+t.getLocalizedMessage(), Toast.LENGTH_LONG);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getOrderList();
        mCreateOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CreateCustomerActivity.class);
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
        adapter = new CustomerAdapter(mOrderList, R.layout.list_item_customer, mContext);
        recyclerView.setAdapter(adapter);
    }


    public void setProgressBarVisible() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void setProgressBarHide() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

}
