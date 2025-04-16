package com.example.test_hw04_gymlog.database;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.test_hw04_gymlog.database.entities.User;

import java.util.List;

import androidx.room.Dao;
@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)

    //This method will take zero or more user objects and insert them into the database.
    void insert(User... user);

@Delete
    void delete(User user);

@Query("SELECT * FROM " + GymLogDatabase.USER_TABLE + " ORDER BY username")
    List<User> getAllUsers();

   @Query("DELETE from " + GymLogDatabase.USER_TABLE)
   void deleteAll();
}
