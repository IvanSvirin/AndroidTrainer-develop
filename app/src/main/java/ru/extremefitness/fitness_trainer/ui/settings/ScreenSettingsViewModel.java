package ru.extremefitness.fitness_trainer.ui.settings;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;

import com.android.volley.Response;

import java.util.Calendar;

import ru.extremefitness.fitness_trainer.ExtremeTrainerApplication;
import ru.extremefitness.fitness_trainer.R;
import ru.extremefitness.fitness_trainer.UserPreferences;
import ru.extremefitness.fitness_trainer.Utils;
import ru.extremefitness.fitness_trainer.models.MailAndPassword;
import ru.extremefitness.fitness_trainer.models.RootContainer;
import ru.extremefitness.fitness_trainer.models.login.LoginRequestContainer;
import ru.extremefitness.fitness_trainer.models.login.NetLogin;
import ru.extremefitness.fitness_trainer.network.ModelsEnum;
import ru.extremefitness.fitness_trainer.network.NetworkDispatcher;
import ru.extremefitness.fitness_trainer.ui.BaseActivity;
import ru.extremefitness.fitness_trainer.ui.BaseViewModel;
import ru.extremefitness.fitness_trainer.ui.MainActivity;
import ru.extremefitness.fitness_trainer.ui.widgets.SettingsListItem;

/**
 * Created: Krylov
 * Date: 24.09.2015
 * Time: 9:55
 */
public class ScreenSettingsViewModel extends BaseViewModel {

    private View rootView;
    private SettingsListItem emailItem;
    private SettingsListItem passwordItem;
    private SettingsListItem firstNameItem;
    private SettingsListItem lastNameItem;
    private SettingsListItem genderItem;
    private SettingsListItem birthdayItem;
    private SettingsListItem weightItem;
    private SettingsListItem heightItem;
    private SettingsListItem pobfItem;

    protected ScreenSettingsViewModel(final BaseActivity activity) {
        super(activity);
    }

    @Override
    protected void initViews() {
        rootView = View.inflate(activity, R.layout.screen_settings, null);
        emailItem = (SettingsListItem) rootView.findViewById(R.id.email);
        passwordItem = (SettingsListItem) rootView.findViewById(R.id.password);
        SettingsListItem authorityItem = (SettingsListItem) rootView.findViewById(R.id.authorization);
        firstNameItem = (SettingsListItem) rootView.findViewById(R.id.first_name);
        lastNameItem = (SettingsListItem) rootView.findViewById(R.id.last_name);
        genderItem = (SettingsListItem) rootView.findViewById(R.id.gender);
        birthdayItem = (SettingsListItem) rootView.findViewById(R.id.birthday);
        weightItem = (SettingsListItem) rootView.findViewById(R.id.weight);
        heightItem = (SettingsListItem) rootView.findViewById(R.id.height);
        pobfItem = (SettingsListItem) rootView.findViewById(R.id.pobf);

        final LoginRequestContainer.UserData userData = UserPreferences.getUserData(activity);

        emailItem.setValue(userData.getEmail());

        passwordItem.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);

        passwordItem.setValue(userData.getPassword());

