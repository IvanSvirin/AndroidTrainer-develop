package ru.extremefitness.fitness_trainer.activity;

import android.content.Context;
import android.os.Bundle;

import ru.extremefitness.fitness_trainer.ui.BaseActivity;
import ru.extremefitness.fitness_trainer.viewmodel.SignInOrSignUpViewModel;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Osipova Ekaterina on 05.02.2016.
 */
public class SignInOrSignUpActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SignInOrSignUpViewModel viewModel = new SignInOrSignUpViewModel(this);
        setContentView(viewModel.getView());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
