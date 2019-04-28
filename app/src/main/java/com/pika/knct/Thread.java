package com.pika.knct;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Thread extends AppCompatActivity {

    View view;
    Dialog answerPopup;
    RecyclerView recyclerView;
    List<AnswersCard> answersCardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        answersCardList = new ArrayList<>();
        anwseradder();
        setContentView(R.layout.activity_thread);
        answerPopup = new Dialog(getApplicationContext());

        recyclerView = findViewById(R.id.answerRecycler);
        AnswersRecyclerViewAdapter answersRecyclerViewAdapter = new AnswersRecyclerViewAdapter(getApplicationContext(), answersCardList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(answersRecyclerViewAdapter);
    }

    public void anwseradder(){
        answersCardList.add(new AnswersCard(R.drawable.default_avatar,"Software engineering philosophy and discipline are taught early and practised throughout the program. ","Ramish"));
        answersCardList.add(new AnswersCard(R.drawable.default_avatar,"Software Engineers help develop software for telecommunications.","Faiz"));
        answersCardList.add(new AnswersCard(R.drawable.default_avatar,"Your degree will be a Bachelor of Software Engineering (BSE).\n" ,"Ammar"));
    }

    public void popup(View view) {
        answerPopup.setContentView(R.layout.answer_popup);
        answerPopup.show();
    }


}
