package wos.lea.networking;

import java.util.List;

public class LectureDetail extends Lecture{

    private List<Exam> exams;

    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }
}
