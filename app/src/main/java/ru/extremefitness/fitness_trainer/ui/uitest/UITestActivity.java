package ru.extremefitness.fitness_trainer.ui.uitest;

import android.os.Bundle;

import ru.extremefitness.fitness_trainer.ui.BaseActivity;

/**
 * User: krilov
 * Date: 03.02.2016
 * Time: 15:21
 */
public class UITestActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		UITestViewModel viewModel = new UITestViewModel(this, savedInstanceState);
		setContentView(viewModel.getView());

	}
}
