package wos.lea;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wos.lea.networking.Answer;
import wos.lea.networking.ExamDetail;
import wos.lea.networking.NetworkManager;
import wos.lea.networking.Question;

import static android.widget.LinearLayout.VERTICAL;

public class QuestionDetailActivity extends AppCompatActivity {

    private Menu menu;
    private RecyclerView answerList;
    private List<Answer> answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        final TextView display_question_text = (TextView) findViewById(R.id.display_question_text);

        int examId = getIntent().getIntExtra("examId", 1);
        final int questionId = getIntent().getIntExtra("questionId", 1);

        answerList = findViewById(R.id.QuestionAnswerRecyclerView);

        answerList.addItemDecoration(new DividerItemDecoration(this, VERTICAL));

        answers = new ArrayList<>();
        final AnswerListAdapter adapter = new AnswerListAdapter(answers);
        answerList.setAdapter(adapter);


        Call<ExamDetail> call = NetworkManager.getInstance().getLeaRestService().getExamById(examId);


        call.enqueue(new Callback<ExamDetail>() {
            @Override
            public void onResponse(Call<ExamDetail> call, Response<ExamDetail> response) {
                ExamDetail examDetail = response.body();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
                String examDate = simpleDateFormat.format(examDetail.getDate());
                ((TextView) findViewById(R.id.appBarExamDate)).setText(examDate);
                ((TextView) findViewById(R.id.appBarExamName)).setText(examDetail.getLecture().getName());
                display_question_text.setText(searchQuestionByID(examDetail.getQuestions(),questionId).getQuestion());

                List<Answer> answers_new = searchQuestionByID(examDetail.getQuestions(),questionId).getAnswers();

                if(!answers_new.isEmpty())
                {
                    answers.addAll(answers_new);
                    Log.d("Answer", "ANSWER: " + answers.get(0).getText());

                    adapter.notifyDataSetChanged();
                }




            }


            @Override
            public void onFailure(Call<ExamDetail> call, Throwable t) {
                Log.d("EXAMS", "FAIL");
            }
        });






        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_answer);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText answerText = (EditText) findViewById(R.id.answerText);
                answerText.requestFocus();
                answerList.setVisibility(View.GONE);
                answerText.setVisibility(View.VISIBLE);
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.add_answer);
                fab1.setVisibility(View.INVISIBLE);

                menu.findItem(R.id.save_answer).setVisible(true);

                answerText.requestFocus();


            }
        });
    }

    private void updateAnswerList()
    {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.save_answer, menu);
        this.menu = menu;
        return true;
    }

    public Question searchQuestionByID(List<Question> questions, int id)
    {
        for (Question question : questions)
        {
            if (question.getId() == id)
                return question;
        }
        return null;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        final int questionId = getIntent().getIntExtra("questionId", 1);

        if (id == R.id.save_answer)
        {
            EditText editText = findViewById(R.id.answerText);
            if (editText.getText().toString().isEmpty())
            {
                //TODO backend stuff
                Toast toast = Toast.makeText(QuestionDetailActivity.this, R.string.answerTextEmpty, Toast.LENGTH_LONG);
                toast.show();

            }
            else {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
                Call<Void> call = NetworkManager.getInstance().getLeaRestService().createNewAnswer(questionId,editText.getText().toString());
                call.enqueue(new Callback<Void>() {
                                 @Override
                                 public void onResponse(Call<Void> call, Response<Void> response) {
                                     Log.d("subtag", "onresponse " + response);
                                     finish();
                                     startActivity(getIntent());
                                 }

                                 @Override
                                 public void onFailure(Call<Void> call, Throwable t) {
                                     Log.d("EXAMS", "FAIL");
                                 }
                             }
                );

                Toast toast = Toast.makeText(QuestionDetailActivity.this, R.string.answerSaved, Toast.LENGTH_LONG);
                toast.show();
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
