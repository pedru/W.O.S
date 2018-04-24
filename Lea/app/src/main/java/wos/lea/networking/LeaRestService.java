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

    @GET("api/exams/{id}")
    Call<ExamDetail> getExamById(@Path("id") int id);

    @GET("api/studies/")
    Call<List<Study>> listAllStudies();

    @GET("api/studies/{id}")
    Call<StudyDetail> getStudyById(@Path("id") int id);

    @GET("api/lecture/{id}")
    Call<Lecture> getLectureById(@Path("id") int id);

    @GET("api/user/token")
    Call<TokenResponse> getAuthToken();

}
