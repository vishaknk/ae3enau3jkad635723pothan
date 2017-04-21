package info.vnk.billex.model.navigation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import info.vnk.billex.model.navigation.Login.LoginModel;

/**
 * Created by priyesh on 22/04/17.
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class ResultModel {


    @JsonProperty("result")
    private List<LoginModel> result;

    @JsonProperty("message")
    private String message;

    @JsonProperty("status")
    private int status;

    public List<LoginModel> getResult() {
        return result;
    }

    public void setResult(List<LoginModel> result) {
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
