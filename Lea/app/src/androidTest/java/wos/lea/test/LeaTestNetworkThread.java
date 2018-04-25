package wos.lea.test;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaTestNetworkThread implements Runnable {
    private Callback callback;
    private final Call call;
    private final Response response;

    public LeaTestNetworkThread(Callback callback, Call call, Response response) {

        this.callback = callback;
        this.call = call;
        this.response = response;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {//        try {
            e.printStackTrace();
        }
        callback.onResponse(call, response);
    }

}
