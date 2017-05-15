package info.vnk.billex.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Visak on 26/04/17.
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class GetProductModel {

    @JsonProperty("orderId")
    String orderId;
    @JsonProperty("pdtId")
    int pdt_id;

    @JsonProperty("pdtName")
    String pdt_name;

    @JsonProperty("pdtQty")
    String pdt_qty;

    @JsonProperty("pdtTotalAmnt")
    String pdt_total_amnt;

    @JsonProperty("pdtDiscount")
    String pdt_discount;

    @JsonProperty("dt")
    String updated_date = "";

    public int getPdt_id() {
        return pdt_id;
    }

    public void setPdt_id(int pdt_id) {
        this.pdt_id = pdt_id;
    }

    public String getPdt_name() {
        return pdt_name;
    }

    @JsonIgnore
    public void setPdt_name(String pdt_name) {
        this.pdt_name = pdt_name;
    }

    public String getPdt_qty() {
        return pdt_qty;
    }

    public void setPdt_qty(String pdt_qty) {
        this.pdt_qty = pdt_qty;
    }

    public String getPdt_total_amnt() {
        return pdt_total_amnt;
    }

    public void setPdt_total_amnt(String pdt_total_amnt) {
        this.pdt_total_amnt = pdt_total_amnt;
    }

    public String getPdt_discount() {
        return pdt_discount;
    }

    public void setPdt_discount(String pdt_discount) {
        this.pdt_discount = pdt_discount;
    }

    public String getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(String updated_date) {
        this.updated_date = updated_date;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
