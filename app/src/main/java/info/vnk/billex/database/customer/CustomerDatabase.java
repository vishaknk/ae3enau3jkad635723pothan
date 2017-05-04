package info.vnk.billex.database.customer;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;


/**
 * Created by Visak on 24/04/17.
 */
@Table(name = "tb_customer")
public class CustomerDatabase extends Model{

    @Column(name = "id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private int cust_id;

    @Column(name = "customer_name")
    private String custName;

    @Column(name = "customer_address")
    private String custAddress;

    @Column(name = "customer_mobile")
    private String custMobile;

    @Column(name = "customer_phone")
    private String custPhone;

    @Column(name = "customer_email")
    private String custEmail;

    @Column(name = "customer_location")
    private String custLocation;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "tin")
    private String tin;
    
    

    public void setId(int id) {
        this.cust_id = id;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustMobile() {
        return custMobile;
    }

    public void setCustMobile(String custMobile) {
        this.custMobile = custMobile;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public String getCustLocation() {
        return custLocation;
    }

    public void setCustLocation(String custLocation) {
        this.custLocation = custLocation;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }
}
