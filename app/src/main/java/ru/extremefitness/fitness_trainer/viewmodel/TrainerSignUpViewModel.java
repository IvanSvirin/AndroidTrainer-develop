package ru.extremefitness.fitness_trainer.viewmodel;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ru.extremefitness.fitness_trainer.R;
import ru.extremefitness.fitness_trainer.Utils;
import ru.extremefitness.fitness_trainer.network.ModelsEnum;
import ru.extremefitness.fitness_trainer.network.NetworkDispatcher;
import ru.extremefitness.fitness_trainer.network.ParamsKeys;
import ru.extremefitness.fitness_trainer.ui.BaseActivity;
import ru.extremefitness.fitness_trainer.ui.BaseViewModel;

/**
 * Created by Osipova Ekaterina on 05.02.2016.
 */
public class TrainerSignUpViewModel extends BaseViewModel implements View.OnClickListener {

    private View rootView;
    private EditText name;
    private EditText email;
    private EditText password;
    private EditText passwordConfirm;
    private Button signUpButton;

    public TrainerSignUpViewModel(final BaseActivity activity) {
        super(activity);

        signUpButton.setOnClickListener(this);
    }

    @Override
    protected void initViews() {
        rootView = View.inflate(activity, R.layout.activity_trainer_sign_up, null);
        name = (EditText) rootView.findViewById(R.id.name);
        email = (EditText) rootView.findViewById(R.id.email);
        password = (EditText) rootView.findViewById(R.id.password);
        passwordConfirm = (EditText) rootView.findViewById(R.id.password_confirm);
        signUpButton = (Button) rootView.findViewById(R.id.sign_up_button);
    }

    @Override
    public View getView() {
        return rootView;
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();

        if (R.id.sign_up_button == id) {
            signUp();
        }
    }

    private void signUp() {
        if (checkViews()) {
            ModelsEnum params = ModelsEnum.LOGIN;
            params.with(
                    ParamsKeys.EMAIL.toString(), email.getText().toString(),
                    ParamsKeys.PASSWORD.toString(), getPassword(),
                    ParamsKeys.FIRST_NAME.toString(), name.getText().toString(),
                    ParamsKeys.LAST_NAME.toString(), passwordConfirm.getText().toString()
            );
            NetworkDispatcher.invoke(params);
        }
    }

    private String getPassword() {
        return Utils.getMd5Hash(password.getText().toString());
    }

    private boolean checkViews() {

        if (TextUtils.isEmpty(name.getText().toString())) {
            showError(name);
            return false;
        }

        if (TextUtils.isEmpty(email.getText().toString())) {
            showError(email);
            return false;
        }

        if (TextUtils.isEmpty(password.getText().toString())) {
            showError(password);
            return false;
        }

        if (TextUtils.isEmpty(passwordConfirm.getText().toString())) {
            showError(passwordConfirm);
            return false;
        }

        return true;
    }

    private void showError(final EditText view) {
        view.setError(activity.getString(R.string.error));
    }
}