        emailItem.setValuesListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    ScreenChangeMailAndPassword.startForResult(activity, ScreenChangeMailAndPassword.REQUEST_MAIL);
                }
                return false;
            }
        });

        passwordItem.setValuesListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    ScreenChangeMailAndPassword.startForResult(activity, ScreenChangeMailAndPassword.REQUEST_PASSWORD);
                }
                return false;
            }
        });

        authorityItem.setValue(NetLogin.SocialNet.getSocialNet(userData.getAuthMethod()).getName(activity));

        firstNameItem.setValue(userData.getFirstName());
        lastNameItem.setValue(userData.getLastName());
        emailItem.setValue(userData.getEmail());
        passwordItem.setValue(userData.getPassword());

        genderItem.setSpinner(Integer.parseInt(userData.getGender()),
                NetLogin.Gender.NOT_DETERMINED.getGender(activity),
                NetLogin.Gender.FEMALE.getGender(activity),
                NetLogin.Gender.MALE.getGender(activity));

        birthdayItem.setValue(userData.getBirthday());
        birthdayItem.setValuesListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    final Calendar calendar = Calendar.getInstance();
                    DatePickerDialog datePickerDialog = new DatePickerDialog(activity,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    birthdayItem.setValue(year + "-" + monthOfYear + "-" + dayOfMonth);
                                }
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.show();
                }
                return false;
            }
        });

        weightItem.setValue(userData.getWeight());
        weightItem.setValuesListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    Utils.showDialogPicker(activity, Utils.WEIGHT, new Utils.NumberPickerResultListerner() {
                        @Override
                        public void onResult(String result) {
                            weightItem.setValue(result);
                        }
                    });
                }
                return false;
            }
        });

        heightItem.setValue(userData.getHeight());
        heightItem.setValuesListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    Utils.showDialogPicker(activity, Utils.HEIGHT, new Utils.NumberPickerResultListerner() {
                        @Override
                        public void onResult(String result) {
                            heightItem.setValue(result);
                        }
                    });
                }
                return false;
            }
        });

        pobfItem.setValue(userData.getPobf());

    }

    @Override
    public View getView() {
        return rootView;
    }

    /*public void saveChanges() {
        final Context context = activity;
        new Thread(new Runnable() {
            @Override
            public void run() {

                LoginRequestContainer.UserData userData = UserPreferences.getUserData(context);


                NetworkDispatcher.invoke(ModelsEnum.LOGIN.with(userData).with("action", "update"),
                        new Response.Listener<RootContainer>() {
                            @Override
                            public void onResponse(RootContainer response) {
                                if (response.isSuccess()) {
                                    final LoginRequestContainer.UserData userData = response.getData();
                                    UserPreferences.saveUserData(context, userData);
                                    MainActivity.sendUpdateUser(context);
                                }
                            }
                        }, null);
            }
        }).start();
    }*/


    /*public void saveMailAndPassword() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                final Context context = ExtremeTrainerApplication.getInstance().getApplicationContext();
                NetworkDispatcher.invoke(ModelsEnum.UPDATE_SETTINGS.with(
                                "email", UserPreferences.getUserData(context).getEmail(),
                                "password", passwordItem.getValue(),
                                "id", UserPreferences.getUserData(context).getId(),
                                "action", "update"
                        ),
                        new Response.Listener<RootContainer>() {
                            @Override
                            public void onResponse(RootContainer response) {
                                if (response.isSuccess()) {
                                    final LoginRequestContainer.UserData userData = response.getData();
                                    UserPreferences.saveUserData(context, userData);
                                    MainActivity.sendUpdateUser(context);
                                }
                            }
                        }, null);
            }
        }).start();
    }*/

    public void saveChanges() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Context context = ExtremeTrainerApplication.getInstance().getApplicationContext();
                NetworkDispatcher.invoke(ModelsEnum.UPDATE_SETTINGS.with(
                                "id", UserPreferences.getUserData(context).getId(),
                                "gender", NetLogin.Gender.getInByString(context, genderItem.getValue()),
                                "first_name", firstNameItem.getValue(),
                                "last_name", lastNameItem.getValue(),
                                "birthday", birthdayItem.getValue(),
                                "weight", weightItem.getValue().replaceAll("\\D+", ""),
                                "height", heightItem.getValue().replaceAll("\\D+", ""),
                                "pobf", pobfItem.getValue(),
                                "action", "update"
                        ),
                        new Response.Listener<RootContainer>() {
                            @Override
                            public void onResponse(RootContainer response) {
                                if (response.isSuccess()) {
                                    final String avatar = UserPreferences.getUserData(context).getAvatar();

                                    final LoginRequestContainer.UserData userData = response.getData();
                                    userData.setAvatar(avatar);
                                    UserPreferences.saveUserData(context, userData);
                                    MainActivity.sendUpdateUser(context);
                                }
                            }
                        }, null);
            }
        }).start();
    }


    public void setMail(final Intent mail) {
        final MailAndPassword mailAndPassword = (MailAndPassword) mail.getSerializableExtra(ScreenChangeMailAndPassword.EXTRA_DATA);
        emailItem.setValue(mailAndPassword.getMail());
        passwordItem.setValue(mailAndPassword.getPassword());
    }

    public void setPassword(final Intent password) {
        final MailAndPassword mailAndPassword = (MailAndPassword) password.getSerializableExtra(ScreenChangeMailAndPassword.EXTRA_DATA);
        passwordItem.setValue(mailAndPassword.getPassword());
    }
}
