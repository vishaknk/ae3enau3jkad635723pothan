package info.vnk.billex.adapter.order;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import info.vnk.billex.R;
import info.vnk.billex.adapter.order.listener.OrderListener;
import info.vnk.billex.model.product.PostProductModel;
import info.vnk.billex.utilities.General;

/**
 * Created by Visak on 26-12-2016.
 */

public class AddOrderListAdapter extends RecyclerView.Adapter<AddOrderListAdapter.OrderListViewHolder> {

    private List<PostProductModel> orderListModel;
    private Context context;
    private OrderListener orderListener;

    public AddOrderListAdapter(List<PostProductModel> orderList, Context context) {
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
    public void onBindViewHolder(final OrderListViewHolder holder, final int position) {
        holder.productName.setText(General.capitalize(orderListModel.get(position).getPdt_name()));
        holder.price.setText(orderListModel.get(position).getPdt_tax());
        holder.quantity.setText(orderListModel.get(position).getPdt_qty());
        holder.mDiscount.setText(orderListModel.get(position).getPdt_discount());
        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderListener.minusClick(position);
            }
        });

        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderListener.plusClick(position);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderListener.deleteClick(position);
            }
        });
        holder.quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long quantity = Long.parseLong(orderListModel.get(position).getPdt_qty());
                orderListener.quantityClick(position, quantity);
            }
        });
        holder.mDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderListener.discountAdded(position, holder.mDiscount.getText().toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderListModel.size();
    }

    public void setListener(OrderListener listener) {
        orderListener = listener;
    }

    public static class OrderListViewHolder extends RecyclerView.ViewHolder {

        TextView productName, quantity, price, btnMinus, btnPlus;
        ImageView btnDelete;
        EditText mDiscount;
        LinearLayout rowItem;

        public OrderListViewHolder(View v) {
            super(v);
            rowItem = (LinearLayout) v.findViewById(R.id.item_row);
            productName = (TextView) v.findViewById(R.id.tv_product_name);
            quantity = (TextView) v.findViewById(R.id.tv_quantity);
            price = (TextView) v.findViewById(R.id.tv_price);
            btnMinus = (TextView) v.findViewById(R.id.tv_minus);
            btnPlus = (TextView) v.findViewById(R.id.tv_plus);
            btnDelete = (ImageView) v.findViewById(R.id.iv_delete);
            mDiscount = (EditText) v.findViewById(R.id.et_discount);
        }
    }
}