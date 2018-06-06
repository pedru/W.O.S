package wos.lea;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wos.lea.networking.Lecture;
import wos.lea.networking.NetworkManager;

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
        int examId = getIntent().getIntExtra("examId", 1);
        EditText editText = findViewById(R.id.question_text);
        if (id == R.id.save_question)
        {

            if (editText.getText().toString().isEmpty())
            {
                Toast toast = Toast.makeText(CreateQuestionActivity.this, R.string.questionTextEmptyToast, Toast.LENGTH_LONG);
                toast.show();
            }
            else {



                Call<Void> call = NetworkManager.getInstance().getLeaRestService().createNewQuestion(examId,editText.getText().toString());
                call.enqueue(new Callback<Void>() {
                                 @Override
                                 public void onResponse(Call<Void> call, Response<Void> response) {
                                     Log.d("QUEST", "onresponse " + response);
                                 }

                                 @Override
                                 public void onFailure(Call<Void> call, Throwable t) {
                                     Log.d("QUEST", "FAIL");
                                 }
                             }
                );

                Toast toast = Toast.makeText(CreateQuestionActivity.this, R.string.saveQuestionToast, Toast.LENGTH_LONG);
                toast.show();

                finish();
     /*           Intent intent = new Intent(CreateQuestionActivity.this, ExamDetailActivity.class);

                intent.putExtra("examId", examId);
                intent.putExtra("fromQue" , true);
                this.startActivity(intent);*/
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
