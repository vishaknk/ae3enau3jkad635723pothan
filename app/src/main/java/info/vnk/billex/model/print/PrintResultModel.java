package info.vnk.billex.model.print;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import info.vnk.billex.model.customer.CustomerModel;

@JsonIgnoreProperties( ignoreUnknown = true )
public class PrintResultModel {

    @JsonProperty("basic_details")
    private BasicDetailsModel basicDetails;

    @JsonProperty("item_details")
    private List<ItemDetailsModel> itemDetails;

    @JsonProperty("final_details")
    private FinalDetailsModel finalDetails;

    public BasicDetailsModel getBasicDetails() {
        return basicDetails;
    }

    public void setBasicDetails(BasicDetailsModel basicDetails) {
        this.basicDetails = basicDetails;
    }

    public List<ItemDetailsModel> getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(List<ItemDetailsModel> itemDetails) {
        this.itemDetails = itemDetails;
    }

    public FinalDetailsModel getFinalDetails() {
        return finalDetails;
    }

    public void setFinalDetails(FinalDetailsModel finalDetails) {
        this.finalDetails = finalDetails;
    }
}
