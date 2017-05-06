package info.vnk.billex.activity.payment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.vnk.billex.R;
import info.vnk.billex.base.BaseActivity;
import info.vnk.billex.model.payment.PaymentDetails;
import info.vnk.billex.model.payment.PaymentResultModel;
import info.vnk.billex.model.payment.PostPaymentModel;
import info.vnk.billex.network.ApiClient;
import info.vnk.billex.network.ApiInterface;
import info.vnk.billex.utilities.General;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static info.vnk.billex.utilities.Constants.CUSTOMER_ID;

/**
 * Created by priyesh on 06/05/17.
 */

public class PaymentDetailActivity extends BaseActivity {

    private TextView mSummary, mPay;
    private String mCustomerId;
    private String mStatus;
    private Context mContext;
    private EditText mAmount;
    private ProgressBar mProgress;
    private MaterialDialog mMaterialDialog;
    private int mBalance = 0, mTotalInhand = 0;
    private List<PaymentDetails> mPaymentList;
    private Spinner mSpCredit;
    private ArrayList<String>mPreferList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_detail);
        mContext = this;
        mCustomerId = getIntent().getStringExtra(CUSTOMER_ID);
        mSummary = (TextView) findViewById(R.id.summary);
        mPay = (TextView) findViewById(R.id.tv_pay);
        mProgress = (ProgressBar) findViewById(R.id.pb_payment);
        mAmount = (EditText) findViewById(R.id.et_amount);
        mSpCredit = (Spinner) findViewById(R.id.sv_credit);
        mPreferList = new ArrayList<>();
        mPreferList.add("Credit");
        mPreferList.add("Debit");
        ArrayAdapter adapter = new ArrayAdapter(mContext, R.layout.spinner, mPreferList);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        mSpCredit.setAdapter(adapter);
        getPaymentSummaryForCustomer();

        mPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMaterialDialog = new MaterialDialog(mContext)
                        .setTitle(R.string.payment)
                        .setMessage(R.string.payment_details)
                        .setPositiveButton(R.string.yes, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CalPaymentApi();
                                mMaterialDialog.dismiss();
                            }
                        })
                        .setNegativeButton(R.string.no, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog.dismiss();
                                return;
                            }
                        });
                mMaterialDialog.show();
            }
        });
        mSpCredit .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    // api after confrming paymnet api
    private void CalPaymentApi() {
        setProgressBarVisible();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<PostPaymentModel> call = apiService.postPayment(preparepayment());
        call.enqueue(new Callback<PostPaymentModel>() {
            @Override
            public void onResponse(Call<PostPaymentModel> call, Response<PostPaymentModel> response) {

                General.showToast(mContext,response.body().getMessage());
                finish();

                setProgressBarHide();
            }

            @Override
            public void onFailure(Call<PostPaymentModel> call, Throwable t) {
                setProgressBarHide();
                General.showToast(mContext,"Please try again later...");
            }
        });
    }

    private PostPaymentModel preparepayment() {
        int amount = 0;
        PostPaymentModel model = new PostPaymentModel();
        if(mPaymentList == null || mPaymentList.size() <= 0) {
            setProgressBarHide();
            return model;
        }
        model.setAmount(mAmount.getText().toString().trim());
        model.setCust_id(mCustomerId);
        int payamount = Integer.parseInt(mAmount.getText().toString().trim());
        int inHand = mTotalInhand;
        if(payamount - inHand > 0){
            mBalance =  payamount - inHand;
            mStatus = "C";
        }else{
            mBalance =  inHand - payamount;
            mStatus = "D";
        }
        model.setPaid_date(General.getCurrentdate());
        model.setAmount(String.valueOf(payamount));
        if(mPreferList.get(mSpCredit.getSelectedItemPosition()).equals("Credit"))
            model.setType("C");
        else
            model.setType("D");
        return model;

    }

    private void getPaymentSummaryForCustomer() {
        setProgressBarVisible();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<PaymentResultModel> call = apiService.getPayment(mCustomerId);
        call.enqueue(new Callback<PaymentResultModel>() {
            @Override
            public void onResponse(Call<PaymentResultModel> call, Response<PaymentResultModel> response) {
                mPaymentList = response.body().getResult();
                if(mPaymentList.size() <= 0){
                    General.showToast(mContext,"Zero Payment");
                    setProgressBarHide();
                    finish();
                    return;
                }
                setPaymentPreview(mPaymentList);
                setProgressBarHide();
            }

            @Override
            public void onFailure(Call<PaymentResultModel> call, Throwable t) {
                setProgressBarHide();
                General.showToast(mContext,"Please try again later...");
            }
        });
    }

    private void setPaymentPreview(List<PaymentDetails> mPaymentList) {
        mTotalInhand= 0 ;
        for(int  i = 0 ; i < mPaymentList.size(); i++){
            if(mPaymentList.get(i).getType().equals("DEBIT")) {
                mTotalInhand = mTotalInhand - Integer.parseInt(mPaymentList.get(i).getAmount());
            }else{
                mTotalInhand = mTotalInhand + Integer.parseInt(mPaymentList.get(i).getAmount());
            }
        }
        String summary =  mPaymentList.get(0).getCust_name() + " have sum of "
                + "<b>" + mTotalInhand + "</b>" + " as ";
        if(mTotalInhand < 0){
            summary =  summary + " Debit balance.";
        }else{
            summary =  summary + " Credit balance";

        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            mSummary.setText(Html.fromHtml(summary,Html.FROM_HTML_MODE_LEGACY));
        } else {
            mSummary.setText(Html.fromHtml(summary));
        }

    }

    private void setProgressBarHide() {
        mProgress.setVisibility(View.INVISIBLE);
    }

    private void setProgressBarVisible() {
        mProgress.setVisibility(View.VISIBLE);
    }
}
