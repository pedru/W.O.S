package wos.lea.networking;

import java.util.List;

/**
 * Created by u11s65 on 25.04.2018.
 */

public class UserDetail {

    int idM;
    String username;
    List<Exam> exams;

    public int getIdM() {
        return idM;
    }

    public void setIdM(int idM) {
        this.idM = idM;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }
}
