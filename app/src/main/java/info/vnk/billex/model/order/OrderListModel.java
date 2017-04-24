package info.vnk.billex.model.order;

/**
 * Created by Visak on 22/04/17.
 */

public class OrderListModel {
    private String productName;
    private int productQuantity = 1, productPrice = 0;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }
}
