package ru.extremefitness.fitness_trainer.ui;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;

import ru.extremefitness.fitness_trainer.loader.LoaderService;


/**
 * Created by user on 06.09.2015.
 */
public class MainActivity extends BaseActivity {


    private static final String ACTION_UPDATE_USERS_DATA = "ru.extremefitness.fitness_trainer.ui.MainActivity.ACTION_UPDATE_USERS_DATA";
    private Intent serviceIntent;
    private MainViewModel viewModel;
    private MyDashBoardModelView.ViewModes currentMode = MyDashBoardModelView.ViewModes.LOAD;

    public static void start(final Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    private BroadcastReceiver loaderServiceReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            viewModel.loaderOnReceive(intent);
        }
    };

    private final BroadcastReceiver updateUserReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ACTION_UPDATE_USERS_DATA.equals(intent.getAction())) {
                viewModel.loadUserData();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        serviceIntent = new Intent(this, LoaderService.class);

        viewModel = new MainViewModel(this);
        setContentView(viewModel.getView());

        startService(serviceIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(updateUserReceiver, new IntentFilter(ACTION_UPDATE_USERS_DATA));
        registerReceiver(loaderServiceReceiver, LoaderService.getIntentFilter());

        currentMode.apply(viewModel.getMyDashboard());

        bindService(serviceIntent, connection, Service.BIND_IMPORTANT);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            viewModel.setLoaderService(((LoaderService.LoaderBinder) service).getService());
            currentMode = MyDashBoardModelView.ViewModes.CONTENT;
            currentMode.apply(viewModel.getMyDashboard());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(updateUserReceiver);
        unregisterReceiver(loaderServiceReceiver);
        unbindService(connection);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    public static void sendUpdateUser(final Context context) {
        context.sendBroadcast(new Intent(ACTION_UPDATE_USERS_DATA));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.saveChanges();
    }


}
