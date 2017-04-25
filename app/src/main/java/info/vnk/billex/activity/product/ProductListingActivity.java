package info.vnk.billex.activity.product;

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

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import info.vnk.billex.R;
import info.vnk.billex.activity.order.AddOrderActivity;
import info.vnk.billex.adapter.ProductAdapter;
import info.vnk.billex.base.BaseActivity;
import info.vnk.billex.model.product.ProductModel;
import info.vnk.billex.model.product.ProductResultModel;
import info.vnk.billex.network.ApiClient;
import info.vnk.billex.network.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by priyesh on 25/04/17.
 */

public class ProductListingActivity extends BaseActivity {

    private Context mContext;
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private FloatingActionButton mCreateOrder;
    private EditText mSearch;
    private List<ProductModel> mProductList;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        init();
        mContext = this;
        setToolbar();
        getOrderList();
        setRecyclerAdapter();

    }

    // call api to fetch data
    private void getOrderList() {
        setProgressBarVisible();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ProductResultModel> call = apiService.getProduct();
        call.enqueue(new Callback<ProductResultModel>() {
            @Override
            public void onResponse(Call<ProductResultModel> call, Response<ProductResultModel> response) {
                mProductList = response.body().getResult();
                setRecyclerAdapter();
                setProgressBarHide();
            }

            @Override
            public void onFailure(Call<ProductResultModel> call, Throwable t) {
                setProgressBarHide();
                Toast.makeText(mContext, "error" + t.getLocalizedMessage(), Toast.LENGTH_LONG);
            }
        });

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
        mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() != 0) {
                    if (mProductList != null)
                        adapter.getFilter().filter(s.toString());
                }
                if (mProductList != null)
                    adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void init() {
        mProductList = new ArrayList<>();
        mCreateOrder = (FloatingActionButton) findViewById(R.id.fab_create_message);
        mSearch = (EditText) findViewById(R.id.et_search);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_login);
        mCreateOrder.setVisibility(View.GONE);
    }

    private void setRecyclerAdapter() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_prospect);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter(mProductList, R.layout.item_order_list, mContext);
        recyclerView.setAdapter(adapter);
    }


    public void setProgressBarVisible() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void setProgressBarHide() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }
}