package info.vnk.billex.model.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by priyesh on 06/05/17.
 */

@JsonIgnoreProperties( ignoreUnknown = true )
public class PaymentDetails {
    @JsonProperty("cust_id")
    private String cust_id;
    @JsonProperty("cust_name")
    private String cust_name;
    @JsonProperty("amount")
    private String amount;
    @JsonProperty("type")
    private String type;
    @JsonProperty("pay_date")
    private String pay_date;

    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPay_date() {
        return pay_date;
    }

    public void setPay_date(String pay_date) {
        this.pay_date = pay_date;
    }
}
