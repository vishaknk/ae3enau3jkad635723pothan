package info.vnk.billex.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by priyesh on 25/04/17.
 */

public class OrderResultModel {


    @JsonProperty("result")
    private List<OrderListModel> result;

    @JsonProperty("message")
    private String message;

    @JsonProperty("status")
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<OrderListModel> getResult() {
        return result;
    }

    public void setResult(List<OrderListModel> result) {
        this.result = result;
    }
}
