package info.vnk.billex.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import info.vnk.billex.R;
import info.vnk.billex.model.navigation.OrderModel;

/**
 * Created by priyesh on 22/04/17.
 */

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderViewHolder> {

    private List<OrderModel> orderModel;
    private int rowLayout;
    private Context context;

    public static class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView name, customerName, mobileNumber, checkIn, duration;

        public OrderViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.tv_name);
            customerName = (TextView) v.findViewById(R.id.tv_custmer_name);
        }
    }

    public OrderListAdapter(List<OrderModel> orderList, int rowLayout, Context context) {
        this.orderModel = orderList;
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

        holder.name.setText(orderModel.get(position).getOrderName());


    }

    @Override
    public int getItemCount() {
        return orderModel.size();
    }
}