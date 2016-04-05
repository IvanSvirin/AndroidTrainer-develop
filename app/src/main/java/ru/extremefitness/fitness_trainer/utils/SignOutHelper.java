package ru.extremefitness.fitness_trainer.utils;

import android.content.Context;

import java.io.File;

import ru.extremefitness.fitness_trainer.UserPreferences;

/**
 * Created by Osipova Ekaterina on 05.02.2016.
 */
public class SignOutHelper {

    // TODO: need to find better place for this method
    public static boolean isLogin(Context context) {
        return UserPreferences.hasUserData(context);
    }

    public static void signOut(Context context) {
        deleteFiles();
        UserPreferences.clear(context);
    }

    private static void deleteFiles() {
        File root = android.os.Environment.getExternalStorageDirectory();

        File dir = new File(root.getAbsoluteFile() + "/Trainer");
        if (dir.exists()) {
            dir.delete();
        }
    }
}
