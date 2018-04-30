package wos.lea.test;

import java.io.IOException;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LeaTestCall<T> implements Call {

    private T result;

    public LeaTestCall(T result) {
        this.result = result;
    }

    @Override
    public Response execute() throws IOException {
        return null;
    }

    @Override
    public void enqueue(Callback callback) {
        Response<T> response = Response.success(result);
        new Thread(new LeaTestNetworkThread(callback, this, response)).run();
    }

    @Override
    public boolean isExecuted() {
        return false;
    }

    @Override
    public void cancel() {

    }

    @Override
    public boolean isCanceled() {
        return false;
    }

    @Override
    public Call clone() {
        return null;
    }

    @Override
    public Request request() {
        return null;
    }
}
