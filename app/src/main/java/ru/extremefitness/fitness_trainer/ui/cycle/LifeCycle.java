package ru.extremefitness.fitness_trainer.ui.cycle;

import android.os.Bundle;

/**
 * User: krilov
 * Date: 04.02.2016
 * Time: 10:06
 */
public interface LifeCycle {
	void addOnResumeListener(final OnResumeListener listener);
	void addOnPauseListener(final OnPauseListener listener);
	void addOnDestroyListener(final OnDestroyListener listener);
	void addOnSaveInstanceListener(final OnSaveInstanceListener listener);


	interface OnResumeListener {
		void onResume();
	}

	interface OnPauseListener {
		void onPause();
	}

	interface OnDestroyListener {
		void onDestroy();
	}

	interface OnSaveInstanceListener {
		void save(Bundle out);
	}
}
