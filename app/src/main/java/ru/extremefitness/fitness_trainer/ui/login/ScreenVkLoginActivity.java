package ru.extremefitness.fitness_trainer.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKList;

import ru.extremefitness.fitness_trainer.models.login.NetLogin;
import ru.extremefitness.fitness_trainer.network.ModelsEnum;
import ru.extremefitness.fitness_trainer.network.NetworkDispatcher;
import ru.extremefitness.fitness_trainer.ui.BaseActivity;

/**
 * Created: Krylov
 * Date: 17.09.2015
 * Time: 1:21
 */
public class ScreenVkLoginActivity extends BaseLoginActivity {

    public static void startForResult(final BaseActivity activity) {
        activity.startActivityForResult(new Intent(activity, ScreenVkLoginActivity.class), VK_REQUEST_CODE);
    }

    private static String[] sMyScope = new String[]{VKScope.FRIENDS, VKScope.WALL, VKScope.PHOTOS, VKScope.NOHTTPS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VKSdk.login(this, sMyScope);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                processResult();
            }

            @Override
            public void onError(VKError error) {
                finish();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void processResult() {
        VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "photo_200,sex,bdate"));

        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                Log.i("tag", response.json.toString());
                final VKList<VKApiUser> list = (VKList<VKApiUser>) response.parsedModel;
                final VKApiUser user = list.get(0);
                final NetLogin netLogin = new NetLogin(user);
                NetworkDispatcher.invoke(ModelsEnum.LOGIN.with(netLogin));
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
            }
        });
    }


}
