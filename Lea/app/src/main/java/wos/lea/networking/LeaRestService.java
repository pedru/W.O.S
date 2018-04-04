package wos.lea.networking;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by faxxe on 3/27/18.
 */

public interface LeaRestService {

    @GET("exams/all")
    Call<List<Exam>> listAllExams();

}