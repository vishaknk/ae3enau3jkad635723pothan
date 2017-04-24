package info.vnk.billex.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.vnk.billex.R;
import info.vnk.billex.model.navigation.order.OrderListModel;

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

    public Filter getFilter() {
        if (filter == null) {
            filter = new OrderFilter();
        }
        return filter;
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView name, customerName, price, orderDate, deliveryDate;

        public OrderViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.tv_name);
            customerName = (TextView) v.findViewById(R.id.tv_custmer_name);
            price = (TextView) v.findViewById(R.id.tv_price);
            orderDate = (TextView) v.findViewById(R.id.tv_ordered_date);
            deliveryDate = (TextView) v.findViewById(R.id.tv_delivery_date);
        }
    }

    public OrderListAdapter(List<OrderListModel> orderList, int rowLayout, Context context) {
        this.orderModel = orderList;
        this.duplicateList = orderList;
        this.filteredList = orderList;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new OrderViewHolder(view);
    }


    @Override
    public void onBindViewHolder(OrderViewHolder holder, final int position) {

        holder.name.setText(orderModel.get(position).getOrderId());
        holder.customerName.setText(orderModel.get(position).getCustomerName());
        holder.price.setText(context.getResources().getString(R.string.rupees)+ orderModel.get(position).getTotalAmount());
        holder.orderDate.setText("Ordered Date : " + orderModel.get(position).getDateOfOrder());
        holder.deliveryDate.setText("Deliver Date : " + orderModel.get(position).getDeliveryDate());


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
}