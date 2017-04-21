package info.vnk.billex.network;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


public class ApiClient {
 
    public static final String BASE_URL = "http://exioxtechnologies.com/vaisakhmarketing/api/v1/index.php/";
    private static Retrofit retrofit = null;
 
 
    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}