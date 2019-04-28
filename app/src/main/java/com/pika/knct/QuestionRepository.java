package com.pika.knct;

import android.app.Application;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.List;

import androidx.lifecycle.LiveData;


public class QuestionRepository {
    private QuestionDao questionDao;
    private LiveData<List<Question>> questionLiveData;
    private LiveData<List<Question>> questionLimitLiveData;

    QuestionRepository(Application application) {
        Database db = Database.getDatabase(application);
        questionDao = db.questionDao();
        questionLiveData = questionDao.getQuestions();
        questionLimitLiveData = questionDao.getQuestionsLimit();
    }

    public void insert(Question question) {
        new insertQuestionTask(questionDao).execute(question);
    }

    public LiveData<List<Question>> getQuestions() {
        return questionLiveData;
    }

    public LiveData<List<Question>> getQuestionsLimit() { return questionLimitLiveData; }

    public void clearTable() { new clearQuestionTableTask(questionDao).execute(); }
}

class insertQuestionTask extends AsyncTask<Question, Void, Void> {
    private WeakReference<QuestionDao> questionDaoWeakReference;

    insertQuestionTask(QuestionDao questionDao) {
        this.questionDaoWeakReference = new WeakReference<>(questionDao);
    }

    @Override
    protected Void doInBackground(Question... questions) {
        questionDaoWeakReference.get().insert(questions[0]);
        return null;
    }
}

class clearQuestionTableTask extends AsyncTask<Void, Void, Void> {
    private WeakReference<QuestionDao> questionDaoWeakReference;

    clearQuestionTableTask(QuestionDao questionDao) {
        this.questionDaoWeakReference = new WeakReference<>(questionDao);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        questionDaoWeakReference.get().clearTable();
        return null;
    }
}