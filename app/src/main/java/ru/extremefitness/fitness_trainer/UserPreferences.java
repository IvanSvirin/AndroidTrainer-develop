package ru.extremefitness.fitness_trainer;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;

import ru.extremefitness.fitness_trainer.models.login.LoginRequestContainer;

/**
 * Created by user on 19.09.2015.
 */
public class UserPreferences {

    private static final String USER_PREFERENCES = "ru.extremefitness.fitness_trainer.UserPreferences.USER_PREFERENCES";
    private static final String USER_DATA = "ru.extremefitness.fitness_trainer.UserPreferences.USER_DATA";

    private static SharedPreferences getPreferences(final Context context) {
        return context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
    }

    public static void saveUserData(final Context context, final LoginRequestContainer.UserData userData) {
        final Gson gson = new Gson();
        final SharedPreferences preferences = getPreferences(context);
        preferences.edit().putString(USER_DATA, gson.toJson(userData)).apply();
    }

    public static synchronized LoginRequestContainer.UserData getUserData(final Context context) {
        if (hasUserData(context)) {
            final SharedPreferences preferences = getPreferences(context);
            final String userDate = preferences.getString(USER_DATA, null);
            final Gson gson = new Gson();
            return gson.fromJson(userDate, LoginRequestContainer.UserData.class);
        }

//        return new LoginRequestContainer.UserData();
        throw new IllegalArgumentException("have not user data");

    }

    public static synchronized boolean hasUserData(final Context context) {
        return !TextUtils.isEmpty(getPreferences(context).getString(USER_DATA, null));
    }

    public static void clear(Context context) {
        getPreferences(context).edit().clear().commit();
    }
}
