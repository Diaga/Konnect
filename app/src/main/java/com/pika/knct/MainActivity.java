package com.pika.knct;

import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity{

    private UserViewModel userViewModel;
    private QuestionViewModel questionViewModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connect with database
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        questionViewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);

        fragmentLoader(new Home());

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public boolean fragmentLoader(Fragment fragment){
        if (fragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fg_container,fragment).commit();
            return true;
        }
        return false;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = menuItem -> {
                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        fragment = new Home();
                        break;
                    case R.id.answers:
                        fragment = new Answers();
                        break;
                    case R.id.chat:
                        fragment = new Chat();
                        break;
                    case R.id.notifications:
                        fragment = new Notifications();
                        break;
                    case R.id.profile:
                        fragment = new Profile();
                        break;
                }
                return fragmentLoader(fragment);
            };

    public void postQuestion(View view) {
        View cardView = view.getRootView();
        EditText questionEdit = cardView.findViewById(R.id.titleText);
        EditText subjectEdit = cardView.findViewById(R.id.subjectText);

        String question = questionEdit.getText().toString();
        String subject = subjectEdit.getText().toString();

        String postedOn = String.valueOf(System.currentTimeMillis());
        String id = userViewModel.getSignedInUser().getId();
        String tags = "NUST, PIEAS";

        String[] args = {"id", "postedOn", "question", "subject", "tags"};
        String[] params = {id, postedOn, question, subject, tags};

        String query = Server.queryBuilder(args, params);
        String url = Server.urlBuilder("question", query);

        new postQuestionTask(userViewModel, questionViewModel).execute(url);
    }
}