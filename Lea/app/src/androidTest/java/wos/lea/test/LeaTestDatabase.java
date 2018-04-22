package wos.lea.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wos.lea.networking.Answer;
import wos.lea.networking.Exam;
import wos.lea.networking.ExamDetail;
import wos.lea.networking.Lecture;
import wos.lea.networking.Question;
import wos.lea.networking.Study;
import wos.lea.networking.StudyDetail;

public class LeaTestDatabase {

    private List<Exam> allExams = new ArrayList<>();
    private List<Exam> savedExams = new ArrayList<>();
    private List<Study> studies = new ArrayList<>();
    private List<Lecture> lectures = new ArrayList<>();

    private Map<Study, Lecture> studyLectureMap = new HashMap<>();
    private Map<Lecture, Exam> lectureExamMap = new HashMap<>();

    public LeaTestDatabase() {

        initLectures();
        initStudies();
        initExams();




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
        exam.setId(1);
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
}
