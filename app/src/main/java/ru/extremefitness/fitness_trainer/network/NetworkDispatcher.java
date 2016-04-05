package ru.extremefitness.fitness_trainer.network;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import ru.extremefitness.fitness_trainer.ExtremeTrainerApplication;
import ru.extremefitness.fitness_trainer.models.RootContainer;

/**
 * Created by Comp on 18.08.2015.
 */
public class NetworkDispatcher {

    public static final String ACTION_LOAD_DONE = "ru.extremefitness.fitness_trainer.network.NetworkDispatcher.ACTION_LOAD_DONE";
    public static final String ACTION_LOAD_ERROR = "ru.extremefitness.fitness_trainer.network.NetworkDispatcher.ACTION_LOAD_ERROR";
    public static final String EXTRA_ERROR = "ru.extremefitness.fitness_trainer.network.NetworkDispatcher.EXTRA_ERROR";

    private static final Context context;
    private static IntentFilter intentFilter;

    static {
        context = ExtremeTrainerApplication.getInstance().getApplicationContext();
        intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_LOAD_DONE);
        intentFilter.addAction(ACTION_LOAD_ERROR);
    }

    private static RootContainer result;

    public static RootContainer getResult() {
        return result;
    }

    public static void invoke(final ModelsEnum modelsEnum) {
        invoke(modelsEnum, null, null);
    }

    public static void invoke(final ModelsEnum modelsEnum, Response.Listener<RootContainer> listener,
                              Response.ErrorListener errorListener) {

        if (checkOnline(context)) {
            return;
        }

        if (listener == null) {
            listener = new Response.Listener<RootContainer>() {
                @Override
                public void onResponse(RootContainer response) {
                    result = response;
                    if (result.isSuccess()) {
                        context.sendBroadcast(new Intent(ACTION_LOAD_DONE));
                    } else {
                        final Intent errorIntent = new Intent(ACTION_LOAD_ERROR);
                        errorIntent.putExtra(EXTRA_ERROR,
                                result.getErrorCode() + "\n" + result.getErrorMessage());
                        context.sendBroadcast(errorIntent);
                    }
                }
            };
        }

        if (errorListener == null) {
            errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i(getClass().getName(), "ERROR " + error.toString());
                    final Intent errorIntent = new Intent(ACTION_LOAD_ERROR);
                    errorIntent.putExtra(EXTRA_ERROR, error.toString());
                    context.sendBroadcast(errorIntent);
                }
            };
        }

        final Request request = modelsEnum.createRequest(context,
                listener,
                errorListener);

        Volley.newRequestQueue(context).add(request);
    }


    private static boolean checkOnline(final Context context) {

        if (!isOnline(context)) {
            context.sendBroadcast(new Intent(ACTION_LOAD_ERROR));
            return true;
        }
        return false;
    }

    private static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public static IntentFilter getIntentFilter() {
        return intentFilter;
    }
}
