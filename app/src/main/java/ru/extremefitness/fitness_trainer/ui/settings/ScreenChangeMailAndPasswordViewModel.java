package ru.extremefitness.fitness_trainer.ui.settings;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import ru.extremefitness.fitness_trainer.R;
import ru.extremefitness.fitness_trainer.UserPreferences;
import ru.extremefitness.fitness_trainer.Utils;
import ru.extremefitness.fitness_trainer.models.MailAndPassword;
import ru.extremefitness.fitness_trainer.models.RootContainer;
import ru.extremefitness.fitness_trainer.network.ModelsEnum;
import ru.extremefitness.fitness_trainer.network.NetworkDispatcher;
import ru.extremefitness.fitness_trainer.ui.BaseActivity;
import ru.extremefitness.fitness_trainer.ui.BaseViewModel;
import ru.extremefitness.fitness_trainer.ui.widgets.SettingsListItem;

/**
 * Created by user on 27.09.2015.
 */
public class ScreenChangeMailAndPasswordViewModel extends BaseViewModel {

    private View mailContainer;
    private View passwordContainer;
    private SettingsListItem oldMailView;
    private SettingsListItem newMailView;
    private SettingsListItem passwordView;
    private final ViewModes currentMode;
    private Button emailSaveButton;
    private SettingsListItem oldPasswordView;
    private SettingsListItem newPasswordView;
    private Button passwordSaveButton;

    private enum ViewModes {
        MAIL {
            @Override
            void apply(ScreenChangeMailAndPasswordViewModel viewModel) {
                viewModel.showMail();
            }
        },
        PASSWORD {
            @Override
            void apply(ScreenChangeMailAndPasswordViewModel viewModel) {
                viewModel.showPassword();
            }
        },;

        abstract void apply(ScreenChangeMailAndPasswordViewModel viewModel);
    }

    private View rootView;


    protected ScreenChangeMailAndPasswordViewModel(final BaseActivity activity, final int requestCode) {
        super(activity);
        currentMode = requestCode == ScreenChangeMailAndPassword.REQUEST_MAIL ? ViewModes.MAIL : ViewModes.PASSWORD;

        currentMode.apply(this);
    }

    @Override
    protected void initViews() {
        rootView = View.inflate(activity, R.layout.screen_change_mail_or_password, null);
        mailContainer = rootView.findViewById(R.id.mail_container);
        passwordContainer = rootView.findViewById(R.id.password_container);

        oldMailView = (SettingsListItem) rootView.findViewById(R.id.old_mail);
        newMailView = (SettingsListItem) rootView.findViewById(R.id.new_email);
        passwordView = (SettingsListItem) rootView.findViewById(R.id.password);
        emailSaveButton = (Button) rootView.findViewById(R.id.email_save_button);

        emailSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = newMailView.getValue();

                final String passInPref = UserPreferences.getUserData(activity).getPassword();

                final String password;

                if (TextUtils.isEmpty(passInPref)) {
                    password = passwordView.getValue();
                } else {
                    password = Utils.getMd5Hash(passwordView.getValue());
                }

                if (!isValidEmail(email)) {
                    Toast.makeText(activity, "Введите корректный e-mail", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(passInPref)) {
                    Toast.makeText(activity, R.string.password_not_equals, Toast.LENGTH_LONG).show();
                    return;
                }

                NetworkDispatcher.invoke(ModelsEnum.LOGIN.with(
                                "new_email", email,
                                "password", Utils.getMd5Hash(passwordView.getValue()),
                                "id", UserPreferences.getUserData(activity).getId(),
                                "action", "update"),
                        new Response.Listener<RootContainer>() {
                            @Override
                            public void onResponse(RootContainer response) {
                                final MailAndPassword mailAndPassword = new MailAndPassword(email, passwordView.getValue());
                                final Intent intent = new Intent();
                                intent.putExtra(ScreenChangeMailAndPassword.EXTRA_DATA, mailAndPassword);
                                activity.setResult(BaseActivity.RESULT_OK, intent);
                                activity.finish();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(activity, "Ошибка сохранения email", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

        oldPasswordView = (SettingsListItem) rootView.findViewById(R.id.old_password);
        newPasswordView = (SettingsListItem) rootView.findViewById(R.id.new_password);
        passwordSaveButton = (Button) rootView.findViewById(R.id.password_save_button);
        passwordSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String passInPref = UserPreferences.getUserData(activity).getPassword();

                final String oldPassword;

                if (TextUtils.isEmpty(passInPref)) {
                    oldPassword = oldPasswordView.getValue();
                } else {
                    oldPassword = Utils.getMd5Hash(oldPasswordView.getValue());
                }


                if (!TextUtils.isEmpty(oldPassword) && !oldPassword.equals(passInPref)) {
                    Toast.makeText(activity, R.string.password_not_equals, Toast.LENGTH_LONG).show();
                    return;
                }

                NetworkDispatcher.invoke(ModelsEnum.LOGIN.with(
                                "password", oldPassword,
                                "new_password", Utils.getMd5Hash(newPasswordView.getValue()),
                                "id", UserPreferences.getUserData(activity).getId(),
                                "action", "update"),
                        new Response.Listener<RootContainer>() {
                            @Override
                            public void onResponse(RootContainer response) {
                                final MailAndPassword mailAndPassword = new MailAndPassword("", "");
                                final Intent intent = new Intent();
                                intent.putExtra(ScreenChangeMailAndPassword.EXTRA_DATA, mailAndPassword);
                                activity.setResult(BaseActivity.RESULT_OK, intent);
                                activity.finish();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(activity, "Ошибка сохранения password", Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        });

    }

    private void showMail() {
        mailContainer.setVisibility(View.VISIBLE);
        passwordContainer.setVisibility(View.GONE);
        final String email = UserPreferences.getUserData(activity).getEmail();
        if (TextUtils.isEmpty(email)) {
            rootView.findViewById(R.id.old_mail_container).setVisibility(View.GONE);
        } else {
            oldMailView.setValue(email);
        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private void showPassword() {
        mailContainer.setVisibility(View.GONE);
        passwordContainer.setVisibility(View.VISIBLE);
    }


    @Override
    public View getView() {
        return rootView;
    }
}
