package com.pika.knct;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Question question);

    @Query("SELECT * FROM Question")
    LiveData<List<Question>> getQuestions();

    @Query("SELECT * FROM Question ORDER BY id ASC LIMIT 10")
    LiveData<List<Question>> getQuestionsLimit();

    @Query("DELETE FROM Question")
    void clearTable();
}
