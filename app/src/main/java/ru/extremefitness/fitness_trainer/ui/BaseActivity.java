package ru.extremefitness.fitness_trainer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import ru.extremefitness.fitness_trainer.R;
import ru.extremefitness.fitness_trainer.UserPreferences;
import ru.extremefitness.fitness_trainer.activity.SignInOrSignUpActivity;
import ru.extremefitness.fitness_trainer.models.login.NetLogin;
import ru.extremefitness.fitness_trainer.ui.cycle.LifeCycle;
import ru.extremefitness.fitness_trainer.ui.login.ScreenFacebookLoginActivity;
import ru.extremefitness.fitness_trainer.utils.SignOutHelper;

/**
 * Created: Krylov
 * Date: 17.09.2015
 * Time: 1:21
 */
public class BaseActivity extends AppCompatActivity implements LifeCycle {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        signOut();
        return true;
    }

    private void signOut() {
        SignOutHelper.signOut(this);
//        signOutFacebook();
        startHomeActivity();
    }

    private void signOutFacebook() {
        if (NetLogin.SocialNet.FACEBOOK == NetLogin.SocialNet.getSocialNet(UserPreferences.getUserData(this).getAuthMethod())) {
            ScreenFacebookLoginActivity.logout(this);
        }
    }

    private void startHomeActivity() {
        // TODO: HomeActivity? SignInOrSignUpActivity or SplashActivity
        final Intent loginIntent = new Intent(this, SignInOrSignUpActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
    }

    /*LifeCycle implementation*/

    private static final int CAPACITY = 2;
    private final ArrayList<OnResumeListener> onResumeListeners = new ArrayList<>(CAPACITY);
    private final ArrayList<OnPauseListener> onPauseListeners = new ArrayList<>(CAPACITY);
    private final ArrayList<OnSaveInstanceListener> onSaveInstanceListeners = new ArrayList<>(CAPACITY);

    @Override
    protected void onResume() {
        super.onResume();
        for (final OnResumeListener listener : onResumeListeners) {
            listener.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (final OnPauseListener listener : onPauseListeners) {
            listener.onPause();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        for (final OnSaveInstanceListener listener : onSaveInstanceListeners) {
            listener.save(outState);
        }
    }

    @Override
    public void addOnResumeListener(final OnResumeListener listener) {
        onResumeListeners.add(listener);
    }

    @Override
    public void addOnPauseListener(final OnPauseListener listener) {
        onPauseListeners.add(listener);
    }

    @Override
    public void addOnDestroyListener(final OnDestroyListener listener) {
        //not implements yet
    }

    @Override
    public void addOnSaveInstanceListener(OnSaveInstanceListener listener) {
        onSaveInstanceListeners.add(listener);
    }
}
