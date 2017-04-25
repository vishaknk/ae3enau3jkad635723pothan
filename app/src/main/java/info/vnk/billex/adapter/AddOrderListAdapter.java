package info.vnk.billex.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;
import info.vnk.billex.R;
import info.vnk.billex.model.order.OrderListModel;

/**
 * Created by Visak on 26-12-2016.
 */

public class AddOrderListAdapter extends RecyclerView.Adapter<AddOrderListAdapter.OrderListViewHolder> {

    private List<OrderListModel> orderListModel;
    private Context context;


    public static class OrderListViewHolder extends RecyclerView.ViewHolder {

        TextView productName, quantity, price;
        LinearLayout rowItem;
        ImageButton btnMinus, btnPlus;

        public OrderListViewHolder(View v) {
            super(v);
            rowItem = (LinearLayout) v.findViewById(R.id.item_row);
            productName = (TextView) v.findViewById(R.id.tv_product_name);
            quantity = (TextView) v.findViewById(R.id.tv_quantity);
            price = (TextView) v.findViewById(R.id.tv_price);
            btnMinus = (ImageButton) v.findViewById(R.id.ib_minus);
            btnPlus = (ImageButton) v.findViewById(R.id.ib_plus);
        }
    }

    public AddOrderListAdapter(List<OrderListModel> orderList, Context context) {
        this.orderListModel = orderList;
        this.context = context;
    }


    @Override
    public AddOrderListAdapter.OrderListViewHolder onCreateViewHolder(ViewGroup parent,
                                                                      int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_order_list, parent, false);
        return new OrderListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(OrderListViewHolder holder, final int position) {
        //holder.companyName.setText();
        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int data = Integer.parseInt(orderListModel.get(position).getTotalQuantity());
                if(data > 0)
                    orderListModel.get(position).setTotalQuantity("" + data--);
                notifyDataSetChanged();
            }
        });

        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int data = Integer.parseInt(orderListModel.get(position).getTotalQuantity());
                orderListModel.get(position).setTotalQuantity("" + data++);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderListModel.size();
    }
}