package com.pika.knct;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class QuestionViewModel extends AndroidViewModel {
    private QuestionRepository questionRepository;
    private LiveData<List<Question>> questionLiveData;
    private LiveData<List<Question>> questionLimitLiveData;

    public QuestionViewModel(Application application) {
        super(application);
        questionRepository = new QuestionRepository(application);
        questionLiveData = questionRepository.getQuestions();
        questionLimitLiveData = questionRepository.getQuestionsLimit();
    }

    public LiveData<List<Question>> getQuestions() {
        return questionLiveData;
    }

    public LiveData<List<Question>> getQuestionsLimitLiveData() {
        return questionLimitLiveData;
    }

    public void insertQuestion(Question question) { questionRepository.insert(question); }

    public void clearTable() { questionRepository.clearTable(); }
}
