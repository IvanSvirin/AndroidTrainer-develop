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
public class TrainerSignInViewModel extends BaseViewModel implements View.OnClickListener{

    private View rootView;
    private EditText email;
    private EditText password;
    private Button signUpButton;

    public TrainerSignInViewModel(final BaseActivity activity) {
        super(activity);

        signUpButton.setOnClickListener(this);
    }

    @Override
    protected void initViews() {
        rootView = View.inflate(activity, R.layout.activity_trainer_sign_in, null);
        email = (EditText) rootView.findViewById(R.id.email);
        password = (EditText) rootView.findViewById(R.id.password);
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
                    ParamsKeys.ACTION.toString(), "auth"
            );

            NetworkDispatcher.invoke(params);
        }
    }

    private String getPassword() {
        return Utils.getMd5Hash(password.getText().toString());
    }

    private boolean checkViews() {

        if (TextUtils.isEmpty(email.getText().toString())) {
            showError(email);
            return false;
        }

        if (TextUtils.isEmpty(password.getText().toString())) {
            showError(password);
            return false;
        }

        return true;
    }

    private void showError(final EditText view) {
        view.setError(activity.getString(R.string.error));
    }
}

