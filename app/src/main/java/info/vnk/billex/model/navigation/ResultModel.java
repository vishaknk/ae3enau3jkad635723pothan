package info.vnk.billex.model.navigation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import info.vnk.billex.model.login.LoginModel;

/**
 * Created by priyesh on 22/04/17.
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class ResultModel {
    @JsonProperty("result")
    private List<LoginModel> loginResult;

    private List<LoginModel> results;


    @JsonProperty("message")
    private String message;

    @JsonProperty("status")
    private int status;

    public List<LoginModel> getLoginResults() {
        return loginResult;
    }

    public void setLoginResult(List<LoginModel> result) {
        this.loginResult = result;
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
