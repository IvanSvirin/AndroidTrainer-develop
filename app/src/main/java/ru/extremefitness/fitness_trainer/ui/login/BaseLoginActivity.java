package ru.extremefitness.fitness_trainer.ui.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import ru.extremefitness.fitness_trainer.UserPreferences;
import ru.extremefitness.fitness_trainer.models.login.LoginRequestContainer;
import ru.extremefitness.fitness_trainer.models.login.NetLogin;
import ru.extremefitness.fitness_trainer.network.NetworkDispatcher;
import ru.extremefitness.fitness_trainer.ui.BaseActivity;

/**
 * Created: Krylov
 * Date: 21.09.2015
 * Time: 9:41
 */
public class BaseLoginActivity extends BaseActivity {

    public static final int TRAINER_REQUEST_CODE = 1004;
    public static final int EXTREME_LOGIN_REQUEST = 1001;
    public static final int GPLUS_REQUEST_CODE = 1003;
    public static final int VK_REQUEST_CODE = 1002;
    public static final int TWITTER_REQUEST_CODE = 1006;
    public static final int FACEBOOK_REQUEST_CODE = 1005;

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(networkReceiver, NetworkDispatcher.getIntentFilter());
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkReceiver);
    }

    private void sendResultAndFinish() {
        final LoginRequestContainer.UserData container = NetworkDispatcher.getResult().getData();
        final Intent resultIntent = new Intent();
        resultIntent.putExtra(LoginRequestContainer.EXTRA_LOGIN_RESULT, container);
        onNetworkDispatcherResult(Activity.RESULT_OK, resultIntent);
    }

    public void onNetworkDispatcherResult(int resultCode, Intent data) {
        setResult(Activity.RESULT_OK, data);
        finish();
    }

    protected void saveLoginData(int requestCode, int resultCode, Intent data) {
        if(resultCode == BaseActivity.RESULT_OK) {

            final LoginRequestContainer.UserData result =
                    (LoginRequestContainer.UserData) data.getSerializableExtra(LoginRequestContainer.EXTRA_LOGIN_RESULT);

            if (null != result) {
                if (EXTREME_LOGIN_REQUEST == requestCode) {
                    result.setAuthMethod(NetLogin.SocialNet.EXTREME.ordinal());
                } else if (TRAINER_REQUEST_CODE == requestCode) {
                    result.setAuthMethod(NetLogin.SocialNet.TRAINER.ordinal());
                }
                UserPreferences.saveUserData(this, result);
            }
        }
    }

    private final BroadcastReceiver networkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (NetworkDispatcher.ACTION_LOAD_DONE.equals(intent.getAction())) {
                sendResultAndFinish();
            } else {
                showErrorDialog(intent);
            }
        }
    };

    private void showErrorDialog(final Intent result) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setNegativeButton("OK", null);
        builder.setTitle("Ошибка");
        builder.setMessage(result.getStringExtra(NetworkDispatcher.EXTRA_ERROR));
        builder.create().show();
    }
}
