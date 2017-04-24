package info.vnk.billex.network;

import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


public class ApiClient {
 
    public static final String BASE_URL = "http://exioxtechnologies.com/vaisakhmarketing/api/v1/index.php/";
    private static Retrofit retrofit = null;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
 
    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    //create a service
    public static <S> S createService(Class<S> serviceClass, Context ctx) {
        if (retrofit == null) {
            getClient();
            {
                //The logging interceptor will be added to the http client
                httpClient.addInterceptor(getInter());
            }
        }

        httpClient.retryOnConnectionFailure(false);
        return retrofit.create(serviceClass);
    }

    public static HttpLoggingInterceptor getInter() {
        {
            ////Here a logging interceptor is created
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set our desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            return logging;
        }


    }
}