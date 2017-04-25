package info.vnk.billex.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import info.vnk.billex.model.order.OrderListModel;
import info.vnk.billex.model.product.ProductModel;

/**
 * Created by Visak on 24/04/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class MainModel {

    public int status;

    public String message;

    public Options result;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Options{

        public List<ProductModel> product_model;

        public List<OrderListModel> order_model;

    }
}
