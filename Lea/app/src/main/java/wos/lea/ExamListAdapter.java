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

/**
 * Created by martin on 04.04.18.
 */

public class ExamListAdapter extends ArrayAdapter<Exam> implements View.OnClickListener {
    private Context context;

    public ExamListAdapter(Context context, List<Exam> exams) {
        super(context, 0, exams);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Exam exam = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView examNameView = (TextView) convertView.findViewById(R.id.name);
        TextView examDateView = (TextView) convertView.findViewById(R.id.date);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date examDate = exam.getDate();
        examNameView.setText(exam.getLecture().getName());
        examDateView.setText(simpleDateFormat.format(examDate));

        convertView.setOnClickListener(this);
        convertView.setTag(exam);

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
