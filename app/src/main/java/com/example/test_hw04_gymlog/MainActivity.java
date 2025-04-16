package com.example.test_hw04_gymlog;

import android.os.Bundle;

import com.example.test_hw04_gymlog.database.GymLogRepository;
import com.example.test_hw04_gymlog.database.entities.GymLog;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.test_hw04_gymlog.databinding.ActivityMainBinding;


import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    String mExercise = "";
    double mWeight = 0.0;
    int mReps = 0;
    private ActivityMainBinding binding;
private static GymLogRepository repository;
    public static final String TAG = "DAC GYMLOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Gives access to DTB
            //repository = new GymLogRepository(getApplication());

        //TODO: LEft oFF HERE 410 - 215AM
        repository = GymLogRepository.getRepository(getApplication());


// scrollable
        binding.logDisplayTextView.setMovementMethod(new ScrollingMovementMethod());

        updateDisplay();
        // Set up the toolbar
        binding.logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformationFromDisplay();
                insertGymLogRecord();
              //  Toast.makeText(MainActivity.this, "Log button clicked!", Toast.LENGTH_SHORT).show();
                updateDisplay();

            }
        });

    }

    private void insertGymLogRecord() {

        if (mExercise.isEmpty()) {
            Toast.makeText(this, "Please enter an exercise", Toast.LENGTH_SHORT).show();
            return;
        }

        GymLog gymLog = new GymLog(mExercise, mWeight, mReps);
        repository.insertGymLog(gymLog);
    }

    private void updateDisplay() {
        ArrayList<GymLog> allLogs = repository.getAllLogs();

        // Check if the list is empty
        if (allLogs.isEmpty()) {
            binding.logDisplayTextView.setText(R.string.nothing_to_show_time_to_hit_the_gym);
        }
        StringBuilder sb = new StringBuilder();
        for (GymLog log : allLogs) {
            sb = new StringBuilder();
            sb.append(log);
        }
        binding.logDisplayTextView.setText(sb.toString());
    }





    private void getInformationFromDisplay() {
        mExercise = binding.exerciseInputEditText.getText().toString();

        try {
            mWeight = Double.parseDouble(binding.weightInputEditText.getText().toString());
        } catch (NumberFormatException e) {
            Log.d(TAG, "Error reading value from Weight edit text");
        }

        try {
            mReps = Integer.parseInt(binding.repInputEditText.getText().toString());
        } catch (NumberFormatException e) {
            Log.d(TAG, "Error reading value from reps edit text");
        }
    }
}