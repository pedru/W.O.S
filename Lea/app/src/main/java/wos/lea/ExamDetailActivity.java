package wos.lea;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wos.lea.networking.ExamDetail;
import wos.lea.networking.NetworkManager;
import wos.lea.networking.Question;

import static android.widget.LinearLayout.VERTICAL;

public class ExamDetailActivity extends AppCompatActivity {
    private ExamDetail examDetail;
    private RecyclerView questionListView;
    private List<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        int id = getIntent().getIntExtra("examId", 1);
        //questionListView = findViewById(R.id.questionList);
        questionListView = findViewById(R.id.questionRecyclerView);
        questionListView.addItemDecoration(new DividerItemDecoration(this, VERTICAL));

        questions = new ArrayList<>();
       final  QuestionListAdapter adapter = new QuestionListAdapter(questions);
        questionListView.setAdapter(adapter);
        Call<ExamDetail> call = NetworkManager.getInstance().getLeaRestService().getExamById(id);



        call.enqueue(new Callback<ExamDetail>() {
            @Override
            public void onResponse(Call<ExamDetail> call, Response<ExamDetail> response) {
                examDetail = response.body();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
                String examDate = simpleDateFormat.format(examDetail.getDate());
                ((TextView) findViewById(R.id.appBarExamDate)).setText(examDate);
                ((TextView) findViewById(R.id.appBarExamName)).setText(examDetail.getLecture().getName());

                questions.addAll(examDetail.getQuestions());
                adapter.notifyDataSetChanged();
                Log.d("EXAM DETAIL", " QUESDTIONS SIZE: " + questions.size());
            }

            @Override
            public void onFailure(Call<ExamDetail> call, Throwable t) {
                Log.d("EXAMS", "FAIL");
            }
        });

        FloatingActionButton fab = findViewById(R.id.add_question);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExamDetailActivity.this, CreateQuestionActivity.class);
                int id = getIntent().getIntExtra("examId", 1);
                intent.putExtra("examId", id);
                ExamDetailActivity.this.startActivity(intent);
            }
        });









    }


}
