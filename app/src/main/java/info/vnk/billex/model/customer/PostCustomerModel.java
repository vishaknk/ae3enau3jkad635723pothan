package info.vnk.billex.model.customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by priyesh on 05/05/17.
 */

@JsonIgnoreProperties( ignoreUnknown = true )
public class PostCustomerModel {
    private String accCode;
    private String head;
    private String address;
    private String agcode;
    private String phone;
    private String tin;
    private String creditDays;

    public String getAccCode() {
        return accCode;
    }

    public void setAccCode(String accCode) {
        this.accCode = accCode;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAgcode() {
        return agcode;
    }

    public void setAgcode(String agcode) {
        this.agcode = agcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getCreditDays() {
        return creditDays;
    }

    public void setCreditDays(String creditDays) {
        this.creditDays = creditDays;
    }
}
