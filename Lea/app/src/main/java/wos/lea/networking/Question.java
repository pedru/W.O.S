package wos.lea.networking;

import java.util.List;

/**
 * Created by martin on 11.04.18.
 */

public class Question {
    private String question;
    private List<Answer> answers;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
