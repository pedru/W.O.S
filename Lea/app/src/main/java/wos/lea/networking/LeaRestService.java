package wos.lea.networking;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @GET("api/user/detail")
    Call<UserDetail> getMyUser();

    @GET("api/lecture/{id}")
    Call<LectureDetail> getLectureById(@Path("id") int id);

    @GET("api/user/token")
    Call<TokenResponse> getAuthToken();

    @FormUrlEncoded
    @POST("api/exams/subscribe")
    Call<ExamSubscription> subscribeExam(@Field("exam_id") int exam_id);

    @FormUrlEncoded
    @POST("api/exams/unsubscribe")
    Call<ExamSubscription> unsubscribeExam(@Field("exam_id") int exam_id);

    @FormUrlEncoded
    @POST("api/exams/")
    Call<Lecture> createNewExam(@Field("lecture_id") int id,
                                @Field("date") String date);

    @FormUrlEncoded
    @POST("api/questions/")
    Call<Void> createNewQuestion(@Field("exam_id") int exam_id,
                                @Field("question") String question);

    @FormUrlEncoded
    @POST("api/answers/")
    Call<Void> createNewAnswer(@Field("question_id") int question_id,
                                 @Field("text") String answer);
}
