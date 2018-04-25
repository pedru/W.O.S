package wos.lea.networking;

import java.util.Date;
import java.util.List;

/**
 * Created by martin on 11.04.18.
 */

public class ExamDetail extends Exam {
    private int owner;

    private List<Question> questions;


    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
