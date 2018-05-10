package info.vnk.billex.activity.payment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import info.vnk.billex.adapter.payment.PaymentAdapter;
import info.vnk.billex.base.BaseActivity;
import info.vnk.billex.model.customer.CustomerModel;
import info.vnk.billex.model.customer.CustomerResultModel;
import info.vnk.billex.network.ApiClient;
import info.vnk.billex.network.ApiInterface;
import info.vnk.billex.utilities.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by priyesh on 05/05/17.
 */

public class PaymentActivity extends BaseActivity {

    private Context mContext;
    private RecyclerView recyclerView;
    private PaymentAdapter adapter;
    private EditText mSearch;
    private List<CustomerModel> mOrderList;
    private static ProgressBar mProgressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        init();
        mContext = this;
        setToolbar();
        setRecyclerAdapter();
        getPaymentList();
    }
    // call api to fetch data
    private void getPaymentList() {
        setProgressBarVisible();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CustomerResultModel> call = apiService.getCustomer(preferencesManager.getString(Constants.mUserId), preferencesManager.getString(Constants.mRole));
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
        mSearch = (EditText) findViewById(R.id.et_search);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_login);
    }

    private void setRecyclerAdapter() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_prospect);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PaymentAdapter(mOrderList, R.layout.list_item_payment, mContext,mProgressBar);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 1){
            mSearch.setText("");
            getPaymentList();

        }else{
            adapter.notifyDataSetChanged();
        }
    }

    public static void setProgressBarVisible() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public static void setProgressBarHide() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }
}
