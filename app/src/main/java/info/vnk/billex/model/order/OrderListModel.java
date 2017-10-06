package info.vnk.billex.model.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by priyesh on 24/04/17.
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class OrderListModel {
    @JsonProperty("id")
    private String id;
    @JsonProperty("orderId")
    private String orderId;
    @JsonProperty("custId")
    private String customerId;
    @JsonProperty("custName")
    private String customerName;
    @JsonProperty("dateOfOrder")
    private String dateOfOrder;
    @JsonProperty("deliveryDate")
    private String deliveryDate;
    @JsonProperty("totalQuantity")
    private String totalQuantity;
    @JsonProperty("totalAmount")
    private String totalAmount;
    @JsonProperty("discount")
    private String discount;
    @JsonProperty("free")
    private String free;
    @JsonProperty("status")
    private String status;
    @JsonProperty("dt")
    private String createdDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(String dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }
}
