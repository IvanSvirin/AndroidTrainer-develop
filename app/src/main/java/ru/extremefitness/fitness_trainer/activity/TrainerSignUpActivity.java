package ru.extremefitness.fitness_trainer.activity;

import android.content.Intent;
import android.os.Bundle;

import ru.extremefitness.fitness_trainer.ui.BaseActivity;
import ru.extremefitness.fitness_trainer.ui.login.BaseLoginActivity;
import ru.extremefitness.fitness_trainer.utils.SignOutHelper;
import ru.extremefitness.fitness_trainer.viewmodel.TrainerSignUpViewModel;

/**
 * Created by Osipova Ekaterina on 05.02.2016.
 */
public class TrainerSignUpActivity extends BaseLoginActivity {

    public static void startForResult(final BaseActivity activity) {
        activity.startActivityForResult(new Intent(activity, TrainerSignUpActivity.class), TRAINER_REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TrainerSignUpViewModel viewModel = new TrainerSignUpViewModel(this);
        setContentView(viewModel.getView());
    }

    @Override
    public void onNetworkDispatcherResult(int resultCode, Intent data) {
        saveLoginData(TRAINER_REQUEST_CODE, resultCode, data);
        if (SignOutHelper.isLogin(this)) {
            startActivity(new Intent(this, AboutActivity.class));
        }
    }
}
