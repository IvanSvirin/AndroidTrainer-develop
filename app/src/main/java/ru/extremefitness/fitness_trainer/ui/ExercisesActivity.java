package ru.extremefitness.fitness_trainer.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.extremefitness.fitness_trainer.network.ModelsEnum;
import ru.extremefitness.fitness_trainer.network.NetworkDispatcher;


//Активность для загрузки групп мышц

public class ExercisesActivity extends AppCompatActivity {

    private ExercisesViewModel viewModel;
    private ExercisesViewModel.ViewModes currentMode = ExercisesViewModel.ViewModes.CONTENT;


    public static void start(final Context context) {
        final Intent intent = new Intent(context, ExercisesActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        viewModel = new ExercisesViewModel(this);
        setContentView(viewModel.getView());

        if (savedInstanceState == null) {
            loadList();
        }


    }


    void loadList() {
        NetworkDispatcher.invoke(ModelsEnum.GET_GYM.with("router", "get_gym"));
        currentMode = ExercisesViewModel.ViewModes.PROGRESS;
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
                viewModel.setContent(NetworkDispatcher.getResult());
                currentMode = ExercisesViewModel.ViewModes.CONTENT;
                currentMode.apply(viewModel);
            } else if (NetworkDispatcher.ACTION_LOAD_ERROR.equals(intent.getAction())) {
                viewModel.setError(intent.getStringExtra(NetworkDispatcher.EXTRA_ERROR));
                currentMode = ExercisesViewModel.ViewModes.ERROR;
                currentMode.apply(viewModel);
            }
        }
    }


}
