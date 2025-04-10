package com.example.test_hw04_gymlog.Database;

import android.app.Application;
import android.util.Log;

import com.example.test_hw04_gymlog.Database.entities.GymLog;
import com.example.test_hw04_gymlog.MainActivity;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class GymLogRepository {

    private GymLogDAO gymLogDAO;

    private ArrayList<GymLog> allLogs;


    public GymLogRepository(Application application) {
        GymLogDatabase db = GymLogDatabase.getDatabase(application);
        this.gymLogDAO = db.gymLogDAO();
        this.allLogs = this.gymLogDAO.getAllRecords();
    }


    public ArrayList<GymLog> getAllLogs() {
        Future<ArrayList<GymLog>> future = GymLogDatabase.databaseWriteExecutor.submit(
             //   new*
                        new Callable<ArrayList<GymLog>>() {
                    //    new*
                            @Override
                            public ArrayList<GymLog> call() throws Exception {
                                return gymLogDAO.getAllRecords();
                            }
                        }
        );
        try {
            return future.get();
        } catch (Exception e) {
            // Handle the exception
            Log.i(MainActivity.TAG, "Error getting all GymLogs in the repo: " + e.getMessage());
        }
        return null;
    }

    public void insertGymLog(GymLog gymLog) {

        GymLogDatabase.databaseWriteExecutor.execute(() -> {

            // Insert the GymLog into the database
            gymLogDAO.insert(gymLog);
          // Log.i(MainActivity.TAG, "GymLog inserted: " + gymLog.toString());
        });




        GymLogDatabase.databaseWriteExecutor.execute(() -> {
            gymLogDAO.insert(gymLog);
            Log.i(MainActivity.TAG, "GymLog inserted: " + gymLog.toString());
        });
    }




}
