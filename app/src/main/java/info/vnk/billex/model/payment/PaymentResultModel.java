package info.vnk.billex.model.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by priyesh on 06/05/17.
 */

@JsonIgnoreProperties( ignoreUnknown = true )
public class PaymentResultModel {
    @JsonProperty("result")
    private List<PaymentDetails> result;

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

    public List<PaymentDetails> getResult() {
        return result;
    }

    public void setResult(List<PaymentDetails> result) {
        this.result = result;
    }
}
