package ru.extremefitness.fitness_trainer.ui.login;

import android.content.Intent;
import android.os.Bundle;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.services.AccountService;

import ru.extremefitness.fitness_trainer.models.login.NetLogin;
import ru.extremefitness.fitness_trainer.network.ModelsEnum;
import ru.extremefitness.fitness_trainer.network.NetworkDispatcher;
import ru.extremefitness.fitness_trainer.ui.BaseActivity;

/**
 * Created: Krylov
 * Date: 22.09.2015
 * Time: 21:29
 */
public final class ScreenTwiterLoginActivity extends BaseLoginActivity {

    private TwitterLoginButton loginButton;

    public static void startForResult(final BaseActivity activity) {
        activity.startActivityForResult(new Intent(activity, ScreenTwiterLoginActivity.class), TWITTER_REQUEST_CODE);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        login();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    private void login() {
        loginButton = new TwitterLoginButton(this);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(final Result<TwitterSession> result) {
                final AccountService service = Twitter.getInstance().getApiClient().getAccountService();
                service.verifyCredentials(true, true, new Callback<User>() {
                    @Override
                    public void success(Result<User> result) {
                        if (null == result.data) {
                            finish();
                        } else {
                            final NetLogin netLogin = new NetLogin(result.data);
                            NetworkDispatcher.invoke(ModelsEnum.LOGIN.with(netLogin));
                        }
                    }

                    @Override
                    public void failure(TwitterException e) {
                        finish();
                    }
                });
            }

            @Override
            public void failure(TwitterException exception) {
//                 Do something on failure
                finish();
            }
        });
        loginButton.performClick();
    }

}
