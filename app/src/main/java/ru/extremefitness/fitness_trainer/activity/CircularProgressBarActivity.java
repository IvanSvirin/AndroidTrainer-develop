package ru.extremefitness.fitness_trainer.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import ru.extremefitness.fitness_trainer.R;
import ru.extremefitness.fitness_trainer.view.WorkoutTimer;

/**
 * Created by Osipova Ekaterina on 21.03.2016.
 */
public class CircularProgressBarActivity extends AppCompatActivity {

    CountDownTimer countDownTimer;
    WorkoutTimer workoutTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circular_bar);

        workoutTimer = (WorkoutTimer) findViewById(R.id.workout_timer);
        /*if (savedInstanceState == null) {
            workoutTimer.setProgress(workoutTimer.getMax());
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(workoutTimer.getProgress() * 1000, 500) {

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;
                workoutTimer.setProgress((int) seconds);
            }

            @Override
            public void onFinish() {
            }
        }.start();

    }
}
