package ru.extremefitness.fitness_trainer.activity;

import android.content.Intent;
import android.os.Bundle;

import ru.extremefitness.fitness_trainer.ui.MainActivity;
import ru.extremefitness.fitness_trainer.ui.login.BaseLoginActivity;
import ru.extremefitness.fitness_trainer.utils.SignOutHelper;
import ru.extremefitness.fitness_trainer.viewmodel.ExtremeLoginViewModel;

/**
 * Created by Comp on 14.09.2015.
 */
public final class ExtremeLoginActivity extends BaseLoginActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExtremeLoginViewModel viewModel = new ExtremeLoginViewModel(this);
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
