package ru.extremefitness.fitness_trainer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.appevents.AppEventsLogger;

import ru.extremefitness.fitness_trainer.UserPreferences;
import ru.extremefitness.fitness_trainer.models.login.LoginRequestContainer;
import ru.extremefitness.fitness_trainer.models.login.NetLogin;
import ru.extremefitness.fitness_trainer.ui.BaseActivity;
import ru.extremefitness.fitness_trainer.ui.MainActivity;
import ru.extremefitness.fitness_trainer.ui.login.BaseLoginActivity;
import ru.extremefitness.fitness_trainer.utils.SignOutHelper;
import ru.extremefitness.fitness_trainer.viewmodel.SignInViewModel;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class SignInActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppEventsLogger.activateApp(this);

        SignInViewModel viewModel = new SignInViewModel(this);
        setContentView(viewModel.getView());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppEventsLogger.deactivateApp(this);
    }

    // TODO: change startActivityForResult to startActivity in viewModel
    @Override
    protected void onResume() {
        super.onResume();
        if (SignOutHelper.isLogin(this)) {
            MainActivity.start(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == BaseActivity.RESULT_OK) {

            final LoginRequestContainer.UserData result =
                    (LoginRequestContainer.UserData) data.getSerializableExtra(LoginRequestContainer.EXTRA_LOGIN_RESULT);

            if (null != result) {
                if (BaseLoginActivity.EXTREME_LOGIN_REQUEST == requestCode) {
                    result.setAuthMethod(NetLogin.SocialNet.EXTREME.ordinal());
                } else if (BaseLoginActivity.TRAINER_REQUEST_CODE == requestCode) {
                    result.setAuthMethod(NetLogin.SocialNet.TRAINER.ordinal());
                }
                UserPreferences.saveUserData(this, result);
            }
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
