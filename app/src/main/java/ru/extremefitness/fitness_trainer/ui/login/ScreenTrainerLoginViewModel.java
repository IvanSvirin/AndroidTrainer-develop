package ru.extremefitness.fitness_trainer.ui.login;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ru.extremefitness.fitness_trainer.R;
import ru.extremefitness.fitness_trainer.Utils;
import ru.extremefitness.fitness_trainer.network.ModelsEnum;
import ru.extremefitness.fitness_trainer.network.NetworkDispatcher;
import ru.extremefitness.fitness_trainer.network.ParamsKeys;
import ru.extremefitness.fitness_trainer.ui.BaseActivity;
import ru.extremefitness.fitness_trainer.ui.BaseViewModel;

/**
 * Created: Krylov
 * Date: 22.09.2015
 * Time: 0:33
 */
public class ScreenTrainerLoginViewModel extends BaseViewModel implements View.OnClickListener{

    private View rootView;
    private EditText email;
    private EditText password;
    private EditText firstName;
    private EditText lastName;
    private Button signUpButton;
    private TextView signUpText;

    private LoginModes currentMode = LoginModes.AUTHORIZATION;

    private enum LoginModes {
        REGISTRATION {
            @Override
            void showViews(final ScreenTrainerLoginViewModel viewModel) {
                viewModel.showRegistrationView();
            }
        },
        AUTHORIZATION {
            @Override
            void showViews(final ScreenTrainerLoginViewModel viewModel) {
                viewModel.showLoginView();
            }
        };
        abstract void showViews(final ScreenTrainerLoginViewModel viewModel);
    }

    protected ScreenTrainerLoginViewModel(final BaseActivity activity) {
        super(activity);

        signUpButton.setOnClickListener(this);
        signUpText.setOnClickListener(this);
        currentMode.showViews(this);
    }

    @Override
    protected void initViews() {
        rootView = View.inflate(activity, R.layout.screen_trainer_login, null);
        email = (EditText) rootView.findViewById(R.id.email);
        password = (EditText) rootView.findViewById(R.id.password);
        firstName = (EditText) rootView.findViewById(R.id.first_name);
        lastName = (EditText) rootView.findViewById(R.id.last_name);
        signUpButton = (Button) rootView.findViewById(R.id.sign_up_button);
        signUpText = (TextView) rootView.findViewById(R.id.change_mode_text);
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
        } else if (R.id.change_mode_text == id) {
            currentMode = LoginModes.REGISTRATION;
            currentMode.showViews(this);
        }
    }

    private void signUp() {
        if (checkViews()) {
            ModelsEnum params = ModelsEnum.LOGIN;
            params.with(
                    ParamsKeys.EMAIL.toString(), email.getText().toString(),
                    ParamsKeys.PASSWORD.toString(), getPassword()
            );

            if (LoginModes.AUTHORIZATION == currentMode) {
                params.with(
                    ParamsKeys.ACTION.toString(), "auth"
                );
            } else {
                params.with(
                    ParamsKeys.FIRST_NAME.toString(), firstName.getText().toString(),
                    ParamsKeys.LAST_NAME.toString(), lastName.getText().toString()
                );
            }

            NetworkDispatcher.invoke(params);
        }
    }

    private String getPassword() {
        return Utils.getMd5Hash(password.getText().toString());
    }

    private boolean checkViews() {

        final String emailText = email.getText().toString();
        final String passwordText = password.getText().toString();
        final String firstNameText = firstName.getText().toString();
        final String lastNameText = lastName.getText().toString();

        boolean good = true;

        if (TextUtils.isEmpty(emailText)) {
            showError(email);
            good = false;
        }

        if (TextUtils.isEmpty(passwordText)) {
            showError(password);
            good = false;
        }

        if (LoginModes.REGISTRATION == currentMode) {
            if (TextUtils.isEmpty(firstNameText)) {
                showError(firstName);
                good = false;
            }

            if (TextUtils.isEmpty(lastNameText)) {
                showError(lastName);
                good = false;
            }
        }

        return good;
    }

    private void showError(final EditText view) {
        view.setError(activity.getString(R.string.error));
    }

    private void showRegistrationView() {
        firstName.setVisibility(View.VISIBLE);
        lastName.setVisibility(View.VISIBLE);
        signUpText.setVisibility(View.GONE);
        signUpButton.setText(R.string.sign_up);
    }

    private void showLoginView() {
        clear();
        firstName.setVisibility(View.GONE);
        lastName.setVisibility(View.GONE);
        signUpText.setVisibility(View.VISIBLE);
        signUpButton.setText(R.string.sign_in);
    }

    private void clear() {
        email.setText("");
        password.setText("");
        firstName.setText("");
        lastName.setText("");
    }

    boolean onBackPressed() {
        if (LoginModes.REGISTRATION == currentMode) {
            currentMode = LoginModes.AUTHORIZATION;
            currentMode.showViews(this);
            return true;
        }

        return false;
    }
}
