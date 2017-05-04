package info.vnk.billex.custom.fullScreenSearch;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.List;

import info.vnk.billex.R;
import info.vnk.billex.custom.fullScreenSearch.adapter.SearchAdapter;
import info.vnk.billex.custom.fullScreenSearch.listener.CustomListener;
import info.vnk.billex.custom.fullScreenSearch.model.SearchModel;

/**
 * Created by Visak on 24/04/17.
 */

public class FullScreenSearch extends LinearLayout {

    private List<SearchModel> searchModel;
    private RecyclerView recyclerOrder;
    private CustomListener listener;
    private SearchAdapter adapter;
    private ProgressBar progressBar;

    public FullScreenSearch(Context context) {
        super(context);
        initViews(context);
    }

    public FullScreenSearch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public FullScreenSearch(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.search_pop_up, this);
        final EditText searchText = (EditText) this.findViewById(R.id.et_search);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() != 0) {
                    if (searchModel != null)
                        adapter.getFilter().filter(s.toString());
                }
                if (searchModel != null)
                    adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        progressBar = (ProgressBar) this.findViewById(R.id.progressBar);
        recyclerOrder = (RecyclerView) this.findViewById(R.id.rv_search_result);
        recyclerOrder.setLayoutManager(new LinearLayoutManager(context));
    }

    public void setSearchAdapter(Context context, int key, List<SearchModel> searchModelData){
        searchModel = searchModelData;
        adapter = new SearchAdapter(searchModel, context, key, listener);
        recyclerOrder.setAdapter(adapter);
        progressBar.setVisibility(GONE);
        setVisibleRecycler(VISIBLE);
    }

    public void setVisibleRecycler(int view){
        recyclerOrder.setVisibility(view);
        if (view == VISIBLE)
            progressBar.setVisibility(GONE);
        else
            progressBar.setVisibility(VISIBLE);
    }

    public CustomListener getListener() {
        return listener;
    }

    public void setListener(CustomListener listener) {
        this.listener = listener;
    }
}
