package wos.lea.networking;

import java.util.List;

/**
 * Created by martin on 11.04.18.
 */

public class ExamDetail extends Exam {
    private int user;

    private List<Question> questions;


    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }



}
