package ru.extremefitness.fitness_trainer.ui.login;

import android.content.Intent;
import android.os.Bundle;

import ru.extremefitness.fitness_trainer.ui.BaseActivity;

/**
 * Created: Krylov
 * Date: 22.09.2015
 * Time: 0:32
 */
public class ScreenTrainerLoginActivity extends BaseLoginActivity {

    private ScreenTrainerLoginViewModel viewModel;

    public static void startForResult(final BaseActivity activity) {
        activity.startActivityForResult(new Intent(activity, ScreenTrainerLoginActivity.class), TRAINER_REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ScreenTrainerLoginViewModel(this);
        setContentView(viewModel.getView());
    }

    @Override
    public void onBackPressed() {
        if(false == viewModel.onBackPressed()) {
            super.onBackPressed();
        }
    }

}
