package info.vnk.billex.activity.order;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import info.vnk.billex.R;
import info.vnk.billex.adapter.order.AddOrderListAdapter;
import info.vnk.billex.adapter.order.listener.OrderListener;
import info.vnk.billex.base.BaseActivity;
import info.vnk.billex.custom.fullScreenSearch.FullScreenSearch;
import info.vnk.billex.custom.fullScreenSearch.listener.CustomListener;
import info.vnk.billex.custom.fullScreenSearch.model.SearchModel;
import info.vnk.billex.model.customer.CustomerModel;
import info.vnk.billex.model.customer.CustomerResultModel;
import info.vnk.billex.model.order.PostMainOrderModel;
import info.vnk.billex.model.order.PostOrderModel;
import info.vnk.billex.model.order.PostOrderResultModel;
import info.vnk.billex.model.product.PostProductModel;
import info.vnk.billex.model.product.ProductModel;
import info.vnk.billex.model.product.ProductResultModel;
import info.vnk.billex.network.ApiClient;
import info.vnk.billex.network.ApiInterface;
import info.vnk.billex.utilities.Constants;
import info.vnk.billex.utilities.General;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddOrderActivity extends BaseActivity {

    private static final int KEY_CUSTOMER = 1;
    private static final int KEY_PRODUCT = 2;
    private Context context;
    private FloatingActionButton btnAddFab;
    private FullScreenSearch fullScreenSearch;
    private List<PostProductModel> postProductModel = new ArrayList<>();
    private AddOrderListAdapter adapter;
    private SearchModel seletedCustomer;
    private List<ProductModel> mProductList;
    private List<CustomerModel> mCustomerList;
    private Toolbar toolbar;
    private EditText dateOfDelivery, dateOfOrder, customerText;
    private static final String TAG = "AddOrderActivity";
    private MaterialDialog mMaterialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        context = this;
        setPreference(context);
        toolbar = setToolbar();
        initToolbar();
        init();
    }

    public void initToolbar() {
        TextView placeOrder = (TextView) findViewById(R.id.tv_right);
        placeOrder.setVisibility(View.VISIBLE);
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(customerText.getText().length() <= 0){
                    return;
                } else if (dateOfOrder.getText().length() <= 0) {
                    return;
                } else if (dateOfDelivery.getText().length() <= 0){
                    return;
                } else if(postProductModel.size() <= 0){
                    return;
                } else {
                    setOrder();
                }
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void init() {

        View viewOrder = findViewById(R.id.content_order_layout);
        customerText = (EditText) viewOrder.findViewById(R.id.et_select_customer);
        dateOfOrder = (EditText) viewOrder.findViewById(R.id.et_date_of_order);
        dateOfDelivery = (EditText) viewOrder.findViewById(R.id.et_date_of_delivery);
        customerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFullScreenSearchVisible(true);
                getCustomerList();
            }
        });
        dateOfOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                General.setEditText(dateOfOrder);
                General.setCalendar(context);
            }
        });
        dateOfDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                General.setEditText(dateOfDelivery);
                General.setCalendar(context);
            }
        });

        btnAddFab = (FloatingActionButton) findViewById(R.id.fab_add);

        fullScreenSearch = (FullScreenSearch) viewOrder.findViewById(R.id.custom_search);
        fullScreenSearch.setListener(new CustomListener() {
            @Override
            public void onItemSelectedSearch(int key, SearchModel productModel) {
                setFullScreenSearchVisible(false);

                if(key == KEY_PRODUCT) {
                    btnAddFab.setVisibility(View.VISIBLE);
                    for (ProductModel data : mProductList) {
                        if(data.getPdtId() == productModel.getId()) {
                            PostProductModel model = new PostProductModel();
                            model.setPdt_id(data.getPdtId());
                            model.setPdt_name(data.getPdtName());
                            model.setPdt_qty("" + ProductModel.DEFAULT_QUANTITY);
                            model.setPdt_total_amnt(data.getMrp());
                            model.setPdt_discount("0");
                            model.setUpdated_date("");
                            postProductModel.add(model);
                            Collections.reverse(postProductModel);
                        }

                    }
                    adapter.notifyDataSetChanged();
                } else if(key == KEY_CUSTOMER) {
                    setSeletedCustomer(productModel);
                    customerText.setText("" + productModel.getName());
                }
            }
        });
        RecyclerView recyclerOrder = (RecyclerView) viewOrder.findViewById(R.id.rv_order);
        recyclerOrder.setLayoutManager(new LinearLayoutManager(context));
        //Adapter
        adapter = new AddOrderListAdapter(getPostProductModel(), context);
        adapter.setListener(new OrderListener() {
            @Override
            public void plusClick(int position) {
                try {
                    int dataValue = Integer.parseInt(postProductModel.get(position).getPdt_qty());
                    dataValue++;
                    postProductModel.get(position).setPdt_qty("" + dataValue);
                    adapter.notifyDataSetChanged();
                } catch (NumberFormatException e) {
                    int dataValue = 1;
                    dataValue++;
                    postProductModel.get(position).setPdt_qty("" + dataValue);
                    adapter.notifyDataSetChanged();
                } catch (Exception e ){
                    e.printStackTrace();
                }
            }

            @Override
            public void minusClick(int position) {
                try {
                    int dataValue = Integer.parseInt(postProductModel.get(position).getPdt_qty());
                    if (dataValue > 1) {
                        dataValue--;
                        postProductModel.get(position).setPdt_qty("" + dataValue);
                    }
                    adapter.notifyDataSetChanged();
                } catch (NumberFormatException e) {
                    int dataValue = 1;
                    if (dataValue > 1) {
                        dataValue--;
                        postProductModel.get(position).setPdt_qty("" + dataValue);
                    }
                    adapter.notifyDataSetChanged();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void deleteClick(int position) {
                setDeleteConfirm(context, position);
            }

            @Override
            public Long quantityClick(Long quantity){
                boolean wrapInScrollView = true;
                long data = 0;
                /*new MaterialDialog(context)
                        .setTitle(R.string.order_title)
                        .setContentView(R.layout.custom_view)
                        .setPositiveButton(R.string.positive)
                        .show();*/
                return data;
            }
        });
        recyclerOrder.setAdapter(adapter);


        btnAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                setFullScreenSearchVisible(true);
                btnAddFab.setVisibility(View.GONE);
                getProductList();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed Called");
        if(fullScreenSearch.getVisibility() == View.VISIBLE){
            fullScreenSearch.setVisibility(View.GONE);
        } else {
            setDialogOkCancel(context);
        }
    }

    public void setDeleteConfirm(Context context, final int index){
        mMaterialDialog = new MaterialDialog(context)
                .setTitle(R.string.order_title)
                .setMessage(R.string.order_delete)
                .setPositiveButton(R.string.yes, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        postProductModel.remove(index);
                        adapter.notifyDataSetChanged();
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

    public void setDialogOkCancel(Context context){
         mMaterialDialog = new MaterialDialog(context)
                .setTitle(R.string.order_title)
                .setMessage(R.string.order_message)
                .setPositiveButton(R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        mMaterialDialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                    }
                });
        mMaterialDialog.show();
    }

    // call api to fetch data
    private void getProductList() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ProductResultModel> call = apiService.getProduct();
        call.enqueue(new Callback<ProductResultModel>() {
            @Override
            public void onResponse(Call<ProductResultModel> call, Response<ProductResultModel> response) {
                mProductList = response.body().getResult();
                fullScreenSearch.setSearchAdapter(context, KEY_PRODUCT, getProduct());
            }

            @Override
            public void onFailure(Call<ProductResultModel> call, Throwable t) {
                Toast.makeText(context, "error" + t.getLocalizedMessage(), Toast.LENGTH_LONG);
            }
        });

    }

    // call api to fetch data
    private void getCustomerList() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CustomerResultModel> call = apiService.getCustomer();
        call.enqueue(new Callback<CustomerResultModel>() {
            @Override
            public void onResponse(Call<CustomerResultModel> call, Response<CustomerResultModel> response) {
                mCustomerList = response.body().getResult();
                fullScreenSearch.setSearchAdapter(context, KEY_CUSTOMER, getCustomer());
            }

            @Override
            public void onFailure(Call<CustomerResultModel> call, Throwable t) {
                Toast.makeText(context, "error" + t.getLocalizedMessage(), Toast.LENGTH_LONG);
            }
        });

    }

    public List<SearchModel> getProduct(){
        List<SearchModel> list = new ArrayList<>();
        for (ProductModel data : mProductList) {
            SearchModel searchModel = new SearchModel();
            searchModel.setId(data.getPdtId());
            searchModel.setName(data.getPdtName());
            searchModel.setCode(data.getPdtCode());
            list.add(searchModel);
        }
        return list;
    }

    public List<SearchModel> getCustomer(){
        List<SearchModel> list = new ArrayList<>();
        for (CustomerModel data : mCustomerList) {
            SearchModel searchModel = new SearchModel();
            searchModel.setId(data.getId());
            searchModel.setName(data.getCustName());
            searchModel.setCode(data.getCustMobile());
            list.add(searchModel);
        }
        return list;
    }

    public PostMainOrderModel postOrder(){
        PostOrderModel orderModel = new PostOrderModel();
        orderModel.setCustomerId(getSeletedCustomer().getId());
        orderModel.setDateOfDelivery(General.DateFormatter(dateOfDelivery.getText().toString()));
        orderModel.setDateOfOrder(General.DateFormatter(dateOfOrder.getText().toString()));
        orderModel.setDiscount("0");
        orderModel.setStaffId(preferencesManager.getString(Constants.mUserId));
        orderModel.setStatus("1");
        orderModel.setTotalAmount("");
        orderModel.setTotalQuantity("" + postProductModel.size());
        orderModel.setUpdateDate("");
        orderModel.setListProduct(postProductModel);
        PostMainOrderModel orderMain = new PostMainOrderModel();
        orderMain.setModel(orderModel);
        return orderMain;
    }

    // call api to post data
    private void setOrder() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<PostOrderResultModel> call = apiService.postOrder(postOrder());
        call.enqueue(new Callback<PostOrderResultModel>() {
            @Override
            public void onResponse(Call<PostOrderResultModel> call, Response<PostOrderResultModel> response) {
                //response.body().getMessage();
                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Call<PostOrderResultModel> call, Throwable t) {
                Toast.makeText(context, "error" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void setFullScreenSearchVisible(boolean value){
        if (value){
            fullScreenSearch.setVisibility(View.VISIBLE);
            fullScreenSearch.setVisibleRecycler(View.GONE);
            btnAddFab.setVisibility(View.GONE);
        } else {
            fullScreenSearch.setVisibility(View.GONE);
            btnAddFab.setVisibility(View.VISIBLE);
        }
    }

    public List<PostProductModel> getPostProductModel() {
        return postProductModel;
    }

    public void setPostProductModel(List<PostProductModel> postProductModel) {
        this.postProductModel = postProductModel;
    }

    public SearchModel getSeletedCustomer() {
        return seletedCustomer;
    }

    public void setSeletedCustomer(SearchModel seletedCustomer) {
        this.seletedCustomer = seletedCustomer;
    }
}

