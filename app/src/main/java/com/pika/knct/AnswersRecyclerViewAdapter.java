package com.pika.knct;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AnswersRecyclerViewAdapter extends RecyclerView.Adapter<AnswersRecyclerViewAdapter.UserViewHolder> {

    Context context;
    List<AnswersCard> answersCards;

    public AnswersRecyclerViewAdapter(Context context, List<AnswersCard> answersCards) {
        this.context = context;
        this.answersCards = answersCards;
    }

    @NonNull
    @Override
    public AnswersRecyclerViewAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.answer_card,parent,false);
        UserViewHolder userViewHolder= new UserViewHolder(view);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AnswersRecyclerViewAdapter.UserViewHolder holder, int position) {
        holder.userName.setText(answersCards.get(position).getUserName());
        holder.userInstitue.setText(answersCards.get(position).getUserInstitue());
        holder.userImage.setImageResource(answersCards.get(position).getUserImage());
    }

    @Override
    public int getItemCount() {
        return answersCards.size();
    }


    public static class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView userImage;
        TextView userName;
        TextView userInstitue;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.userImage);
            userName = itemView.findViewById(R.id.chatName);
            userInstitue = itemView.findViewById(R.id.chatInstitue);
        }
    }

}
