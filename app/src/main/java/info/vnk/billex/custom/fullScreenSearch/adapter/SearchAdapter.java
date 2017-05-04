package info.vnk.billex.custom.fullScreenSearch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.vnk.billex.R;
import info.vnk.billex.custom.fullScreenSearch.listener.CustomListener;
import info.vnk.billex.custom.fullScreenSearch.model.SearchModel;

/**
 * Created by Visak on 26-12-2016.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private List<SearchModel> searchItemModel;
    private List<SearchModel> filteredList;
    private List<SearchModel> duplicateList;
    private Context context;
    private CustomListener listener;
    private Filter filter;
    private int key;

    public SearchAdapter(List<SearchModel> searchList, Context context, int keyValue, CustomListener customListener) {
        this.searchItemModel = searchList;
        this.duplicateList = searchList;
        this.filteredList = searchList;
        this.context = context;
        this.listener = customListener;
        this.key = keyValue;
    }

    public Filter getFilter() {
        if (filter == null) {
            filter = new AddOrderFilter();
        }
        return filter;
    }

    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, final int position) {
        holder.itemName.setText("" + searchItemModel.get(position).getName());
        holder.mainRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemSelectedSearch(key, searchItemModel.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchItemModel.size();
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {

        TextView itemName;
        LinearLayout mainRow;

        public SearchViewHolder(View v) {
            super(v);
            mainRow = (LinearLayout) v.findViewById(R.id.ll_main);
            itemName = (TextView) v.findViewById(R.id.tv_item_name);
        }
    }

    private class AddOrderFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            constraint = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();
            // Holds the results of a filtering operation in values
            ArrayList<SearchModel> FilteredArrList = new ArrayList<>();
            /********
             *
             *  If constraint(CharSequence that is received) is null returns the mDisplayedList(ie the duplicate data of original list) values
             *  else does the Filtering and returns FilteredArrList(Filtered)
             *
             ********/
            if (constraint != null && constraint.length() > 0) {
                List<SearchModel> order = duplicateList;
                for (int i = 0; i < duplicateList.size(); i++) {

                    String name = order.get(i).getName() == null ? "" : order.get(i).getName();
                    String code = order.get(i).getCode() == null ? "" : order.get(i).getCode();
                    try {
                        if (name.toLowerCase().contains(constraint.toString()) || code.toLowerCase().contains(constraint.toString())) {
                            FilteredArrList.add(order.get(i));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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
            searchItemModel = (ArrayList<SearchModel>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}

