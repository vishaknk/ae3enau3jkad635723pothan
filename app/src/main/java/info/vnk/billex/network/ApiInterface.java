package info.vnk.billex.network;

import info.vnk.billex.model.navigation.ResultModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ApiInterface {
    @FormUrlEncoded
    @POST("userLogin")
    Call<ResultModel> getUser(@Field("email_phone") String username, @Field("password") String password);
}