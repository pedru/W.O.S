package wos.lea.networking;

import java.util.List;

/**
 * Created by martin on 11.04.18.
 */

public class ExamDetail {
    private String lecture;
    private List<Question> questions;

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getLecture() {
        return lecture;
    }

    public void setLecture(String lecture) {
        this.lecture = lecture;
    }
}
