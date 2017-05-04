package info.vnk.billex.model.product;

/**
 * Created by Visak on 24/04/17.
 */

public class ProductModel {

    public static final int DEFAULT_QUANTITY = 1;
    private int pdtId;

    private String pdtName;

    private String pdtCode, mrp, purPrice, price1, quantity = "1", landingCost;

    public int getPdtId() {
        return pdtId;
    }

    public void setPdtId(int pdtId) {
        this.pdtId = pdtId;
    }

    public String getPdtCode() {
        return pdtCode;
    }

    public void setPdtCode(String pdtCode) {
        this.pdtCode = pdtCode;
    }

    public String getPdtName() {
        return pdtName;
    }

    public void setPdtName(String pdtName) {
        this.pdtName = pdtName;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getPurPrice() {
        return purPrice;
    }

    public void setPurPrice(String purPrice) {
        this.purPrice = purPrice;
    }

    public String getPrice1() {
        return price1;
    }

    public void setPrice1(String price1) {
        this.price1 = price1;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getLandingCost() {
        return landingCost;
    }

    public void setLandingCost(String landingCost) {
        this.landingCost = landingCost;
    }
}
