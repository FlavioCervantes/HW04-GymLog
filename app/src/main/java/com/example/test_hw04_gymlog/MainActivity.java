package com.example.test_hw04_gymlog;

import android.os.Bundle;

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

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    String mExercise = "";
    double mWeight = 0.0;
    int mReps = 0;
    ActivityMainBinding binding;

    private static final String TAG = "DAC GYMLOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
// scrollable
        binding.logDisplayTextView.setMovementMethod(new ScrollingMovementMethod());
        // Set up the toolbar
        binding.logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformationFromDisplay();
                updateDisplay();

            }
        });

    }

    private void updateDisplay() {
        getInformationFromDisplay();
     String currentInfo = binding.logDisplayTextView.getText().toString();
    String newDisplay = String.format(Locale.US,"Exercise: %s%nWeight:%.2f%nReps:%d%n     %n%s", mExercise, mWeight, mReps,currentInfo);

    binding.logDisplayTextView.setText(newDisplay);
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