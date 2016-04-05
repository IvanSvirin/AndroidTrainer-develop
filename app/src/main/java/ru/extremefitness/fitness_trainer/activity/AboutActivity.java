package ru.extremefitness.fitness_trainer.activity;

import android.os.Bundle;

import ru.extremefitness.fitness_trainer.ui.login.BaseLoginActivity;
import ru.extremefitness.fitness_trainer.viewmodel.AboutViewModel;

/**
 * Created by Osipova Ekaterina on 08.02.2016.
 */
public class AboutActivity extends BaseLoginActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AboutViewModel viewModel = new AboutViewModel(this);
        setContentView(viewModel.getView());
    }
}
