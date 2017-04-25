package info.vnk.billex.custom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import info.vnk.billex.R;
import info.vnk.billex.model.product.ProductModel;

/**
 * Created by Visak on 26-12-2016.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private List<ProductModel> productListModel;
    private Context context;
    private CustomListener listener;


    public static class SearchViewHolder extends RecyclerView.ViewHolder {

        TextView itemName;
        LinearLayout mainRow;

        public SearchViewHolder(View v) {
            super(v);
            mainRow = (LinearLayout) v.findViewById(R.id.ll_main);
            itemName = (TextView) v.findViewById(R.id.tv_item_name);
        }
    }

    public SearchAdapter(List<ProductModel> productList, Context context, CustomListener customListener) {
        this.productListModel = productList;
        this.context = context;
        this.listener = customListener;
    }


    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_list, parent, false);
        return new SearchViewHolder(view);
    }


    @Override
    public void onBindViewHolder(SearchViewHolder holder, final int position) {
        holder.itemName.setText(productListModel.get(position).getPdtName());
        holder.mainRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemSelectedSearch();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productListModel.size();
    }
}

