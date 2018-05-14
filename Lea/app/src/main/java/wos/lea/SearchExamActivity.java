package wos.lea;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wos.lea.networking.Exam;
import wos.lea.networking.ExamDetail;
import wos.lea.networking.Lecture;
import wos.lea.networking.LectureDetail;
import wos.lea.networking.NetworkManager;
import wos.lea.networking.Study;
import wos.lea.networking.StudyDetail;


public class SearchExamActivity extends AppCompatActivity {
    private TextView examDate;
    private Spinner studyProgramSpinner;
    private Spinner courseSpinner;
    private ListView examList;
    private int d, m, y;
    private Calendar selectedDate;

    private ArrayList<Study> studies;
    private Map<String, Study> studiesMap;
    private StudyDetail studyDetail;
    private Map<String, Lecture> lectureMap;
    private LectureDetail lecture;
    private ArrayList<Exam> exams;
    private Snackbar snackbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_exam);
        findControlls();
        examDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                d = calendar.get(Calendar.DAY_OF_MONTH);
                m = calendar.get(Calendar.MONTH);
                y = calendar.get(Calendar.YEAR);
                DatePickerDialog pickerDialog = new DatePickerDialog(SearchExamActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        Calendar c = Calendar.getInstance();
                        c.set(year, month, date);
                        selectedDate = c;

                        SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd. MMM yyyy");
                        dateFormat.setTimeZone(c.getTimeZone());
                        String dateString = dateFormat.format(c.getTime());

                        examDate.setText(dateString);
                    }
                }, y, m, d);
                pickerDialog.show();
            }
        });
        SetDropdownElements();

        View view = findViewById(R.id.createNewExam);
        String message = "Do you want to create a new exam?";
        int duration = Snackbar.LENGTH_INDEFINITE;
        snackbar = Snackbar.make(view, message, duration);
    }

    private void findControlls() {
        examDate = findViewById(R.id.examDate);
        studyProgramSpinner = findViewById(R.id.studyProgramSpinner);
        courseSpinner = findViewById(R.id.courseSpinner);
        examList = findViewById(R.id.ExamView);
    }


    private void SetDropdownElements() {

        Call<List<Study>> call = NetworkManager.getInstance().getLeaRestService().listAllStudies();

        call.enqueue(new Callback<List<Study>>() {
            @Override
            public void onResponse(Call<List<Study>> call, Response<List<Study>> response) {
                studies = new ArrayList<>(response.body());
                List<String> categories = new ArrayList<>();
                studiesMap = new HashMap<>();
                for (Study study : studies) {
                    studiesMap.put(study.getName(), study);
                    categories.add(study.getName());
                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(SearchExamActivity.this,R.layout.spinner_item, categories);





                dataAdapter.setDropDownViewResource(R.layout.spinner_item_list);
                studyProgramSpinner.setAdapter(dataAdapter);
               // studyProgramSpinner.setSelection(dataAdapter.getCount());
                //courseSpinner.setAdapter(dataAdapter);
            }

            @Override
            public void onFailure(Call<List<Study>> call, Throwable t) {
                Log.d("EXAMS", "FAIL");
            }
        });

        studyProgramSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                final String study = studyProgramSpinner.getSelectedItem().toString();
                //Toast.makeText(getApplicationContext(), "Selected : " + msupplier, Toast.LENGTH_SHORT).show();
                Integer study_id = studiesMap.get(study).getId();

                Call<StudyDetail> call = NetworkManager.getInstance().getLeaRestService().getStudyById(study_id);

                call.enqueue(new Callback<StudyDetail>() {
                    @Override
                    public void onResponse(Call<StudyDetail> call, Response<StudyDetail> response) {
                        studyDetail = response.body();
                        if (studyDetail == null || studyDetail.getLectures() == null) {
                            return;
                        }
                        List<String> categories = new ArrayList<>();
                        lectureMap = new HashMap<>();
                        for (Lecture lecture : studyDetail.getLectures()) {
                            lectureMap.put(lecture.getName(), lecture);
                            categories.add(lecture.getName());
                        }

                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(SearchExamActivity.this, R.layout.spinner_item, categories);
                        dataAdapter.setDropDownViewResource(R.layout.spinner_item_list);
                        courseSpinner.setAdapter(dataAdapter);
                    }

                    @Override
                    public void onFailure(Call<StudyDetail> call, Throwable t) {
                        Log.d("EXAMS", "FAIL");
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                String lecture_name = courseSpinner.getSelectedItem().toString();
                //Toast.makeText(getApplicationContext(), "Selected : " + lecture_name, Toast.LENGTH_SHORT).show();
                final Integer lecture_id = lectureMap.get(lecture_name).getId();
                Call<LectureDetail> call = NetworkManager.getInstance().getLeaRestService().getLectureById(lecture_id);

                call.enqueue(new Callback<LectureDetail>() {
                    @Override
                    public void onResponse(Call<LectureDetail> call, Response<LectureDetail> response) {
                        Response<LectureDetail> res = response;
                        Object body = response.body();
                        lecture = response.body();
                        exams = new ArrayList<>();
                        exams = new ArrayList<>(lecture.getExams());
                        if (exams.isEmpty()){
                            findViewById(R.id.noExamsText).setVisibility(TextView.VISIBLE);
                            findViewById(R.id.ExamView).setVisibility(TextView.INVISIBLE);

                            showSnackbar(snackbar);
                        }
                        else {
                            findViewById(R.id.noExamsText).setVisibility(TextView.INVISIBLE);
                            findViewById(R.id.ExamView).setVisibility(TextView.VISIBLE);
                            //findViewById(R.id.createNewExam).setVisibility(TextView.INVISIBLE);
                            ExamListAdapter adapter = new ExamListAdapter(SearchExamActivity.this, exams);
                            examList.setAdapter(adapter);
                            hideSnackbar(snackbar);

                        }
                    }

                    @Override
                    public void onFailure(Call<LectureDetail> call, Throwable t) {
                        Log.d("EXAMS", "FAIL");
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    }
    public void showSnackbar(Snackbar snackbar)
    {

        // Set an action on it, and a handler
        snackbar.setAction("CREATE", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //snackbar.dismiss();
                Intent intent = new Intent(SearchExamActivity.this, CreateNewExam.class);
                startActivity(intent);
            }
        });
        snackbar.show();
    }

    public void hideSnackbar(Snackbar snackbar){
        snackbar.dismiss();
    }
}
