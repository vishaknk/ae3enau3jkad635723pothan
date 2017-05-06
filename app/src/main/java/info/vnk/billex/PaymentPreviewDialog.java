package info.vnk.billex;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

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

/**
 * Created by priyesh on 06/05/17.
 */

public class PaymentPreviewDialog extends Dialog {
    private TextView mSummary, mPay;
    private String mCustomerId;
    private Context mContext;
    private ProgressBar mProgress;
    private MaterialDialog mMaterialDialog;
    private List<PaymentDetails> mPaymentList;
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
                setProgressBarHide();
                dismiss();
            }

            @Override
            public void onFailure(Call<PostPaymentModel> call, Throwable t) {
                setProgressBarHide();
                General.showToast(mContext,"Please try again later...");
                dismiss();
            }
        });
    }

    private PostPaymentModel preparepayment() {

        PostPaymentModel model = new PostPaymentModel();
        if(mPaymentList == null || mPaymentList.size() <= 0) {
            setProgressBarHide();
            return model;
        }
        model.setAmount(mPaymentList.get(0).getAmount());
        model.setCust_id(mPaymentList.get(0).getCust_id());
        if(mPaymentList.get(0).getType().equals("CREDIT")) {
            model.setType("C");
        }else{
            model.setType("D");
        }
        model.setPaid_date(mPaymentList.get(0).getPay_date());

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
                General.showToast(mContext,"Please try again later...");
                dismiss();
            }
        });
    }

    private void setPaymentPreview(List<PaymentDetails> mPaymentList) {
        String summary =  mPaymentList.get(0).getCust_name() + " have sum of "
                + "<b>" + mPaymentList.get(0).getAmount() + "</b>" + " as ";
        if(mPaymentList.get(0).getType().equals("DEBIT")){
            summary =  summary + " Debit balance. Do you want to pay it now?";
        }else{
            summary =  summary + " Credit balance. Do you want to pay it now?";

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
