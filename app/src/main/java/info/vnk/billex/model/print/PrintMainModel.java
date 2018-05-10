package info.vnk.billex.model.print;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import info.vnk.billex.model.customer.CustomerModel;

@JsonIgnoreProperties( ignoreUnknown = true )
public class PrintMainModel {

    @JsonProperty("result")
    private PrintResultModel result;

    @JsonProperty("message")
    private String message;

    @JsonProperty("status")
    private int status;

    public PrintResultModel getResult() {
        return result;
    }

    public void setResult(PrintResultModel result) {
        this.result = result;
    }

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