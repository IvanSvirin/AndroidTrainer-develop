package ru.extremefitness.fitness_trainer.loader;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Comp on 29.04.2015.
 */
public class LoaderService extends Service {

    public static final String ACTION_LOAD_COMPLETE = "ru.extremefitness.fitness_trainer.ru.extremefitness.fitness_trainer.loader.ACTION_LOAD_COMPLETE";
    public static final String ACTION_LOAD_ERROR = "ru.extremefitness.fitness_trainer.ru.extremefitness.fitness_trainer.loader.ACTION_LOAD_ERROR";
    public static final String ACTION_UPDATE_PROGRESS = "ru.extremefitness.fitness_trainer.ru.extremefitness.fitness_trainer.loader.ACTION_UPDATE_PROGRESS";

    public static final String EXTRA_BROADCAST_MESSAGE = "extra_loader_service_broadcast_message";
    public static final String EXTRA_PROGRESS_VALUE = "extra_loader_service_progress_value";
    public static final String EXTRA_PROGRESS_ID = "extra_loader_service_progress_id";

    public enum PhotoSource {
        STORE,
        CAMERA,
        URL
    }

    private static IntentFilter intentFilter;
    private Loadable currentLoader;
    private Bitmap currentBitmap;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("LoaderService", "onCreate");
    }

    public void loadFromSource(final Intent data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    LoaderService.this.setCurrentBitmap(currentLoader.createData(data));
                    LoaderService.this.sendOptionalBroadcast(ACTION_LOAD_COMPLETE, "");
                } catch (IOException e) {
                    e.printStackTrace();
                    LoaderService.this.sendOptionalBroadcast(ACTION_LOAD_ERROR, e.getMessage());
                }
            }
        }).start();
    }

    public Bitmap getCurrentBitmap() {
        return currentBitmap;
    }

    private void setCurrentBitmap(Bitmap bitmap) {
        if (currentBitmap != null) {
            currentBitmap.recycle();
        }
        currentBitmap = bitmap;
    }

    public Intent startLoad(PhotoSource sourceId) {
        if (sourceId == PhotoSource.STORE) {
            currentLoader = new StoreLoader(this);
        } else if (sourceId == PhotoSource.CAMERA) {
            currentLoader = new CameraLoader(this);
        } else if (sourceId == PhotoSource.URL) {
            currentLoader = new UrlLoader(this);
        } else {
            throw new IllegalArgumentException(sourceId + " is not found");
        }

        return currentLoader.getLoadIntent();
    }

    private void sendOptionalBroadcast(String action, String message) {
        Intent intent = new Intent(action);
        intent.putExtra(EXTRA_BROADCAST_MESSAGE, message);
        sendBroadcast(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new LoaderBinder();
    }

    public class LoaderBinder extends Binder {
        public LoaderService getService() {
            return LoaderService.this;
        }
    }

    public static IntentFilter getIntentFilter() {

        if (null == intentFilter) {
            intentFilter = new IntentFilter();
            intentFilter.addAction(ACTION_LOAD_COMPLETE);
            intentFilter.addAction(ACTION_LOAD_ERROR);
            intentFilter.addAction(ACTION_UPDATE_PROGRESS);
        }

        return intentFilter;
    }
}
