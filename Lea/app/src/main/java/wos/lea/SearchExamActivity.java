package wos.lea;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
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
import wos.lea.networking.Lecture;
import wos.lea.networking.NetworkManager;
import wos.lea.networking.Study;
import wos.lea.networking.StudyDetail;

public class SearchExamActivity extends AppCompatActivity {
    private TextView examDate;
    private Spinner studyProgramSpinner;
    private Spinner courseSpinner;
    private int d, m, y;
    private Calendar selectedDate;

    private ArrayList<Study> studies;
    private Map<String, Study> studiesMap;
    private StudyDetail studyDetail;
    private Map<String, StudyDetail> lectureMap;


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
    }

    private void findControlls() {
        examDate = findViewById(R.id.examDate);
        studyProgramSpinner = findViewById(R.id.studyProgramSpinner);
        courseSpinner = findViewById(R.id.courseSpinner);
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

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(SearchExamActivity.this, android.R.layout.simple_spinner_item, categories);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                studyProgramSpinner.setAdapter(dataAdapter);
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
                            lectureMap.put(lecture.getName(), studyDetail);
                            categories.add(lecture.getName());
                        }

                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(SearchExamActivity.this, android.R.layout.simple_spinner_item, categories);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
                String lecture = courseSpinner.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), "Selected : " + lecture, Toast.LENGTH_SHORT).show();

                //Call<Lecture> call = NetworkManager.getInstance().getLeaRestService().getLectureById();
                //TODO: show exam dates

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    public ArrayList<Study> getStudies() {
        return studies;
    }

    public void setStudies(ArrayList<Study> studies) {
        this.studies = studies;
    }
}
