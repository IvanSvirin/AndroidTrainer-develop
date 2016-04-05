package ru.extremefitness.fitness_trainer.ui.uitest;

import android.os.Bundle;
import android.view.View;

import ru.extremefitness.fitness_trainer.ui.BaseActivity;
import ru.extremefitness.fitness_trainer.ui.cycle.LifeCycle;
import ru.extremefitness.fitness_trainer.utils.saveinstance.InstanceSaver;
import ru.extremefitness.fitness_trainer.utils.saveinstance.SaveInBundle;

/**
 * User: krilov
 * Date: 03.02.2016
 * Time: 15:21
 */
public class UITestViewModel extends UiTestLayoutAutoGenerate {

	private InstanceSaver saver;

	@SaveInBundle
	private int someValueToSave;

	UITestViewModel(BaseActivity context, Bundle state) {
		super(context);

		setSomeText("someText");
		setMakeVisibleOnClickListener();
		setMakeInvisibleOnClickListener();

		saver = new InstanceSaver(this);
		saver.restore(state);

		context.addOnSaveInstanceListener(new LifeCycle.OnSaveInstanceListener() {
			@Override
			public void save(Bundle out) {
				saver.saveInstance(out);
			}
		});
		setLabelVisibility(someValueToSave);
	}

	public void setSomeText(String text) {
		setLabelText(text);
	}

	public void setMakeVisibleOnClickListener() {
		makeVisibleOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				someValueToSave = View.VISIBLE;
				setLabelVisibility(someValueToSave);
			}
		});
	}

	public void setMakeInvisibleOnClickListener() {
		makeInvisibleOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				someValueToSave = View.INVISIBLE;
				setLabelVisibility(someValueToSave);
			}
		});
	}
}
