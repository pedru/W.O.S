package wos.lea.networking;

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


    private String API_BASE_URL = "http://netzweber.at:8080";

    private NetworkManager() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        leaRestService = retrofit.create(LeaRestService.class);

    }



}
