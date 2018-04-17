package wos.lea;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

public class SearchExamActivity extends AppCompatActivity {
Button showCalenderButton;
int d,m,y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_exam);
        findcontrol();
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
    }

    private void findcontrol() {
        showCalenderButton=findViewById(R.id.dateButton);
    }
}
