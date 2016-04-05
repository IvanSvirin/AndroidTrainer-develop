package ru.extremefitness.fitness_trainer.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.model.people.Person;

import ru.extremefitness.fitness_trainer.models.login.NetLogin;
import ru.extremefitness.fitness_trainer.network.ModelsEnum;
import ru.extremefitness.fitness_trainer.network.NetworkDispatcher;
import ru.extremefitness.fitness_trainer.ui.BaseActivity;

/**
 * Created: Krylov
 * Date: 20.09.2015
 * Time: 22:35
 */
public class ScreenGooglePlusLoginActivity extends BaseLoginActivity implements
        GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {

    private static final String TAG = "ExampleActivity";
    private static final int REQUEST_CODE_RESOLVE_ERR = 9000;

    private ProgressDialog mConnectionProgressDialog;
    private PlusClient mPlusClient;
    private ConnectionResult mConnectionResult;

    public static void startForResult(final BaseActivity activity) {
        activity.startActivityForResult(new Intent(activity, ScreenGooglePlusLoginActivity.class), GPLUS_REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlusClient = new PlusClient.Builder(this, this, this)
                .build();
        // Если ошибку соединения не удастся разрешить, будет отображаться индикатор выполнения.
        mConnectionProgressDialog = new ProgressDialog(this);
        mConnectionProgressDialog.setMessage("Signing in...");

        ProgressBar progressBar = new ProgressBar(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        setContentView(progressBar, layoutParams);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mPlusClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPlusClient.disconnect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (result.hasResolution()) {
            try {
                result.startResolutionForResult(this, REQUEST_CODE_RESOLVE_ERR);
            } catch (IntentSender.SendIntentException e) {
                mPlusClient.connect();
            }
        }
        // �������� ��������� � �������� ������ ���������� �� ������� ������������.
        mConnectionResult = result;
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == REQUEST_CODE_RESOLVE_ERR && responseCode == RESULT_OK) {
            mConnectionResult = null;
            mPlusClient.connect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        final Person person = mPlusClient.getCurrentPerson();
        final NetLogin netLogin = new NetLogin(person);
        NetworkDispatcher.invoke(ModelsEnum.LOGIN.with(netLogin));

    }

    @Override
    public void onDisconnected() {
        Log.d(TAG, "disconnected");
    }

}
