package wos.lea;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import wos.lea.networking.Answer;

/**
 * Created by martin on 11.04.18.
 */

public class AnswerListAdapter extends RecyclerView.Adapter<AnswerListAdapter.MyViewHolder> {

    private List<Answer> answers;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView text;
        private Context context;



        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            text = view.findViewById(R.id.question_text);
            context = view.getContext();

        }
        @Override
        public void onClick(View view) {


        }
    }



    public AnswerListAdapter(List<Answer> answers) {
        this.answers = answers;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Answer answer = answers.get(position);
        holder.text.setText(answer.getText());

    }

    @Override
    public int getItemCount() {
        return answers.size();
    }






}