package ru.extremefitness.fitness_trainer.loader;

import android.content.Intent;
import android.graphics.Bitmap;

import java.io.IOException;

/**
 * Created by Comp on 29.04.2015.
 */
public interface Loadable {
    public Intent getLoadIntent();

    public Bitmap createData(Intent resultIntent) throws IOException;
}
