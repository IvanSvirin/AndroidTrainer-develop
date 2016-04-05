package ru.extremefitness.fitness_trainer.activity;

import android.content.Intent;
import android.os.Bundle;

import ru.extremefitness.fitness_trainer.ui.MainActivity;
import ru.extremefitness.fitness_trainer.ui.login.BaseLoginActivity;
import ru.extremefitness.fitness_trainer.utils.SignOutHelper;
import ru.extremefitness.fitness_trainer.viewmodel.TrainerSignInViewModel;

/**
 * Created by Osipova Ekaterina on 05.02.2016.
 */
public class TrainerSignInActivity extends BaseLoginActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TrainerSignInViewModel viewModel = new TrainerSignInViewModel(this);
        setContentView(viewModel.getView());
    }

    @Override
    public void onNetworkDispatcherResult(int resultCode, Intent data) {
        saveLoginData(TRAINER_REQUEST_CODE, resultCode, data);
        if (SignOutHelper.isLogin(this)) {
            MainActivity.start(this);
        }
    }
}

