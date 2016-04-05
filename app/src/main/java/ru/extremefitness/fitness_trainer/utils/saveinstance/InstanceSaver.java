package ru.extremefitness.fitness_trainer.utils.saveinstance;

import android.os.Bundle;
import android.util.Log;

import junit.framework.Assert;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Set;

/**
 * Created by Comp on 21.06.2015.
 */
public class InstanceSaver {

    private static final String TAG = InstanceSaver.class.getName();

    private Object object;

    public InstanceSaver(final Object t) {
        Log.i(TAG, "constructor");
        object = t;
    }

    /*public static InstanceSaver createController(final Object object) {
        return new InstanceSaver(object);
    }*/

    public void saveInstance(final Bundle state) {
        Log.i(TAG, "saveInstance");
        if (null == state) {
            Log.i(TAG, "return");
            return;
        }

        Field[] fields = object.getClass().getDeclaredFields();

        Field f;
        for (int i = 0, limit = fields.length; i < limit; i++) {
            f = fields[i];
            if (f.isAnnotationPresent(SaveInBundle.class)) {
                putInBundle(f, state);
            }
        }
    }

    private void putInBundle(final Field field, final Bundle bundle) {
        Assert.assertNotNull("object is null", object);
        Log.i(TAG, "object class = " + object.getClass().getName());

        field.setAccessible(true);
        try {
//            Assert.assertTrue("object not Serializable, field name =  " + field.getName(), field.invoke(object) instanceof Serializable);
            bundle.putSerializable(object.getClass().getName() + field.getName(), (Serializable) field.get(object));
            Log.i(TAG, "putInBundle field = " + field.getClass().getName() + field.getName());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void restore(final Bundle state) {
        Log.i(TAG, "restore");
        if (null == state) {
            Log.i(TAG, "return");
            return;
        }

        Assert.assertNotNull("object is null", object);

        Set<String> keys = state.keySet();
        Field field;
        final String className = object.getClass().getName();
        for (String s : keys) {
            final String fieldName = s.replace(className, "");
            try {
                field = object.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(object, state.get(s));

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

    }
}
