package wos.lea;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wos.lea.networking.Exam;
import wos.lea.networking.ExamDetail;
import wos.lea.networking.ExamSubscription;
import wos.lea.networking.NetworkManager;
import wos.lea.networking.UserDetail;

public class ExamDetailActivity extends AppCompatActivity {
    private ArrayList<Exam> exams;

    private ExamDetail examDetail;
    private ListView questionListView;
    private int id;
    private boolean canRememberExam;
    private UserDetail userDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        id = getIntent().getIntExtra("examId", 1);
        questionListView = findViewById(R.id.questionList);
        Call<ExamDetail> call = NetworkManager.getInstance().getLeaRestService().getExamById(id);

        call.enqueue(new Callback<ExamDetail>() {
            @Override
            public void onResponse(Call<ExamDetail> call, Response<ExamDetail> response) {
                examDetail = response.body();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
                String examDate = simpleDateFormat.format(examDetail.getDate());
                ((TextView) findViewById(R.id.appBarExamDate)).setText(examDate);
                ((TextView) findViewById(R.id.appBarExamName)).setText(examDetail.getLecture().getName());

                QuestionListAdapter adapter = new QuestionListAdapter(ExamDetailActivity.this, examDetail.getQuestions());
                questionListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ExamDetail> call, Throwable t) {
                Log.d("EXAMS", "FAIL");
            }
        });

        Call<UserDetail> call_subscribe = NetworkManager.getInstance().leaRestService.getMyUser();


        call_subscribe.enqueue(new Callback<UserDetail>() {
            @Override
            public void onResponse(Call<UserDetail> call_subscribe, Response<UserDetail> response) {

                UserDetail userDetail = response.body();
                exams = new ArrayList<>(userDetail.getExams());
                Log.d("set cr", "set");
                 for (Exam ex : exams) {
                    if(ex.getId() == id){
                        canRememberExam = false;
                        break;
                    }
                    else {
                        canRememberExam = true;
                    }
                }
            }

            @Override
            public void onFailure(Call<UserDetail> call_subscribe, Throwable t) {
                Log.d("EXAMS", "FAIL");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.exam_detail_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        for(Exam ex : exams)
        {
            Log.d("liste ", " " + ex.getLecture().getName() + " " + ex.getId());
        }
        switch (item.getItemId()) {

            case R.id.action_remember:
                if (canRememberExam)
                {
                    Call<ExamSubscription> call_sub = NetworkManager.getInstance().leaRestService.subscribeExam(id);
                    call_sub.enqueue(new Callback<ExamSubscription>() {
                                         @Override
                                         public void onResponse(Call<ExamSubscription> call, Response<ExamSubscription> response) {
                                             Log.d("subtag", "onresponse sub" + response);
                                         }

                                         @Override
                                         public void onFailure(Call<ExamSubscription> call, Throwable t) {
                                             Log.d("EXAMS sub", "FAIL");
                                         }
                                     }
                    );
                }
                else
                {
                    Call<ExamSubscription> call_unsub = NetworkManager.getInstance().leaRestService.unsubscribeExam(id);
                    call_unsub.enqueue(new Callback<ExamSubscription>() {
                                         @Override
                                         public void onResponse(Call<ExamSubscription> call, Response<ExamSubscription> response) {
                                             Log.d("unsubtag", "onresponse unsubscribe" + response);
                                         }

                                         @Override
                                         public void onFailure(Call<ExamSubscription> call, Throwable t) {
                                             Log.d("EXAMS unsubscribe", "FAIL");
                                         }
                                     }
                    );

                }
                canRememberExam =! canRememberExam;

                invalidateOptionsMenu();
                for(Exam ex : exams)
                {
                    Log.d("liste2 act ", " " + ex.getLecture().getName() + " " + ex.getId());
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_remember);
        if(canRememberExam){
            item.setIcon(R.drawable.ic_action_star_0);
        }
        else
        {
            item.setIcon(R.drawable.ic_action_star_10);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    public boolean getCanRememberExam() {
        return canRememberExam;
    }

    public ArrayList<Exam> getExams() {
        return exams;
    }

    public int getId() {
        return id;
    }
}
