package info.vnk.billex.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Visak on 26/04/17.
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class PostProductModel {

    @JsonProperty("pdt_id")
    int pdt_id;

    String pdt_name;

    @JsonProperty("pdt_qty")
    String pdt_qty;

    @JsonProperty("pdt_mrp")
    String pdt_mrp;

    @JsonProperty("pdt_price")
    String pdt_price;

    @JsonProperty("gstper")
    String pdt_gst;

    @JsonProperty("free_qty")
    String pdt_free;

    @JsonProperty("pdt_discount")
    String pdt_discount;

    @JsonProperty("pdt_tax_price")
    private String pdt_tax = "";


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

    public String getPdt_discount() {
        if(pdt_discount != null && !pdt_discount.equals("null"))
            return pdt_discount;
        else
            return "0";
    }

    public void setPdt_discount(String pdt_discount) {
        this.pdt_discount = pdt_discount;
    }

    public String getPdt_mrp() {
        return pdt_mrp;
    }

    public void setPdt_mrp(String pdt_mrp) {
        this.pdt_mrp = pdt_mrp;
    }

    public String getPdt_price() {
        return pdt_price;
    }

    public void setPdt_price(String pdt_price) {
        this.pdt_price = pdt_price;
    }

    public String getPdt_gst() {
        return pdt_gst;
    }

    public void setPdt_gst(String pdt_gst) {
        this.pdt_gst = pdt_gst;
    }

    public String getPdt_free() {
        if(pdt_free != null && !pdt_free.equals("null"))
            return pdt_free;
        else
            return "0";
    }

    public void setPdt_free(String pdt_free) {
        this.pdt_free = pdt_free;
    }

    public String getPdt_tax() {
        return pdt_tax;
    }

    public void setPdt_tax(String pdt_tax) {
        this.pdt_tax = pdt_tax;
    }
}
