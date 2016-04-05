package ru.extremefitness.fitness_trainer.viewmodel;

import android.content.Intent;
import android.view.View;

import ru.extremefitness.fitness_trainer.R;
import ru.extremefitness.fitness_trainer.activity.ExtremeLoginActivity;
import ru.extremefitness.fitness_trainer.activity.TrainerSignInActivity;
import ru.extremefitness.fitness_trainer.ui.BaseActivity;
import ru.extremefitness.fitness_trainer.ui.BaseViewModel;
import ru.extremefitness.fitness_trainer.ui.login.ScreenFacebookLoginActivity;
import ru.extremefitness.fitness_trainer.ui.login.ScreenGooglePlusLoginActivity;
import ru.extremefitness.fitness_trainer.ui.login.ScreenTwiterLoginActivity;
import ru.extremefitness.fitness_trainer.ui.login.ScreenVkLoginActivity;

/**
 * Created by user on 18.09.2015.
 */
public class SignInViewModel extends BaseViewModel implements View.OnClickListener {

    private View rootView;

    public SignInViewModel(BaseActivity activity) {
        super(activity);
    }

    @Override
    protected void initViews() {
        rootView = View.inflate(activity, R.layout.screen_login, null);

        rootView.findViewById(R.id.login_vk).setOnClickListener(this);
        rootView.findViewById(R.id.login_facebook).setOnClickListener(this);
        rootView.findViewById(R.id.login_twitter).setOnClickListener(this);
        rootView.findViewById(R.id.login_google).setOnClickListener(this);
        rootView.findViewById(R.id.login_extreme).setOnClickListener(this);
        rootView.findViewById(R.id.login_trainer).setOnClickListener(this);
    }

    @Override
    public View getView() {
        return rootView;
    }

    @Override
    public void onClick(View v) {
        final int viewId = v.getId();

        switch (viewId) {

            case R.id.login_vk:
                ScreenVkLoginActivity.startForResult(activity);
                break;
            case R.id.login_facebook:
                ScreenFacebookLoginActivity.startForResult(activity);
                break;
            case R.id.login_twitter:
                ScreenTwiterLoginActivity.startForResult(activity);
                break;
            case R.id.login_google:
                ScreenGooglePlusLoginActivity.startForResult(activity);
                break;
            case R.id.login_extreme:
                activity.startActivity(new Intent(activity, ExtremeLoginActivity.class));
                break;
            case R.id.login_trainer:
                activity.startActivity(new Intent(activity, TrainerSignInActivity.class));
                break;
        }

//        activity.startActivity(new Intent(activity, ProgramsActivity.class));
    }
}
