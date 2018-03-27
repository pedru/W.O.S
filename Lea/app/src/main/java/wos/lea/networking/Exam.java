package wos.lea.networking;

import java.util.Date;

/**
 * Created by faxxe on 3/27/18.
 */

public class Exam {

    private String lectureName;
    private String study;
    private Date created;


    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public String getStudy() {
        return study;
    }

    public void setStudy(String study) {
        this.study = study;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
