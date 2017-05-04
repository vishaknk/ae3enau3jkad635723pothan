package info.vnk.billex.activity.customer;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import info.vnk.billex.R;
import info.vnk.billex.base.BaseActivity;
import info.vnk.billex.model.customer.PostCustomerModel;
import info.vnk.billex.model.customer.PostMainCustomerModel;
import info.vnk.billex.model.order.PostMainOrderModel;
import info.vnk.billex.model.order.PostOrderResultModel;
import info.vnk.billex.network.ApiClient;
import info.vnk.billex.network.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by priyesh on 04/05/17.
 */

public class CreateCustomerActivity extends BaseActivity {
    private Context context;
    private Toolbar toolbar;
    private EditText mAccCode, mHead, mAddress1, mAddress2, mPhone, mTin, mAgcode, mCreditDays;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_customer);

        context = this;
        setPreference(context);
        toolbar = setToolbar();
        initToolbar();
        init();
    }

    private void init() {
        mAccCode = (EditText) findViewById(R.id.et_acccode);
        mHead = (EditText) findViewById(R.id.et_acccode);
        mAddress1 = (EditText) findViewById(R.id.et_address1);
        mAddress2 = (EditText) findViewById(R.id.et_address2);
        mPhone = (EditText) findViewById(R.id.et_phone);
        mTin = (EditText) findViewById(R.id.et_tin);
        mAgcode = (EditText) findViewById(R.id.et_agcode);
        mCreditDays = (EditText) findViewById(R.id.et_creditDays);

    }

    private void initToolbar() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }



    public void AddCustomer(View view){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<PostCustomerModel> call = apiService.postCustomer(postCustomer());
        call.enqueue(new Callback<PostCustomerModel>() {
            @Override
            public void onResponse(Call<PostCustomerModel> call, Response<PostCustomerModel> response) {
                finish();
            }

            @Override
            public void onFailure(Call<PostCustomerModel> call, Throwable t) {
                Toast.makeText(context, "error" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private PostMainCustomerModel postCustomer() {
        PostCustomerModel mModel = new PostCustomerModel();
        mModel.setAccCode(mAccCode.getText().toString().trim());
        mModel.setHead(mHead.getText().toString().trim());
        mModel.setAddress(mAddress1.getText().toString().trim() + ", " + mAddress2.getText().toString().trim());
        mModel.setAgcode(mAgcode.getText().toString().trim());
        mModel.setPhone(mPhone.getText().toString().trim());
        mModel.setTin(mTin.getText().toString().trim());
        mModel.setCreditDays(mCreditDays.getText().toString().trim());

        PostMainCustomerModel customerMain = new PostMainCustomerModel();
        customerMain.setModel(mModel);
        return customerMain;

    }

}
