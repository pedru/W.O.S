package wos.lea.networking;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by u11s65 on 27.03.2018.
 */

public class NetworkManager {
    private static final NetworkManager ourInstance = new NetworkManager();


    public static NetworkManager getInstance() {
        return ourInstance;
    }

    public LeaRestService leaRestService;

    private String authtoken = "";


    private String API_BASE_URL = "http://netzweber.at:8080";

    private NetworkManager() {
        try {
            Class<LeaRestService> testLeaRestService = (Class<LeaRestService>) Class.forName("wos.lea.test.LeaTestRestService");
            leaRestService = testLeaRestService.newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            leaRestService = createLeaRestService();
        }
    }

    private LeaRestService createLeaRestService() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Authorization", "Token " + authtoken)
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(LeaRestService.class);

    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public LeaRestService getLeaRestService() {
        return leaRestService;
    }

    public void setLeaRestService(LeaRestService leaRestService) {
        this.leaRestService = leaRestService;
    }
}
