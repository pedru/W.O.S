package wos.lea.test;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wos.lea.networking.Answer;
import wos.lea.networking.Exam;
import wos.lea.networking.ExamDetail;
import wos.lea.networking.ExamSubscription;
import wos.lea.networking.Lecture;
import wos.lea.networking.LectureDetail;
import wos.lea.networking.Question;
import wos.lea.networking.Study;
import wos.lea.networking.StudyDetail;
import wos.lea.networking.UserDetail;

public class LeaTestDatabase {

    private List<Exam> allExams = new ArrayList<>();
    private List<Exam> savedExams = new ArrayList<>();
    private List<Study> studies = new ArrayList<>();
    private List<Lecture> lectures = new ArrayList<>();
    private List<LectureDetail> lecture = new ArrayList<>();
    private UserDetail users;

    private Map<Study, Lecture> studyLectureMap = new HashMap<>();
    private Map<Lecture, Exam> lectureExamMap = new HashMap<>();

    public LeaTestDatabase() {

        initLectures();
        initStudies();
        initExams();
        initLectureDetail();
        initUserDetals();

    }

    private void initExams() {
        List<Question> questions = new ArrayList<>();
        Question dummyQuestion = new Question();
        dummyQuestion.setQuestion("This is the question!");
        dummyQuestion.setAnswers(Arrays.asList(new Answer(), new Answer()));
        questions.add(dummyQuestion);

        dummyQuestion = new Question();
        dummyQuestion.setQuestion("This is another question!");
        dummyQuestion.setAnswers(Arrays.asList(new Answer(), new Answer()));
        questions.add(dummyQuestion);

        ExamDetail exam = new ExamDetail();
        exam.setId(1);
        exam.setQuestions(questions);
        exam.setCreated(new Date());
        exam.setLecture(lectures.get(0));
        exam.setDate(new Date());
        allExams.add(exam);
        savedExams.add(exam);

        exam = new ExamDetail();
        exam.setId(2);
        exam.setQuestions(questions);
        exam.setCreated(new Date());
        exam.setLecture(lectures.get(1));
        exam.setDate(new Date());
        allExams.add(exam);
        savedExams.add(exam);

    }

    private void initLectures() {
        Lecture lecture = new Lecture();
        lecture.setId(0);
        lecture.setName("Mobile Applications");
        lectures.add(lecture);

        lecture = new Lecture();
        lecture.setId(1);
        lecture.setName("Information Theory");
        lectures.add(lecture);

        lecture = new Lecture();
        lecture.setId(2);
        lecture.setName("Operating Systems");
        lectures.add(lecture);

        lecture = new Lecture();
        lecture.setId(3);
        lecture.setName("Computer Networks");
        lectures.add(lecture);

        lecture = new Lecture();
        lecture.setId(3);
        lecture.setName("Geometric Algorithms");
        lectures.add(lecture);
    }

    private void initStudies() {
        StudyDetail study = new StudyDetail();
        study.setId(0);
        study.setName("Computer Science");
        study.setLectures(Arrays.asList(lectures.get(0), lectures.get(1)));
        studies.add(study);
        study = new StudyDetail();
        study.setLectures(Arrays.asList(lectures.get(1), lectures.get(2)));
        study.setId(1);
        study.setName("Electrical Engineering");
        studies.add(study);
        study = new StudyDetail();
        study.setLectures(Arrays.asList(lectures.get(2)));
        study.setId(2);
        study.setName("Biology");
        studies.add(study);
        study = new StudyDetail();
        study.setLectures(Arrays.asList(lectures.get(3), lectures.get(2)));
        study.setId(3);
        study.setName("Chemistry");
        studies.add(study);
        study = new StudyDetail();
        study.setLectures(Arrays.asList(lectures.get(0), lectures.get(1)));
        study.setId(4);
        study.setName("Biomedial engineering");
        studies.add(study);
    }

    public void initLectureDetail(){
        LectureDetail lecture1 = new LectureDetail();
        lecture1.setExams(allExams);
        lecture1.setId(0);
        lecture1.setName("Softwaretechnologie");

        lecture.add(lecture1);

        LectureDetail lecture2 = new LectureDetail();
        List <Exam> noExams = new ArrayList<Exam>();
        lecture2.setExams(noExams);
        lecture2.setId(1);
        lecture2.setName("Analysis 1");

        lecture.add(lecture2);
    }

    public void initUserDetals(){
        users = new UserDetail();
        users.setExams(allExams);
        users.setUsername("Test ABC");
        users.setIdM(1);
    }


    public List<Exam> getAllExams() {
        return (List<Exam>) allExams;
    }


    public List<Study> getAllStudies() {
        return studies;
    }

    public StudyDetail getStudyById(int id) {
        for (Study tmp: studies) {
            if (tmp.getId() == id) {
                return (StudyDetail) tmp;
            }
        }
        return null;
    }

    public ExamDetail getExamById(int id) {
        ExamDetail detail;

        for (Exam tmp: allExams) {
            if (tmp.getId() == id) {
                return (ExamDetail) tmp;
            }
        }
        return null;
    }

    public LectureDetail getLectureById(int id) {
        for (LectureDetail tmp: lecture) {
            if (tmp.getId() == id) {
                return (LectureDetail) tmp;
            }
        }
        return null;
    }

    public UserDetail getMyUsers() {
        return users;
    }

    public ExamSubscription rememberExam(int exam_id)
    {
        ExamSubscription examSubscription = new ExamSubscription();
        examSubscription.setExam_id(exam_id);
        for(Exam exam : getMyUsers().getExams())
        {
           if(exam.getId() == exam_id)
           {
               return examSubscription;
           }
        }
        getMyUsers().addExam(getExamById(exam_id));
        return examSubscription;
    }

    public ExamSubscription forgetExam(int exam_id)
    {
        ExamSubscription examSubscription = new ExamSubscription();
        examSubscription.setExam_id(exam_id);
        for(Exam exam : getMyUsers().getExams())
        {
            if(exam.getId() == exam_id)
            {
                getMyUsers().removeExam(exam);
                return examSubscription;
            }
        }

        return examSubscription;
    }
}
