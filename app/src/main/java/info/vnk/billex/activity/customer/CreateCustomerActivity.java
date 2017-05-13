package info.vnk.billex.activity.customer;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import info.vnk.billex.R;
import info.vnk.billex.base.BaseActivity;
import info.vnk.billex.model.customer.PostCustomerModel;
import info.vnk.billex.network.ApiClient;
import info.vnk.billex.network.ApiInterface;
import info.vnk.billex.utilities.Constants;
import info.vnk.billex.utilities.General;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by priyesh on 04/05/17.
 */

public class CreateCustomerActivity extends BaseActivity {
    private Context context;
    private Toolbar toolbar;
    private ProgressBar mProgressBar;
    private EditText mName, mAddress1, mAddress2, mPhone, mTin, mCreditDays;
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
        mName = (EditText) findViewById(R.id.et_name);
        mAddress1 = (EditText) findViewById(R.id.et_address1);
        mAddress2 = (EditText) findViewById(R.id.et_address2);
        mPhone = (EditText) findViewById(R.id.et_phone);
        mTin = (EditText) findViewById(R.id.et_tin);
        mCreditDays = (EditText) findViewById(R.id.et_creditDays);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_login);

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
        validate();
        mProgressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<PostCustomerModel> call = apiService.postCustomer(postCustomer());
        call.enqueue(new Callback<PostCustomerModel>() {
            @Override
            public void onResponse(Call<PostCustomerModel> call, Response<PostCustomerModel> response) {
                General.showToast(CreateCustomerActivity.this,response.body().getMessage());
                mProgressBar.setVisibility(View.INVISIBLE);
                finish();
            }

            @Override
            public void onFailure(Call<PostCustomerModel> call, Throwable t) {
                mProgressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(context, "error" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void validate() {
        if(mName.getText().toString().trim().equals("")
                || mPhone.getText().toString().trim().equals("")
                || mAddress1.getText().toString().trim().equals("")
                || mAddress2.getText().toString().trim().equals("")
                || mTin.getText().toString().trim().equals("")){
            General.showToast(this,"Empty Validation");
            return;
        }
    }

    private PostCustomerModel postCustomer() {
        PostCustomerModel mModel = new PostCustomerModel();
        mModel.setName(mName.getText().toString().trim());
        mModel.setAddress(mAddress1.getText().toString().trim() + ", " + mAddress2.getText().toString().trim());
        mModel.setPhone(mPhone.getText().toString().trim());
        mModel.setTin(mTin.getText().toString().trim());
        mModel.setCreditDays(mCreditDays.getText().toString().trim());
        mModel.setStaff_id(preferencesManager.getString(Constants.mUserId));
        return mModel;

    }

}
