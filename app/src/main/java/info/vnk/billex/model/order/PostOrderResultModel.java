package info.vnk.billex.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by priyesh on 25/04/17.
 */

public class PostOrderResultModel {

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
}
