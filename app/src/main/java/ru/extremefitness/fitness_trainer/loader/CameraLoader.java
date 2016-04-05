package ru.extremefitness.fitness_trainer.loader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import java.io.IOException;

/**
 * Created by Comp on 29.04.2015.
 */
public class CameraLoader implements Loadable {
    private Context mContext;

    public CameraLoader(Context context) {
        mContext = context;
    }

    @Override
    public Intent getLoadIntent() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        return cameraIntent;
    }

    @Override
    public Bitmap createData(Intent resultIntent) throws IOException {
        Bitmap photo = (Bitmap) resultIntent.getExtras().get("data");

        return photo;
    }

}
