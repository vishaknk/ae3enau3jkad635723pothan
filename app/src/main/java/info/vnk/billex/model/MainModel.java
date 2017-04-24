package info.vnk.billex.model;

import info.vnk.billex.model.product.ProductModel;

import java.util.List;

/**
 * Created by Visak on 24/04/17.
 */

//@JsonIgnoreProperties(ignoreUnknown = true)
public class MainModel {

    public int status;

    public String message;

    public Options result;

    //@JsonIgnoreProperties(ignoreUnknown = true)
    public static class Options{

        public String contents;

        public List<ProductModel> product_model;
    }
}
