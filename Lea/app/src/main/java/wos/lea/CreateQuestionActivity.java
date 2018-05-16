package wos.lea;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class CreateQuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.save_question, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.save_question)
        {
            EditText editText = findViewById(R.id.question_text);
            if (editText.getText().toString().isEmpty())
            {
                Toast toast = Toast.makeText(CreateQuestionActivity.this, R.string.questionTextEmptyToast, Toast.LENGTH_LONG);
                toast.show();
            }
            else {
                Toast toast = Toast.makeText(CreateQuestionActivity.this, R.string.saveQuestionToast, Toast.LENGTH_LONG);
                toast.show();
                int examId = getIntent().getIntExtra("examId", 1);
                Intent intent = new Intent(CreateQuestionActivity.this, ExamDetailActivity.class);
                intent.putExtra("examId", examId);
                this.startActivity(intent);
            }
            return true;
        }
        if (item.getItemId()== android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
