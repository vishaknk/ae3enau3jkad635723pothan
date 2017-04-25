package info.vnk.billex.network;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


public class ApiClient {
 
    public static final String BASE_URL = "http://exioxtechnologies.com/vaisakhmarketing/api/v1/index.php/";
    public static final int TIME_OUT_CONNECT = 30;// SEC
    public static final int TIME_OUT_READ = 30; // SEC

    private static Retrofit retrofit = null;


    public static Retrofit getClient() {

        if (retrofit==null) {

//            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    //.client(client)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
        }

        /*HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override public void log(String message) {
                //Timber.tag("OkHttp").d(message);
            }
        });*/

        return retrofit;
    }
}