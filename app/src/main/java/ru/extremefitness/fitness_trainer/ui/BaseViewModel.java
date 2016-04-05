package ru.extremefitness.fitness_trainer.ui;

import android.view.View;

/**
 * Created by Comp on 15.09.2015.
 */
public abstract class BaseViewModel {

    protected BaseActivity activity;

    protected BaseViewModel(final BaseActivity activity) {
        this.activity = activity;
        initViews();
    }

    protected abstract void initViews();

    public abstract View getView();
}
