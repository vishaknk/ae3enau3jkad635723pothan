package info.vnk.billex.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import info.vnk.billex.R;
import info.vnk.billex.base.BaseActivity;
import info.vnk.billex.model.login.LoginModel;
import info.vnk.billex.model.navigation.ResultModel;
import info.vnk.billex.network.ApiClient;
import info.vnk.billex.network.ApiInterface;
import info.vnk.billex.utilities.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by priyesh on 22/04/17.
 */

public class LoginActivity extends BaseActivity {


    private EditText mUsername, mPassword;
    private Button btnLogin;
    private Context mContext;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_login);
        init();
        setPreference(mContext);
    }

    public void init() {
        mContext = LoginActivity.this;
        mUsername = (EditText) findViewById(R.id.et_username);
        mPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        progressBar = (ProgressBar) findViewById(R.id.pb_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getUsername() != null && getPassword() != null) {
                    setProgressBarVisible();
                    getLogin();
                }
            }
        });
    }

    private void getLogin() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResultModel> call = apiService.getUser(getUsername(), getPassword());
        call.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {

                //Log.v("Response", "" + response.raw());
                List<LoginModel> loginDetails = response.body().getLoginResults();
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                //checking the size of the results
                if (loginDetails.size() != 0) {
                    //checking the status ; if the status is 0 then the account is inactive
                    //if the status is 1 then the account is active
                    if (loginDetails.get(0).getStatus().equals("1")) {
                        //Role == 1 Admin otherwise user
                        if (loginDetails.get(0).getRole().equals("1")) {
                            preferencesManager.putBoolean(Constants.mIsAdmin, true);
                        } else {
                            preferencesManager.putBoolean(Constants.mIsAdmin, false);
                        }
                        preferencesManager.putString(Constants.mUserName, getUsername());
                        preferencesManager.putString(Constants.EMAIL_ID, loginDetails.get(0).getEmail());
                        preferencesManager.putString(Constants.mUserId, loginDetails.get(0).getUserId());
                        preferencesManager.putString(Constants.MOBILE, loginDetails.get(0).getMobile());
                        preferencesManager.putString(Constants.mSession, loginDetails.get(0).getSession());
                        startActivity(intent);
                        //Setting the account to Logged in state
                        preferencesManager.putBoolean(Constants.IS_LOGGED_IN, true);
                        finish();
                    } else {
                        Toast.makeText(mContext, getResources().getString(R.string.error_inactive_user), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {
                Log.e("Error", t.toString());
                setProgressBarHide();
                Toast.makeText(mContext,getResources().getString(R.string.try_again), Toast.LENGTH_LONG).show();
            }
        });
    }

    public String getUsername() {
        return mUsername.getText().toString().trim().equals("") ? null : mUsername.getText().toString();
    }
    public String getPassword() {
        return mPassword.getText().toString().trim().equals("") ? null : mPassword.getText().toString();
    }
    public void setProgressBarVisible() {
        progressBar.setVisibility(View.VISIBLE);
        btnLogin.setEnabled(false);
    }

    public void setProgressBarHide() {
        progressBar.setVisibility(View.INVISIBLE);
        btnLogin.setEnabled(true);
    }
}
