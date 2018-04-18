package wos.lea;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SearchExamActivity extends AppCompatActivity {
    Button showCalenderButton;
    Spinner studyProgramSpinner;
    Spinner courseSpinner;
    int d,m,y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_exam);
        findControlls();
        showCalenderButton.setText("Select Date");
        showCalenderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                d = calendar.get(Calendar.DAY_OF_MONTH);
                m = calendar.get(Calendar.MONTH);
                y = calendar.get(Calendar.YEAR);
                DatePickerDialog pickerDialog = new DatePickerDialog(SearchExamActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1+=1;
                        showCalenderButton.setText(i2 + "." + i1 + "." + i);
                    }
                }, y, m, d);
                pickerDialog.show();
            }
        });
        SetDropdownElements();
    }

    private void findControlls() {
        showCalenderButton = findViewById(R.id.dateButton);
        studyProgramSpinner = findViewById(R.id.studyProgramSpinner);
        courseSpinner = findViewById(R.id.courseSpinner);
    }

    private void SetDropdownElements() {

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Automobile");
        categories.add("Business Services");
        categories.add("Computers");
        categories.add("Education");
        categories.add("Personal");
        categories.add("Travel");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        studyProgramSpinner.setAdapter(dataAdapter);
        courseSpinner.setAdapter(dataAdapter);
    }
}
