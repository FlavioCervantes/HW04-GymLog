package com.example.test_hw04_gymlog.database;

import android.app.Application;
import android.util.Log;

import com.example.test_hw04_gymlog.database.entities.GymLog;
import com.example.test_hw04_gymlog.MainActivity;
import com.example.test_hw04_gymlog.database.entities.User;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class GymLogRepository {

    private final GymLogDAO gymLogDAO;
    private final UserDAO userDAO;

    private ArrayList<GymLog> allLogs;

    private static GymLogRepository repository;
    private User user;


    private GymLogRepository(Application application) {
        GymLogDatabase db = GymLogDatabase.getDatabase(application);
        this.gymLogDAO = db.gymLogDAO();
        this.userDAO = db.userDAO();
        this.allLogs = (ArrayList<GymLog>) this.gymLogDAO.getAllRecords();
    }


    public static GymLogRepository getRepository(Application application) {

        if (repository != null) {
            return repository;
        }
        Future<GymLogRepository> future = GymLogDatabase.databaseWriteExecutor.submit(
                //    new*
                new Callable<GymLogRepository>() {

                    // new*
                    @Override
                    public GymLogRepository call() throws Exception {
                        return new GymLogRepository(application);

                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d(MainActivity.TAG, "Problem getting GymLogRepo.,  thread error.");
        }
        return null;
    }


    public ArrayList<GymLog> getAllLogs() {
        Future<ArrayList<GymLog>> future = GymLogDatabase.databaseWriteExecutor.submit(
                //   new*
                new Callable<ArrayList<GymLog>>() {
                    //    new*
                    @Override
                    public ArrayList<GymLog> call() throws Exception {
                        return (ArrayList<GymLog>) gymLogDAO.getAllRecords();
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            // Handle the exception
            Log.i(MainActivity.TAG, "Error getting all GymLogs in the repo: ");
        }
        return null;
    }

    public void insertGymLog(GymLog gymLog) {
        GymLogDatabase.databaseWriteExecutor.execute(() -> {
            gymLogDAO.insert(gymLog);
        });
    }

    public void insertUser(User... user) {
        GymLogDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.insert(user);
        });
    }

}



