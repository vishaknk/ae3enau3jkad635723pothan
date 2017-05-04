package info.vnk.billex.model.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import info.vnk.billex.model.product.PostProductModel;

/**
 * Created by Visak-Mac on 01/05/17.
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class PostOrderModel {
    @JsonProperty("cust_id")
    int customerId;

    @JsonProperty("date_of_order")
    String dateOfOrder;

    @JsonProperty("delivery_date")
    String dateOfDelivery;

    @JsonProperty("total_quantity")
    String totalQuantity;

    @JsonProperty("total_amount")
    String totalAmount;

    @JsonProperty("discount")
    String discount;

    @JsonProperty("staff_id")
    String staffId;

    @JsonProperty("status")
    String status;

    @JsonProperty("updated_date")
    String updateDate;

    @JsonProperty("product_details")
    List<PostProductModel> listProduct;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(String dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public String getDateOfDelivery() {
        return dateOfDelivery;
    }

    public void setDateOfDelivery(String dateOfDelivery) {
        this.dateOfDelivery = dateOfDelivery;
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

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public List<PostProductModel> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<PostProductModel> listProduct) {
        this.listProduct = listProduct;
    }
}
