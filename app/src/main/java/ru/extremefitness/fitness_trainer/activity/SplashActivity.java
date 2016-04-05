package ru.extremefitness.fitness_trainer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ru.extremefitness.fitness_trainer.R;
import ru.extremefitness.fitness_trainer.ui.MainActivity;
import ru.extremefitness.fitness_trainer.utils.SignOutHelper;

/**
 * Created by Osipova Ekaterina on 05.02.2016.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    public void finishSplash(View view) {
        if (SignOutHelper.isLogin(this)) {
            MainActivity.start(this);
            return;
        }
        Intent intent = new Intent(this, SignInOrSignUpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
