package ru.extremefitness.fitness_trainer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.List;

import ru.extremefitness.fitness_trainer.R;
import ru.extremefitness.fitness_trainer.adapter.ExerciseRecyclerViewAdapter;
import ru.extremefitness.fitness_trainer.models.Exercise;
import ru.extremefitness.fitness_trainer.models.ExerciseSet;
import ru.extremefitness.fitness_trainer.models.Training;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Osipova Ekaterina on 23.02.2016.
 */
public class ExercisesActivity extends AppCompatActivity {

    Training training;
    List<ExerciseSet> exercises;

    ScrollView scrollView;
    private ExerciseRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        training = Parcels.unwrap(getIntent().getParcelableExtra(Training.TRAINING_EXTRA));
        boolean editable = getIntent().getBooleanExtra(ProgramActivity.EDITABLE_EXTRA, false);
        exercises = training.getExercises();

        TextView exercisesTitle = (TextView) findViewById(R.id.exercises_title);
        RecyclerView exercisesRecyclerView = (RecyclerView) findViewById(R.id.exercises_list);
        Button addExerciseBtn = (Button) findViewById(R.id.add_exercise);
        scrollView = (ScrollView) findViewById(R.id.scroll_view);

        exercisesTitle.setText(training.getName());
        addExerciseBtn.setVisibility(editable ? View.VISIBLE : View.GONE);
        exercisesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ExerciseRecyclerViewAdapter(exercises);
        exercisesRecyclerView.setAdapter(adapter);
    }

    public void addExercise(View view) {
        startActivityForResult(new Intent(this, ChooseExerciseActivity.class),
                ChooseExerciseActivity.CHOOSE_EXERCISE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == ChooseExerciseActivity
                .CHOOSE_EXERCISE_REQUEST_CODE) {
            Exercise exercise = Parcels.unwrap(data.getParcelableExtra(Exercise.EXERCISE_EXTRA));
            exercises.add(new ExerciseSet(exercise));
            adapter.notifyDataSetChanged();
            scrollView.post(new Runnable() {
                public void run() {
                    scrollView.smoothScrollTo(0, scrollView.getBottom());
                }
            });
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
