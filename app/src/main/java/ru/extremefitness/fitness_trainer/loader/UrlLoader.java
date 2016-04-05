package ru.extremefitness.fitness_trainer.loader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Comp on 29.04.2015.
 */
public class UrlLoader implements Loadable {

    public enum UrlIds {
        MAIN_AVATAR,
        INVALID
    }

    public static String EXTRA_URL = "ru.extremefitness.fitness_trainer.ru.extremefitness.fitness_trainer.loader.UrlLoader.EXTRA_URL";
    private String ID = UrlIds.INVALID.name();

    private final Context mContext;

    public UrlLoader(Context context) {
        mContext = context;
    }

    @Override
    public Intent getLoadIntent() {
        return new Intent();
    }

    @Override
    public Bitmap createData(Intent resultIntent) throws IOException {

        ID = resultIntent.getStringExtra(LoaderService.EXTRA_PROGRESS_ID);

        String url = resultIntent.getStringExtra(EXTRA_URL);

        String path = loadImage(url);

        BitmapFactory.Options options = new BitmapFactory.Options();
        //    options.inSampleSize = 20;
        options.inJustDecodeBounds = true;


        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }

    private String loadImage(String url) throws IOException {
        File root = android.os.Environment.getExternalStorageDirectory();

        File dir = new File(root.getAbsoluteFile() + "/Trainer");

        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, "avatar");

        URLConnection urlConnection = new URL(url).openConnection();

        BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());

        FileOutputStream fileOutputStream = new FileOutputStream(file);

        byte[] data = new byte[1024];
        int count = 0;
        int total = 0;
        int lengthOfFile = urlConnection.getContentLength();

        while ((count = bufferedInputStream.read(data)) != -1) {
            total += count;
            // publishing the progress....
            // After this onProgressUpdate will be called
            publishProgress((total * 100) / lengthOfFile);

            // writing data to file
            fileOutputStream.write(data, 0, count);
        }

        // flushing output
        fileOutputStream.flush();

        // closing streams
        fileOutputStream.close();
        bufferedInputStream.close();

        return file.toString();
    }

    private void publishProgress(int progress) {
        Intent intent = new Intent(LoaderService.ACTION_UPDATE_PROGRESS);
        intent.putExtra(LoaderService.EXTRA_PROGRESS_ID, ID);
        intent.putExtra(LoaderService.EXTRA_PROGRESS_VALUE, progress);
        mContext.sendBroadcast(intent);
    }

}
