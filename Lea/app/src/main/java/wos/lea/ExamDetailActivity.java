package wos.lea;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wos.lea.networking.Exam;
import wos.lea.networking.ExamDetail;
import wos.lea.networking.NetworkManager;
import wos.lea.networking.Question;

public class ExamDetailActivity extends AppCompatActivity {
    private ExamDetail examDetail;
    private ListView questionListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int id = getIntent().getIntExtra("examId", 0);
        questionListView = findViewById(R.id.questionList);
        Call<ExamDetail> call = NetworkManager.getInstance().leaRestService.getExamById(id);

        call.enqueue(new Callback<ExamDetail>() {
            @Override
            public void onResponse(Call<ExamDetail> call, Response<ExamDetail> response) {
                examDetail = response.body();
                setTitle(examDetail.getLecture());
                QuestionListAdapter adapter = new QuestionListAdapter(ExamDetailActivity.this, examDetail.getQuestions());
                questionListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ExamDetail> call, Throwable t) {
                Log.d("EXAMS", "FAIL");
            }
        });
    }

}
