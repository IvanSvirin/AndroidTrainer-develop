package ru.extremefitness.fitness_trainer.ui.login;

import android.content.Intent;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import ru.extremefitness.fitness_trainer.models.login.NetLogin;
import ru.extremefitness.fitness_trainer.network.ModelsEnum;
import ru.extremefitness.fitness_trainer.network.NetworkDispatcher;
import ru.extremefitness.fitness_trainer.ui.BaseActivity;

/**
 * Created: Krylov
 * Date: 20.09.2015
 * Time: 22:21
 */
public class ScreenFacebookLoginActivity extends BaseLoginActivity {

    private CallbackManager callbackManager;
    private String id;
    private String photo;
    private String first_name;
    private String last_name;
    private String birthday;
    private String gender;

    public static void startForResult(final BaseActivity activity) {
        activity.startActivityForResult(new Intent(activity, ScreenFacebookLoginActivity.class), FACEBOOK_REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        login();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void login() {

        callbackManager = CallbackManager.Factory.create();
        final LoginButton loginButton = new LoginButton(this);
        loginButton.setReadPermissions("user_friends", "email", "user_birthday");

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getIdAndName();
            }

            @Override
            public void onCancel() {
                // App code
                finish();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                finish();
            }
        });
        loginButton.performClick();
    }

    private void getIdAndName() {
        final Bundle bundle = new Bundle();
        bundle.putString("fields", "id,birthday,first_name,gender,last_name,picture");
        // App code
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me",
                bundle,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        final NetLogin netLogin = new NetLogin(response);
                        NetworkDispatcher.invoke(ModelsEnum.LOGIN.with(netLogin));
                    }
                }
        ).executeAsync();
    }

    public static void logout(final BaseActivity activity) {
        new LoginButton(activity).performClick();
    }
}
