package ru.extremefitness.fitness_trainer.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;
import java.util.Arrays;

import ru.extremefitness.fitness_trainer.models.GetGymContainer;
import ru.extremefitness.fitness_trainer.network.ModelsEnum;
import ru.extremefitness.fitness_trainer.network.NetworkDispatcher;

/**
 * Created by Александр on 18.11.2015.
 */

//Активность для загрузки упражнений

public class UnderExercisesActivity extends AppCompatActivity implements Serializable {

    private UnderExercisesViewModel viewModel;
    private UnderExercisesViewModel.ViewModes currentMode = UnderExercisesViewModel.ViewModes.CONTENT;
    public GetGymContainer.ExercisesInfo[] exercisesInfo;


    public static void start(final Context context) {
        final Intent intent = new Intent(context, UnderExercisesActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        viewModel = new UnderExercisesViewModel(this);
        setContentView(viewModel.getView());

        Intent intent = getIntent();


        try {

            //parse exercises info
            Object[] exercisesInfoSerializable = (Object[]) intent.getSerializableExtra("exercises_info");

            exercisesInfo = new GetGymContainer.ExercisesInfo[exercisesInfoSerializable.length];
            for (int i = 0; i < exercisesInfoSerializable.length; ++i) {
                exercisesInfo[i] = (GetGymContainer.ExercisesInfo) exercisesInfoSerializable[i];
                System.out.println(exercisesInfo[i].getName());
                System.out.println(Arrays.toString(exercisesInfo[i].getVideos()));
                String[] vidos = exercisesInfo[i].getVideos();

            }


        } catch (Exception e) {
            System.out.println("Error: " + e);
        }


        if (savedInstanceState == null) {
            loadList();
        }


        int id = intent.getIntExtra("id", 0);
        String name = intent.getStringExtra("name");

        //Установка переданного названия из предыдущей активности
        setTitle(name);
    }


    void loadList() {
        NetworkDispatcher.invoke(ModelsEnum.GET_GYM.with("router", "get_gym"));
        currentMode = UnderExercisesViewModel.ViewModes.PROGRESS;
        currentMode.apply(viewModel);
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(loadReceiver, loadReceiver.intentFilter);

        currentMode.apply(viewModel);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(loadReceiver);
    }

    private LoadReceiver loadReceiver = new LoadReceiver();

    private final class LoadReceiver extends BroadcastReceiver {

        private IntentFilter intentFilter;

        LoadReceiver() {
            intentFilter = new IntentFilter();
            intentFilter.addAction(NetworkDispatcher.ACTION_LOAD_DONE);
            intentFilter.addAction(NetworkDispatcher.ACTION_LOAD_ERROR);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (NetworkDispatcher.ACTION_LOAD_DONE.equals(intent.getAction())) {
                viewModel.setContent(exercisesInfo);
                currentMode = UnderExercisesViewModel.ViewModes.CONTENT;
                currentMode.apply(viewModel);
            } else if (NetworkDispatcher.ACTION_LOAD_ERROR.equals(intent.getAction())) {
                viewModel.setError(intent.getStringExtra(NetworkDispatcher.EXTRA_ERROR));
                currentMode = UnderExercisesViewModel.ViewModes.ERROR;
                currentMode.apply(viewModel);
            }
        }
    }


}
