package ru.extremefitness.fitness_trainer.ui.settings;

import android.content.Intent;
import android.os.Bundle;

import ru.extremefitness.fitness_trainer.ui.BaseActivity;

/**
 * Created by user on 27.09.2015.
 */

public class ScreenChangeMailAndPassword extends BaseActivity{

    public static final int REQUEST_MAIL = 1021;
    public static final int REQUEST_PASSWORD = 1022;
    public static final String EXTRA_REQUEST_CODE = "ScreenChangeMailAndPassword.EXTRA_REQUEST_CODE";
    public static final String EXTRA_DATA = "ScreenChangeMailAndPassword.EXTRA_DATA";

    private ScreenChangeMailAndPasswordViewModel viewModel;

    public static void startForResult(final BaseActivity activity, final int requestCode) {
        final Intent intent = new Intent(activity, ScreenChangeMailAndPassword.class);
        intent.putExtra(EXTRA_REQUEST_CODE, requestCode);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ScreenChangeMailAndPasswordViewModel(this, getIntent().getIntExtra(EXTRA_REQUEST_CODE, REQUEST_MAIL));
        setContentView(viewModel.getView());
    }
}
