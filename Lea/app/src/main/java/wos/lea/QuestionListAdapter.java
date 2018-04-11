package wos.lea;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import wos.lea.networking.Exam;
import wos.lea.networking.Question;

/**
 * Created by martin on 11.04.18.
 */

public class QuestionListAdapter extends ArrayAdapter<Question> implements View.OnClickListener {
    private Context context;

    public QuestionListAdapter(Context context, List<Question> questions) {
        super(context, 0, questions);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Question question = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.queston_list_item, parent, false);
        }

        TextView questionTextView = (TextView) convertView.findViewById(R.id.questionText);
        questionTextView.setText(question.getQuestion());
        return convertView;
    }

    @Override
    public void onClick(View view) {
        Exam exam = (Exam) view.getTag();

        Intent intent = new Intent(context, ExamDetailActivity.class);
        intent.putExtra("examId", exam.getId());
        context.startActivity(intent);
    }
}

