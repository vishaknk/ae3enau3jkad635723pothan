package info.vnk.billex.adapter.payment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.vnk.billex.PaymentPreviewDialog;
import info.vnk.billex.R;
import info.vnk.billex.model.customer.CustomerModel;
import info.vnk.billex.model.payment.PaymentDelete;
import info.vnk.billex.network.ApiClient;
import info.vnk.billex.network.ApiInterface;
import info.vnk.billex.utilities.General;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by priyesh on 05/05/17.
 */

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.CustomerViewHolder> implements Filterable {

    private List<CustomerModel> orderModel = new ArrayList<>();
    private List<CustomerModel> filteredList;
    private List<CustomerModel> duplicateList;
    private int rowLayout;
    private Context context;
    private Filter filter;
    private MaterialDialog mMaterialDialog;

    public Filter getFilter() {
        if (filter == null) {
            filter = new OrderFilter();
        }
        return filter;
    }

    public static class CustomerViewHolder extends RecyclerView.ViewHolder {

        TextView name, customerName, pay;
        ImageView delete;

        public CustomerViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.tv_name);
            customerName = (TextView) v.findViewById(R.id.tv_custmer_name);
            pay = (TextView) v.findViewById(R.id.tv_pay);
            delete = (ImageView) v.findViewById(R.id.iv_delete);
        }
    }

    public PaymentAdapter(List<CustomerModel> orderList, int rowLayout, Context context) {
        this.orderModel = orderList;
        this.duplicateList = orderList;
        this.filteredList = orderList;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new PaymentAdapter.CustomerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(CustomerViewHolder holder, final int position) {

        if(General.isNullOrEmpty(orderModel.get(position).getCustName())){
            holder.name.setVisibility(View.GONE);
        }else{
            holder.name.setVisibility(View.VISIBLE);
            holder.name.setText(orderModel.get(position).getCustName());
        }

        if(General.isNullOrEmpty(orderModel.get(position).getCustAddress())){
            holder.customerName.setVisibility(View.VISIBLE);
        }else{
            holder.customerName.setVisibility(View.VISIBLE);
            holder.customerName.setText(orderModel.get(position).getCustAddress());
        }

        holder.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentPreviewDialog mDialog = new PaymentPreviewDialog(context,String.valueOf(orderModel.get(position).getId()));
                mDialog.show();
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMaterialDialog = new MaterialDialog(context)
                        .setTitle(R.string.payment)
                        .setMessage(R.string.payment_delete)
                        .setPositiveButton(R.string.yes, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CalPaymentDeleteApi(String.valueOf(orderModel.get(position).getId()),position);
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

    private void CalPaymentDeleteApi(String mCustomerId, final int position) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<PaymentDelete> call = apiService.deletePayment(mCustomerId);
        call.enqueue(new Callback<PaymentDelete>() {
            @Override
            public void onResponse(Call<PaymentDelete> call, Response<PaymentDelete> response) {
                orderModel.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, orderModel.size());
                General.showToast(context,response.body().getMessage());
            }

            @Override
            public void onFailure(Call<PaymentDelete> call, Throwable t) {
                General.showToast(context,"Please try again later...");
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderModel.size();
    }

    private class OrderFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            constraint = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
            ArrayList<CustomerModel> FilteredArrList = new ArrayList<>();
            /********
             *
             *  If constraint(CharSequence that is received) is null returns the mDisplayedList(ie the duplicate data of original list) values
             *  else does the Filtering and returns FilteredArrList(Filtered)
             *
             ********/
            if (constraint != null && constraint.length() > 0) {
                List<CustomerModel> order = duplicateList;
                for (int i = 0; i < duplicateList.size(); i++) {
                    String data = order.get(i).getCustName();
                    if (data != null && data.toLowerCase().contains(constraint.toString())) {
                        FilteredArrList.add(order.get(i));
                    }
                }
                // set the Filtered result to return
                results.count = FilteredArrList.size();
                results.values = FilteredArrList;
            } else {
                results.count = filteredList.size();
                results.values = filteredList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            orderModel = (ArrayList<CustomerModel>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}
