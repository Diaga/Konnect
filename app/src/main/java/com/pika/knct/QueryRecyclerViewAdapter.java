package com.pika.knct;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class QueryRecyclerViewAdapter extends RecyclerView.Adapter<QueryRecyclerViewAdapter.QueryViewHolder> {


    Context context;
    List<Querycard> querydata;

    public QueryRecyclerViewAdapter(Context context, List<Querycard> querydata) {
        this.context = context;
        this.querydata = querydata;
    }

    @NonNull
    @Override
    public QueryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.query_card, parent,false);
        QueryViewHolder queryViewHolder = new QueryViewHolder(view);
        //queryViewHolder.question.
        return queryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QueryViewHolder queryViewHolder, int position) {
        queryViewHolder.question.setText(querydata.get(position).getQuestions());
        queryViewHolder.name.setText(querydata.get(position).getName());
        queryViewHolder.institute.setText(querydata.get(position).getLocation());
        queryViewHolder.userImage.setImageBitmap(querydata.get(position).getUserimage());
        queryViewHolder.answer.setText(querydata.get(position).getAnswers());
        queryViewHolder.query_cards.setOnClickListener(v -> {
            Toast.makeText(context,"Opened"+ queryViewHolder.getAdapterPosition(),Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(v.getContext(), Thread.class);
            v.getContext().startActivity(intent);
        });
    }

    public void updateUI() {
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return querydata.size();
    }

    public static class QueryViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout query_cards;

        TextView time;
        TextView question;
        TextView name;
        TextView institute;
        ImageView userImage;
        TextView answer;

        public QueryViewHolder(@NonNull View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.timeText);
            query_cards = itemView.findViewById(R.id.query_card);
            question = itemView.findViewById(R.id.questionText);
            name = itemView.findViewById(R.id.nameText);
            institute = itemView.findViewById(R.id.locationText);
            userImage = itemView.findViewById(R.id.avatar);
            answer = itemView.findViewById(R.id.answerText);
        }
    }
}
