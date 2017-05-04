package info.vnk.billex.model.customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by priyesh on 05/05/17.
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class PostMainCustomerModel {
    public PostCustomerModel getModel() {
        return model;
    }

    public void setModel(PostCustomerModel model) {
        this.model = model;
    }

    @JsonProperty("customer")
    private PostCustomerModel model;
}
