package ru.extremefitness.fitness_trainer.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import ru.extremefitness.fitness_trainer.Config;
import ru.extremefitness.fitness_trainer.models.GetGymContainer;
import ru.extremefitness.fitness_trainer.models.VideoInfoContainer;
import ru.extremefitness.fitness_trainer.models.login.LoginRequestContainer;

/**
 * Created by Comp on 15.09.2015.
 */
public enum ModelsEnum {

    VIDEO_INFO(VideoInfoContainer.class, Config.VIDEO_LIST_URL, Request.Method.GET),
    LOGIN(LoginRequestContainer.class, Config.LOGIN, Request.Method.POST),
    UPDATE_SETTINGS(LoginRequestContainer.class, Config.LOGIN, Request.Method.POST),
    GET_GYM(GetGymContainer.class, Config.SERVER_URL_GET_GYM, Request.Method.POST);

    private final String url;
    private final Class clazz;
    private final int method;
    private final Map<String, String> params = new HashMap<>();

    <T> ModelsEnum(final Class<T> clazz, final String url, final int method) {
        this.clazz = clazz;
        this.url = url;
        this.method = method;
    }

    public ModelsEnum with(final String... args) {
        if (null == args || (args.length % 2) != 0) {
            throw new IllegalArgumentException("wrong arguments");
        }

        for (int i = 0; i < args.length - 1; i += 2) {
            params.put(args[i], args[i + 1]);
        }

        return this;
    }

    public ModelsEnum with(final Object netLogin) {

        final Field[] fields = netLogin.getClass().getDeclaredFields();

        for (final Field f : fields) {
            putInParams(f, netLogin);
        }

        return this;
    }

    private void putInParams(final Field field, final Object netLogin) {
        try {
            field.setAccessible(true);
            params.put(field.getName(), (String) field.get(netLogin));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public <T> GsonRequest<T> createRequest(final Context context, final Response.Listener<T> listener,
                                            final Response.ErrorListener errorListener) {

        return GsonRequest.create(context, method, url,
                clazz, params, listener, errorListener);
    }

}
