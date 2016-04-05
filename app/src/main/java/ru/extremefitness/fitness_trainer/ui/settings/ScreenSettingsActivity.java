package ru.extremefitness.fitness_trainer.ui.settings;

import android.content.Intent;
import android.os.Bundle;

import ru.extremefitness.fitness_trainer.ui.BaseActivity;

/**
 * Created: Krylov
 * Date: 24.09.2015
 * Time: 9:54
 */
public class ScreenSettingsActivity extends BaseActivity {

    private ScreenSettingsViewModel viewModel;

    public static void start(final BaseActivity baseActivity) {
        baseActivity.startActivity(new Intent(baseActivity, ScreenSettingsActivity.class));
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ScreenSettingsViewModel(this);
        setContentView(viewModel.getView());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.saveChanges();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == BaseActivity.RESULT_OK) {
            if (requestCode == ScreenChangeMailAndPassword.REQUEST_MAIL) {
                viewModel.setMail(data);
            } else if (requestCode == ScreenChangeMailAndPassword.REQUEST_PASSWORD) {
                viewModel.setPassword(data);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
