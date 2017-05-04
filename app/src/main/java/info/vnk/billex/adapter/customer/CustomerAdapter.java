package info.vnk.billex.adapter.customer;

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
import info.vnk.billex.model.customer.CustomerModel;

/**
 * Created by priyesh on 25/04/17.
 */

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> implements Filterable {

    private List<CustomerModel> orderModel;
    private List<CustomerModel> filteredList;
    private List<CustomerModel> duplicateList;
    private int rowLayout;
    private Context context;
    private Filter filter;

    public Filter getFilter() {
        if (filter == null) {
            filter = new OrderFilter();
        }
        return filter;
    }

    public static class CustomerViewHolder extends RecyclerView.ViewHolder {

        TextView name, customerName, price, phone, email;

        public CustomerViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.tv_name);
            customerName = (TextView) v.findViewById(R.id.tv_custmer_name);
            price = (TextView) v.findViewById(R.id.tv_price);
            phone = (TextView) v.findViewById(R.id.tv_ordered_date);
            email = (TextView) v.findViewById(R.id.tv_delivery_date);
        }
    }

    public CustomerAdapter(List<CustomerModel> orderList, int rowLayout, Context context) {
        this.orderModel = orderList;
        this.duplicateList = orderList;
        this.filteredList = orderList;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new CustomerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(CustomerViewHolder holder, final int position) {

        holder.name.setText(orderModel.get(position).getCustName());
        holder.customerName.setText(orderModel.get(position).getCustAddress());
        holder.price.setVisibility(View.GONE);
//        holder.price.setText(context.getResources().getString(R.string.rupees)+ orderModel.get(position).getMrp());
        if(orderModel.get(position).getCustPhone() != null && !orderModel.get(position).getCustPhone().equals("")) {
            holder.phone.setVisibility(View.VISIBLE);
            holder.phone.setText("Phone : " + orderModel.get(position).getCustPhone());
        }else{
            holder.phone.setVisibility(View.GONE);
        }
        if(orderModel.get(position).getCustEmail() != null && !orderModel.get(position).getCustEmail().equals("")) {
            holder.email.setVisibility(View.VISIBLE);
            holder.email.setText("Email : " + orderModel.get(position).getCustEmail());
        }else{
            holder.email.setVisibility(View.GONE);
        }


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
                    String customers = order.get(i).getCustAddress();
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
            orderModel = (ArrayList<CustomerModel>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}
