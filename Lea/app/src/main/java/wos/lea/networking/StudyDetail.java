package wos.lea.networking;

import java.util.List;

public class StudyDetail extends Study {

    private List<Lecture> lectures;


    public List<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }
}
