package com.example.test_hw04_gymlog.database;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.test_hw04_gymlog.database.entities.GymLog;
import com.example.test_hw04_gymlog.MainActivity;
import com.example.test_hw04_gymlog.database.entities.User;
import com.example.test_hw04_gymlog.database.typeConverters.LocalDateTypeConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@TypeConverters(LocalDateTypeConverter.class)

@Database(entities =  {GymLog.class, User.class}, version = 2, exportSchema = false)
public abstract class GymLogDatabase extends RoomDatabase {


    public static final String USER_TABLE = "user_table";
    private static final String DATABASE_NAME = "gymLog.database";
    public static final String GYM_LOG_TABLE = "gymLogTable";

    private static volatile  GymLogDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static GymLogDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GymLogDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            GymLogDatabase.class,
                            DATABASE_NAME
                    )
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                Log.i(MainActivity.TAG, "Database created");
            databaseWriteExecutor.execute(() -> {
                UserDAO dao = INSTANCE.userDAO();
                dao.deleteAll();
                User admin = new User("admin", "admin");
                admin.setAdmin(true); // Set the user as admin
                dao.insert(admin);

                User testUser1 = new User("testuser1", "testuser1");
                dao.insert(testUser1);
            });
        }
    };

    public abstract GymLogDAO gymLogDAO();

    public abstract UserDAO userDAO();
}
