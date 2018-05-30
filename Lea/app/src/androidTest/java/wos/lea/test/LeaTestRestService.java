package wos.lea.test;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import wos.lea.networking.Exam;
import wos.lea.networking.ExamDetail;
import wos.lea.networking.ExamSubscription;
import wos.lea.networking.LeaRestService;
import wos.lea.networking.LectureDetail;
import wos.lea.networking.Study;
import wos.lea.networking.StudyDetail;
import wos.lea.networking.TokenResponse;
import wos.lea.networking.UserDetail;

public class LeaTestRestService implements LeaRestService {

    private LeaTestDatabase leaTestDatabase;

    public LeaTestRestService() {
        leaTestDatabase = new LeaTestDatabase();
    }

    public LeaTestDatabase getLeaTestDatabase() {
        return leaTestDatabase;
    }

    @Override
    public Call<List<Exam>> listAllExams() {
        List<Exam> allExams = leaTestDatabase.getAllExams();
        return new LeaTestCall<>(allExams);
    }

    @Override
    public Call<ExamDetail> getExamById(int id) {
        ExamDetail examDetail = leaTestDatabase.getExamById(id);
        return new LeaTestCall<>(examDetail);
    }

    @Override
    public Call<List<Study>> listAllStudies() {
        List<Study> studies = leaTestDatabase.getAllStudies();
        return new LeaTestCall<>(studies);
    }

    @Override
    public Call<StudyDetail> getStudyById(int id) {
        StudyDetail detail = leaTestDatabase.getStudyById(id);
        return new LeaTestCall<>(detail);
    }

    @Override
    public Call<UserDetail> getMyUser() {
        UserDetail detail = leaTestDatabase.getMyUsers();
        return new LeaTestCall<>(detail);
    }

    @Override
    public Call<LectureDetail> getLectureById(int id) {
        LectureDetail detail = leaTestDatabase.getLectureById(id);
        return new LeaTestCall<>(detail);
    }

    @Override
    public Call<TokenResponse> getAuthToken() {
        TokenResponse response = new TokenResponse();
        response.setToken("thisisaTestToken");
        response.setUser("user@lea.com");
        return new LeaTestCall<>(response);
    }

    @Override
    public Call<ExamSubscription> subscribeExam(@Field("exam_id") int exam_id) {
        ExamSubscription response = leaTestDatabase.rememberExam(exam_id);
        return new LeaTestCall<>(response);
    }

    @Override
    public Call<ExamSubscription> unsubscribeExam(@Field("exam_id") int exam_id) {
        ExamSubscription response = leaTestDatabase.forgetExam(exam_id);
        return new LeaTestCall<>(response);
    }

}
