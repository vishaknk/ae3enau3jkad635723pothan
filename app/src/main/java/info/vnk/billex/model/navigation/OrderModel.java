package info.vnk.billex.model.navigation;

/**
 * Created by priyesh on 22/04/17.
 */

public class OrderModel {
    private String orderName;
    private String orderId;
    private String customername;

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }
}
