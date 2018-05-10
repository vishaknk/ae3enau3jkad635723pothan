package info.vnk.billex.model.print;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties( ignoreUnknown = true )
public class BasicDetailsModel {

    @JsonProperty("bill_no")
    private String BillNumber;

    @JsonProperty("order_date")
    private String OrderDate;

    @JsonProperty("cust_name")
    private String name;

    @JsonProperty("cust_address")
    private String address;

    @JsonProperty("cust_phone")
    private String phone;

    @JsonProperty("type")
    private String type;

    @JsonProperty("cust_gst")
    private String gst;

    public String getBillNumber() {
        return BillNumber;
    }

    public void setBillNumber(String billNumber) {
        BillNumber = billNumber;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }
}