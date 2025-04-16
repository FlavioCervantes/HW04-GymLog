package com.example.test_hw04_gymlog.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.test_hw04_gymlog.database.entities.GymLog;

import java.util.List;


@Dao
public interface GymLogDAO {

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    void insert(GymLog gymlog);


    @Query("SELECT * from " + GymLogDatabase.GYM_LOG_TABLE + " ORDER BY date DESC")
   List<GymLog> getAllRecords();
}
