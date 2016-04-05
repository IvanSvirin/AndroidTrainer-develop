package ru.extremefitness.fitness_trainer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;
import android.widget.NumberPicker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Comp on 16.09.2015.
 */
public final class Utils {

    public static final int WEIGHT = 1;
    public static final int HEIGHT = 2;

    public static String parseResource(Context context, int resource) throws IOException {
        InputStream is = context.getResources().openRawResource(resource);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }

        return writer.toString();
    }

    public static int getAppVersion(final Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static String getAppVersionName(final Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return "Version " + packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return context.getString(R.string.error_version_not_defined);
        }
    }

    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    public static String getMd5Hash(final String text) {
        /*String resultHash = "";
        try {
            byte[] byteOfMessage = text.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(byteOfMessage);
            StringBuilder sb = new StringBuilder();
            for (byte b : thedigest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            resultHash = sb.toString().substring(0, 8);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return resultHash;*/
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(text.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString().toLowerCase();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    public static String getDateString(String date, String fromFormat,
                                       String toFormat) {
        final SimpleDateFormat fromDateFormat = new SimpleDateFormat(fromFormat);
        final SimpleDateFormat toDateFormat = new SimpleDateFormat(toFormat);

        String newsDateString = "";
        try {
            newsDateString = toDateFormat.format(fromDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return newsDateString;
    }

   /* public static void showDialogPicker(final Context context, final int type, final DialogInterface.OnClickListener positiveListener){


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.set_value);

        View pickerView = View.inflate(context, R.layout.picker_simple_time, null);

        final NumberPicker picker = (NumberPicker) pickerView.findViewById(R.id.number_picker);

        if (type == WEIGHT) {
            picker.setMinValue(35);
            picker.setMaxValue(150);
        } else if (type == HEIGHT) {
            picker.setMinValue(130);
            picker.setMaxValue(2200);
        } else {
            return;
        }

        builder.setPositiveButton(R.string.action_ok, positiveListener);


            builder.setNegativeButton(R.string.action_cancel,null);

            builder.setView(pickerView);
        builder.create().show();
    }*/

    public static void showDialogPicker(final Context context, final int type, final NumberPickerResultListerner positiveListener) {


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.set_value);

        View pickerView = View.inflate(context, R.layout.picker_simple_time, null);

        final NumberPicker picker = (NumberPicker) pickerView.findViewById(R.id.number_picker);

        if (type == WEIGHT) {
            picker.setMinValue(35);
            picker.setMaxValue(150);
        } else if (type == HEIGHT) {
            picker.setMinValue(130);
            picker.setMaxValue(2200);
        } else {
            return;
        }

        builder.setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                positiveListener.onResult(String.valueOf(picker.getValue()));
            }
        });

        builder.setNegativeButton(R.string.action_cancel, null);

        builder.setView(pickerView);
        builder.create().show();
    }


    public interface NumberPickerResultListerner {
        void onResult(final String result);
    }
}
