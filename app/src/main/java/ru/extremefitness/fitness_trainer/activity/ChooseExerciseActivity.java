package ru.extremefitness.fitness_trainer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.extremefitness.fitness_trainer.AdapterItemSelectedListener;
import ru.extremefitness.fitness_trainer.R;
import ru.extremefitness.fitness_trainer.adapter.ChooseExerciseRecyclerViewAdapter;
import ru.extremefitness.fitness_trainer.models.Exercise;
import ru.extremefitness.fitness_trainer.models.ExtremeRequest;
import ru.extremefitness.fitness_trainer.models.ExtremeResponse;
import ru.extremefitness.fitness_trainer.network.RestClient;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Osipova Ekaterina on 02.03.2016.
 */
public class ChooseExerciseActivity extends AppCompatActivity implements AdapterItemSelectedListener<Exercise> {

    public static final int CHOOSE_EXERCISE_REQUEST_CODE = 700;

    RecyclerView chooseExercisesRecyclerView;
    List<Exercise> exercises = new ArrayList<>();
    private ChooseExerciseRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym);

        chooseExercisesRecyclerView = (RecyclerView) findViewById(R.id.choose_exercise_list);
        chooseExercisesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChooseExerciseRecyclerViewAdapter(exercises, this);
        chooseExercisesRecyclerView.setAdapter(adapter);

        Call<ExtremeResponse> extremeResponseCall = RestClient.getApiService().getGym(new ExtremeRequest("",
                "ANDROID", "get_gym", "1.1"));
        extremeResponseCall.enqueue(new Callback<ExtremeResponse>() {
            @Override
            public void onResponse(Call<ExtremeResponse> call, Response<ExtremeResponse> response) {
                if (response.isSuccess()) {
                    exercises.clear();
                    exercises.addAll(response.body().getData().getExercises());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ExtremeResponse> call, Throwable t) {
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onItemSelected(Exercise item) {
        Intent intent = new Intent(this, ExerciseActivity.class);
        intent.putExtra(Exercise.EXERCISE_EXTRA, Parcels.wrap(item));
        startActivityForResult(intent, ExerciseActivity.EXERCISE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == ExerciseActivity.EXERCISE_REQUEST_CODE) {
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
