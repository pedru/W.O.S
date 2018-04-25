package wos.lea.networking;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by faxxe on 3/27/18.
 */

public interface LeaRestService {

    @GET("api/exams/")
    Call<List<Exam>> listAllExams();

    @GET("api/user/token")
    Call<TokenResponse> getAuthToken();

    @GET("api/exams/{id}")
    Call<ExamDetail> getExamById(@Path("id") int id);


    @GET("api/user/detail")
    Call<UserDetail> getMyUser();


}
