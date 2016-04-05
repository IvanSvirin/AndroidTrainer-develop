package ru.extremefitness.fitness_trainer.viewmodel;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import ru.extremefitness.fitness_trainer.R;
import ru.extremefitness.fitness_trainer.activity.SignInActivity;
import ru.extremefitness.fitness_trainer.activity.TrainerSignUpActivity;
import ru.extremefitness.fitness_trainer.ui.BaseActivity;
import ru.extremefitness.fitness_trainer.ui.BaseViewModel;

/**
 * Created by Osipova Ekaterina on 05.02.2016.
 */
public class SignInOrSignUpViewModel extends BaseViewModel implements View.OnClickListener {

    private View rootView;
    private Button signInButton;
    private Button signUpButton;

    public SignInOrSignUpViewModel(BaseActivity activity) {
        super(activity);
        signInButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
    }

    @Override
    protected void initViews() {
        rootView = View.inflate(activity, R.layout.activity_signin_or_signup, null);
        signInButton = (Button) rootView.findViewById(R.id.btn_sign_in);
        signUpButton = (Button) rootView.findViewById(R.id.btn_sign_up);
    }

    @Override
    public View getView() {
        return rootView;
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();

        if (id == R.id.btn_sign_in) {
            signIn();
        }
        if (id == R.id.btn_sign_up) {
            signUp();
        }
    }

    private void signIn() {
        activity.startActivity(new Intent(activity, SignInActivity.class));
    }

    private void signUp() {
        activity.startActivity(new Intent(activity, TrainerSignUpActivity.class));
//        activity.startActivity(new Intent(activity, ProgramsActivity.class));
    }
}
