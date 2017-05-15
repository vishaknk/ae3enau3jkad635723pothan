package info.vnk.billex.adapter.order;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.vnk.billex.R;
import info.vnk.billex.adapter.order.listener.ListOrderListener;
import info.vnk.billex.model.order.OrderListModel;
import info.vnk.billex.model.payment.PaymentDelete;
import info.vnk.billex.network.ApiClient;
import info.vnk.billex.network.ApiInterface;
import info.vnk.billex.utilities.General;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by priyesh on 22/04/17.
 */

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderViewHolder> implements Filterable {

    private List<OrderListModel> orderModel;
    private List<OrderListModel> filteredList;
    private List<OrderListModel> duplicateList;
    private int rowLayout;
    private Context context;
    private Filter filter;
    private MaterialDialog mMaterialDialog;
    private ProgressBar mProgressBar;
    private ListOrderListener listOrderListener;

    public Filter getFilter() {
        if (filter == null) {
            filter = new OrderFilter();
        }
        return filter;
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView name, customerName, price, orderDate, deliveryDate, discount;
        ImageView cancelOrder, editOrder;

        public OrderViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.tv_name);
            customerName = (TextView) v.findViewById(R.id.tv_custmer_name);
            price = (TextView) v.findViewById(R.id.tv_price);
            orderDate = (TextView) v.findViewById(R.id.tv_ordered_date);
            deliveryDate = (TextView) v.findViewById(R.id.tv_delivery_date);
            discount = (TextView) v.findViewById(R.id.tv_discount);
            cancelOrder = (ImageView) v.findViewById(R.id.iv_cancelOrder);
            editOrder = (ImageView) v.findViewById(R.id.iv_edit);
        }
    }

    public OrderListAdapter(List<OrderListModel> orderList, int rowLayout, Context context, ProgressBar mProgressBar) {
        this.orderModel = orderList;
        this.duplicateList = orderList;
        this.filteredList = orderList;
        this.rowLayout = rowLayout;
        this.context = context;
        this.mProgressBar = mProgressBar;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new OrderViewHolder(view);
    }


    @Override
    public void onBindViewHolder(OrderViewHolder holder, final int position) {

        if(General.isNullOrEmpty(orderModel.get(position).getOrderId())){
            holder.name.setVisibility(View.GONE);
        }else{
            holder.name.setVisibility(View.VISIBLE);
            holder.name.setText(General.capitalize(orderModel.get(position).getOrderId()));
        }
        if(General.isNullOrEmpty(orderModel.get(position).getCustomerName())){
            holder.customerName.setVisibility(View.GONE);
        }else{
            holder.customerName.setVisibility(View.VISIBLE);
            holder.customerName.setText(General.capitalize(orderModel.get(position).getCustomerName()));
        }
        if(General.isNullOrEmpty(orderModel.get(position).getTotalAmount())){
            holder.price.setVisibility(View.GONE);
        }else{
            holder.price.setVisibility(View.VISIBLE);
            holder.price.setText(context.getResources().getString(R.string.rupees)+  " " + orderModel.get(position).getTotalAmount());
        }
        if(General.isNullOrEmpty(orderModel.get(position).getDateOfOrder())){
            holder.orderDate.setVisibility(View.GONE);
        }else{
            holder.orderDate.setVisibility(View.VISIBLE);
            holder.orderDate.setText("Ordered Date : " + orderModel.get(position).getDateOfOrder());
        }
        if(General.isNullOrEmpty(orderModel.get(position).getDeliveryDate())){
            holder.deliveryDate.setVisibility(View.GONE);
        }else{
            holder.deliveryDate.setVisibility(View.VISIBLE);
            holder.deliveryDate.setText("Deliver Date : " + orderModel.get(position).getDeliveryDate());
        }

        if(General.isNullOrEmpty(orderModel.get(position).getDiscount())){
            holder.discount.setVisibility(View.GONE);
        }else{
            holder.discount.setVisibility(View.VISIBLE);
            holder.discount.setText("Discount : " + context.getResources().getString(R.string.rupees)+  " " + orderModel.get(position).getDiscount());
        }

        if(orderModel.get(position).getStatus().equals("0")){
            holder.cancelOrder.setVisibility(View.GONE);
        }else{
            holder.cancelOrder.setVisibility(View.VISIBLE);
        }

        holder.cancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMaterialDialog = new MaterialDialog(context)
                        .setTitle(R.string.order_title)
                        .setMessage(R.string.order_delete)
                        .setPositiveButton(R.string.yes, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cancelOrder(orderModel.get(position).getId(),position);
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

        holder.editOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListOrderListener().onEditClick(orderModel.get(position));
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
            ArrayList<OrderListModel> FilteredArrList = new ArrayList<>();
            /********
             *
             *  If constraint(CharSequence that is received) is null returns the mDisplayedList(ie the duplicate data of original list) values
             *  else does the Filtering and returns FilteredArrList(Filtered)
             *
             ********/
            if (constraint != null && constraint.length() > 0) {
                List<OrderListModel> order = duplicateList;
                for (int i = 0; i < duplicateList.size(); i++) {

                    String data = order.get(i).getOrderId();
                    String customers = order.get(i).getCustomerName();
                    if (data.toLowerCase().contains(constraint.toString()) || customers.toLowerCase().contains(constraint.toString())) {
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
            orderModel = (ArrayList<OrderListModel>) filterResults.values;
            notifyDataSetChanged();
        }
    }
    private void cancelOrder(String id, final int position) {
        mProgressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<PaymentDelete> call = apiService.cancelOrder(id);
        call.enqueue(new Callback<PaymentDelete>() {
            @Override
            public void onResponse(Call<PaymentDelete> call, Response<PaymentDelete> response) {
                orderModel.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, orderModel.size());
                General.showToast(context,response.body().getMessage());
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<PaymentDelete> call, Throwable t) {
                General.showToast(context,"Please try again later...");
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    public ListOrderListener getListOrderListener() {
        return listOrderListener;
    }

    public void setListOrderListener(ListOrderListener listOrderListener) {
        this.listOrderListener = listOrderListener;
    }
}