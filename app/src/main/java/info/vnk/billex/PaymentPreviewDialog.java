package info.vnk.billex;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import info.vnk.billex.model.payment.PaymentDetails;
import info.vnk.billex.model.payment.PaymentResultModel;
import info.vnk.billex.network.ApiClient;
import info.vnk.billex.network.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by priyesh on 06/05/17.
 */

public class PaymentPreviewDialog extends Dialog {
    private TextView mSummary, mPay;
    private String mCustomerId;
    private Context mContext;
    private ProgressBar mProgress;
    public PaymentPreviewDialog(@NonNull Context context, String custId) {
        super(context);
        this.mCustomerId = custId;
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_payment);
        mSummary = (TextView) findViewById(R.id.summary);
        mPay = (TextView) findViewById(R.id.tv_pay);
        mProgress = (ProgressBar) findViewById(R.id.pb_payment);
        getPaymentSummaryForCustomer();
    }

    private void getPaymentSummaryForCustomer() {
        setProgressBarVisible();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<PaymentResultModel> call = apiService.getPayment(mCustomerId);
        call.enqueue(new Callback<PaymentResultModel>() {
            @Override
            public void onResponse(Call<PaymentResultModel> call, Response<PaymentResultModel> response) {
                List<PaymentDetails> mPaymentList = response.body().getResult();
                if(mPaymentList.size() <= 0){
                    dismiss();
                    setProgressBarHide();
                    return;
                }
                setPaymentPreview(mPaymentList);
                setProgressBarHide();
            }

            @Override
            public void onFailure(Call<PaymentResultModel> call, Throwable t) {
                setProgressBarHide();
                Toast.makeText(mContext, "error"+t.getLocalizedMessage(), Toast.LENGTH_LONG);
            }
        });
    }

    private void setPaymentPreview(List<PaymentDetails> mPaymentList) {
        String summary =  mPaymentList.get(0).getCust_name() + " has "
                + mPaymentList.get(0).getAmount() + " as ";
        if(mPaymentList.get(0).getType().equals("C")){
            summary =  summary + " Credit balance.";
        }else{
            summary =  summary + " Debit balance. Do you want to pay now?";
        }
        mSummary.setText(summary);

    }

    private void setProgressBarHide() {
        mProgress.setVisibility(View.INVISIBLE);
    }

    private void setProgressBarVisible() {
        mProgress.setVisibility(View.VISIBLE);
    }
}
