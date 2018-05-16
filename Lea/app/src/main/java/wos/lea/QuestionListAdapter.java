package wos.lea;

import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import wos.lea.networking.Question;

/**
 * Created by martin on 11.04.18.
 */

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.MyViewHolder> {

    private List<Question> questions;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView text;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            text = view.findViewById(R.id.question_text);

        }
        @Override
        public void onClick(View view) {

            Question question = questions.get(this.getAdapterPosition());

            Log.d("QUESTIONS", "CLICKED " + question.getId());

        }
    }





    public QuestionListAdapter(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Question question = questions.get(position);
        holder.text.setText(question.getQuestion());

    }

    @Override
    public int getItemCount() {
        return questions.size();
    }






}