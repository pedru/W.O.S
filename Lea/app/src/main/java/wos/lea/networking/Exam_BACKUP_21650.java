package wos.lea.networking;

import java.util.Date;

/**
 * Created by faxxe on 3/27/18.
 */

public class Exam {

<<<<<<< HEAD
    private int id;
    private String lectureName;
    private String study;
=======
    private Lecture lecture;
    private Study study;
>>>>>>> origin/develop
    private Date created;
    private Date date;

<<<<<<< HEAD
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getExamDate() {
        return examDate;
=======
    public Date getDate() {
        return date;
>>>>>>> origin/develop
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Lecture getLecture() {
        return lecture;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

    public Study getStudy() {
        return study;
    }

    public void setStudy(Study study) {
        this.study = study;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
