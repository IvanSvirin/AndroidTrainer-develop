package ru.extremefitness.fitness_trainer.loader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Comp on 29.04.2015.
 */
public class StoreLoader implements Loadable {

    private Context mContext;

    public StoreLoader(Context context) {
        mContext = context;
    }

    @Override
    public Intent getLoadIntent() {
        Intent selectIntent = new Intent(Intent.ACTION_GET_CONTENT);
        selectIntent.setType("image/*");
        selectIntent.addCategory(Intent.CATEGORY_OPENABLE);

        return selectIntent;
    }

    @Override
    public Bitmap createData(Intent resultIntent) throws IOException {
        InputStream inputStream = mContext.getContentResolver().openInputStream(resultIntent.getData());
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream, new Rect(), options);

        inputStream.close();
        return bitmap;
    }

}
