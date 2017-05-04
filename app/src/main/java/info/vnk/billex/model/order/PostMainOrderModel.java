package info.vnk.billex.model.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Visak-Mac on 01/05/17.
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class PostMainOrderModel {
    @JsonProperty("order")
    PostOrderModel model;

    public PostOrderModel getModel() {
        return model;
    }

    public void setModel(PostOrderModel model) {
        this.model = model;
    }
}
