package com.example.test_hw04_gymlog.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.test_hw04_gymlog.Database.entities.GymLog;

import java.util.ArrayList;


@Dao
public interface GymLogDAO {

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    void insert(GymLog gymlog);


    @Query("SELECT * from " + GymLogDatabase.GYM_LOG_TABLE)
    ArrayList<GymLog> getAllRecords();




}
