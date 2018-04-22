package wos.lea.test;

import java.util.List;

import retrofit2.Call;
import wos.lea.networking.Exam;
import wos.lea.networking.ExamDetail;
import wos.lea.networking.LeaRestService;
import wos.lea.networking.Study;
import wos.lea.networking.StudyDetail;
import wos.lea.networking.TokenResponse;

public class LeaTestRestService implements LeaRestService {

    private LeaTestDatabase leaTestDatabase;

    public LeaTestRestService() {
        leaTestDatabase = new LeaTestDatabase();
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
    public Call<TokenResponse> getAuthToken() {
        TokenResponse response = new TokenResponse();
        response.setToken("thisisaTestToken");
        response.setUser("user@lea.com");
        return new LeaTestCall<>(response);
    }

}
