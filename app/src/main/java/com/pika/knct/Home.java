package com.pika.knct;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {
    Dialog editor_popup;
    ImageButton addbutton;

    View view;
    RecyclerView recyclerView;
    List<Querycard> querycardList;
    QueryRecyclerViewAdapter queryRecyclerViewAdapter;

    private UserViewModel userViewModel;
    private QuestionViewModel questionViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.querycards);
        queryRecyclerViewAdapter = new QueryRecyclerViewAdapter(getContext(),querycardList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(queryRecyclerViewAdapter);
        editor_popup = new Dialog(getContext());
        addbutton = view.findViewById(R.id.imageButton);
        addbutton.setOnClickListener(v -> showpop());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        String query = "";
        String url = Server.urlBuilder("get-question", query);
        new getQuestionsTask(userViewModel, questionViewModel).execute(url);
    }

    public void showpop(){
        editor_popup.setContentView(R.layout.editor_popup);
        editor_popup.show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Connect with database
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        questionViewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);

        // Initialize Question Adapter
        querycardList = new ArrayList<>();

        if (questionViewModel.getQuestionsLimitLiveData().getValue() != null) {
            addQuestionsToAdapter(questionViewModel.getQuestionsLimitLiveData().getValue());
        }

        questionViewModel.getQuestionsLimitLiveData().observe(this, questions -> {
            removeQuestionsFromAdapter();
            addQuestionsToAdapter(questions);
        });
    }

    private void addQuestionsToAdapter(List<Question> questions) {
        if (questions.size() > 0) {
            Question question;

            Log.i("Counter", String.valueOf(questions.size()));
            for (int counter = 0; questions.size()<10 ? counter<questions.size() : counter<10; counter++) {
                question = questions.get(counter);

                User user = userViewModel.getUserFromId(question.getUserId());

                Bitmap bitmap = null;

                // Load Image
                // Null Check!!!!
                String imageURI = user.getPhotoURI();
                if (imageURI.equals("")) {
                    try {
                        bitmap = Picasso.with(getContext()).load(R.drawable.default_avatar).get();
                    } catch (Exception e) {
                        Log.i("Bitmap Load", e.toString());
                    }
                } else {
                    try {
                        bitmap = Picasso.with(getContext()).load(imageURI).get();
                    } catch (Exception e) {
                        Log.i("ImageUri Load", e.toString());
                    }
                }

                Log.i("Counter", question.getQuestion());
                querycardList.add(new Querycard(question.getQuestion(), user.getDisplayName(), user.getInstitute(),
                        bitmap, question.getSubject(), question.getPostedOn()));
            }

            queryRecyclerViewAdapter.updateUI();
        }
    }

    private void removeQuestionsFromAdapter() {
        querycardList.clear();
        queryRecyclerViewAdapter.updateUI();
    }
}
