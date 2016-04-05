package ru.extremefitness.fitness_trainer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.parceler.Parcels;

import ru.extremefitness.fitness_trainer.R;
import ru.extremefitness.fitness_trainer.models.BodyPartEfficiency;
import ru.extremefitness.fitness_trainer.models.Exercise;
import ru.extremefitness.fitness_trainer.view.EfficiencyChart;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Osipova Ekaterina on 09.03.2016.
 */
public class ExerciseActivity extends AppCompatActivity {

    public static final int EXERCISE_REQUEST_CODE = 701;
    private Exercise exercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        exercise = Parcels.unwrap(getIntent().getParcelableExtra(Exercise.EXERCISE_EXTRA));

        TextView exerciseNameTextView = (TextView) findViewById(R.id.exercise_name);
        TextView exerciseAboutTextView = (TextView) findViewById(R.id.exercise_about);
        EfficiencyChart exerciseEfficiencyChart = (EfficiencyChart) findViewById(R.id
                .exercise_efficiency);
        RecyclerView setsRecyclerView = (RecyclerView) findViewById(R.id.sets_list);

        exerciseNameTextView.setText(exercise.getName());
        exerciseAboutTextView.setText(exercise.getAbout());
        exerciseEfficiencyChart.setData(exercise.getExerciseEfficiency());
    }

    public void addSet(View view) {
    }

    public void actionOk(View view) {
        Intent intent = new Intent();
        intent.putExtra(Exercise.EXERCISE_EXTRA, Parcels.wrap(exercise));
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
