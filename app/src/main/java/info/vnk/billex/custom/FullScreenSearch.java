package info.vnk.billex.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.List;

import info.vnk.billex.R;
import info.vnk.billex.model.product.ProductModel;

/**
 * Created by Visak on 24/04/17.
 */

public class FullScreenSearch extends LinearLayout {

    private List<ProductModel> productModel;
    private RecyclerView recyclerOrder;
    private CustomListener listener;

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
        EditText searchText = (EditText) this.findViewById(R.id.et_search);
        ProgressBar progressBar = (ProgressBar) this.findViewById(R.id.progressBar);
        recyclerOrder = (RecyclerView) this.findViewById(R.id.rv_search_result);
        recyclerOrder.setLayoutManager(new LinearLayoutManager(context));
    }

    public void setProductAdapter(Context context, List<ProductModel> productModel){
        setModel(productModel);
        SearchAdapter adapter = new SearchAdapter(getModel(), context, listener);
        recyclerOrder.setAdapter(adapter);
    }

    public void setModel(List<ProductModel> model){
        productModel = model;
    }

    public List<ProductModel> getModel(){
        return productModel;
    }

    public CustomListener getListener() {
        return listener;
    }

    public void setListener(CustomListener listener) {
        this.listener = listener;
    }
}
