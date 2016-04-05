package ru.extremefitness.fitness_trainer.ui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.idunnololz.widgets.AnimatedExpandableListView;

import ru.extremefitness.fitness_trainer.R;
import ru.extremefitness.fitness_trainer.models.GetGymContainer;

/**
 * Created by Александр on 17.11.2015.
 */

public class ExpListAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
    private GetGymContainer.BodyParts[] mGroups;
    private GetGymContainer.ExercisesInfo[] exGroups;
    private Context mContext;
    private static final float SHADE_FACTOR = 0.9f;
    final String LOG_TAG = "myLogs";
    DBHelper dbHelper, dbHelper1;
    String name_musculs_button = null;
    String tag_color_text = null;

    public ExpListAdapter(Context context, GetGymContainer.BodyParts[] groups, GetGymContainer.ExercisesInfo[] exerGroups) {
        mContext = context;
        mGroups = groups;
        exGroups = exerGroups;
    }

    @Override
    public int getGroupCount() {
        return mGroups.length;
    }

    /*@Override
    public int getChildrenCount(int groupPosition) {

    }*/

    @Override
    public Object getGroup(int groupPosition) {
        return mGroups[groupPosition];
    }

    @Override
    public GetGymContainer.Musculs getChild(int groupPosition, int childPosition) {
        return mGroups[groupPosition].getMusculses()[childPosition];
    }


    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_view, null);
        }

        dbHelper1 = new DBHelper(mContext);

        String selection;
        String selectionArgs[];

        ContentValues cv_muscules = new ContentValues();

        int id_musculses = mGroups[groupPosition].get_id();
        String name_musculses = mGroups[groupPosition].get_name();





        SQLiteDatabase db = dbHelper1.getWritableDatabase();

        cv_muscules.put("id_musculses", id_musculses);
        cv_muscules.put("name_musculses", name_musculses);

        long rowIDmuscles = db.insert("muscles", null, cv_muscules);

        Log.d(LOG_TAG, "row inserted into muscles, id = " + rowIDmuscles);

        selection = "id_musculses = ?";
        selectionArgs = new String[]{String.valueOf(id_musculses)};

        Cursor c = db.query("muscles", null, selection, selectionArgs, null, null, null);


        if (c.moveToFirst()) {
            int id_musculsesColIndex = c.getColumnIndex("id_musculses");
            int name_musculsesColIndex = c.getColumnIndex("name_musculses");

            do {
                Log.d(LOG_TAG, "id_musculses = " + c.getInt(id_musculsesColIndex) + ", name_musculses = " + c.getString(name_musculsesColIndex));
                TextView textGroup = (TextView) convertView.findViewById(R.id.textGroup);
                textGroup.setText(c.getString(name_musculsesColIndex));
                name_musculs_button = c.getString(name_musculsesColIndex);

            } while (c.moveToNext());

        } else
            Log.d(LOG_TAG, "0 rows");
        c.close();


        dbHelper1.close();


        if (isExpanded) {
            //Изменяем что-нибудь, если текущая Group раскрыта


        } else {
            //Изменяем что-нибудь, если текущая Group скрыта
        }

        return convertView;

    }


    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_view, null);
        }

        dbHelper = new DBHelper(mContext);

        ContentValues cv_exercises = new ContentValues();

        int id_exercises = mGroups[groupPosition].getMusculses()[childPosition].getId();
        int id_body_part = mGroups[groupPosition].getMusculses()[childPosition].getId_body_part();
        String name_exercises = mGroups[groupPosition].getMusculses()[childPosition].getName();
        String exercises_about = mGroups[groupPosition].getMusculses()[childPosition].getAbout();
        String exercises_image = mGroups[groupPosition].getMusculses()[childPosition].getImage();
        String exercises_tag_color = mGroups[groupPosition].getMusculses()[childPosition].getTag_color();
        int exercises_status = mGroups[groupPosition].getMusculses()[childPosition].getStatus();
        String ex_name_exer_info = exGroups[groupPosition].getName();
        System.out.println(ex_name_exer_info);

        String selection;
        String[] selectionArgs;

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Log.d(LOG_TAG, "--- Insert in my table ---");


        try {
            cv_exercises.put("id_exercises", id_exercises);
            cv_exercises.put("id_body_part", id_body_part);
            cv_exercises.put("name_exercises", name_exercises);
            cv_exercises.put("exercises_about", exercises_about);
            cv_exercises.put("exercises_image", exercises_image);
            cv_exercises.put("exercises_tag_color", exercises_tag_color);
            cv_exercises.put("exercises_status", exercises_status);
        } catch (SQLiteConstraintException e) {
            System.out.println("Такая запись существует в БД!" + e);
        }

        //Обновление
        if (exercises_status == 0) {
            Log.d(LOG_TAG, "--- Update exercises: ---");
            // подготовим значения для обновления
            cv_exercises.put("id_exercises", id_exercises);
            cv_exercises.put("id_body_part", id_body_part);
            cv_exercises.put("name_exercises", name_exercises);
            cv_exercises.put("exercises_about", exercises_about);
            cv_exercises.put("exercises_image", exercises_image);
            cv_exercises.put("exercises_tag_color", exercises_tag_color);
            cv_exercises.put("exercises_status", exercises_status);
            // обновляем по id
            int updCount = db.update("exercises", cv_exercises, "id = ?",
                    new String[]{String.valueOf(id_exercises)});
            Log.d(LOG_TAG, "updated rows count = " + updCount);
        }

        long rowIDexercises = db.insert("exercises", null, cv_exercises);
        Log.d(LOG_TAG, "row inserted into exercises, id = " + rowIDexercises);


        Log.d(LOG_TAG, "--- Rows in exercises ---");

        selection = "id_exercises = ?";
        selectionArgs = new String[]{String.valueOf(id_exercises)};

        Cursor d = db.query("exercises", null, selection, selectionArgs, null, null, null);

        if (d.moveToFirst()) {

            int id_exercisesColIndex = d.getColumnIndex("id_exercises");
            int id_body_partColIndex = d.getColumnIndex("id_body_part");
            int name_exercisestColIndex = d.getColumnIndex("name_exercises");
            int exercises_aboutColIndex = d.getColumnIndex("exercises_about");
            int exercises_imageColIndex = d.getColumnIndex("exercises_image");
            int exercises_tag_colorColIndex = d.getColumnIndex("exercises_tag_color");
            int exercises_statusColIndex = d.getColumnIndex("exercises_status");

            do {
                Log.d(LOG_TAG, "id_exercises = " + d.getInt(id_exercisesColIndex) + " id_body_part = " + d.getInt(id_body_partColIndex) + " name_exercises = " + d.getString(name_exercisestColIndex) + " exercises_about = " + d.getString(exercises_aboutColIndex) + " exercises_image = " + d.getString(exercises_imageColIndex) + " tag_color = " + d.getString(exercises_tag_colorColIndex) + " exercises_status = " + d.getInt(exercises_statusColIndex));

                System.out.println(d.getString(name_exercisestColIndex));
                TextView textChild = (TextView) convertView.findViewById(R.id.textChild);
                textChild.setText(d.getString(name_exercisestColIndex));

                tag_color_text = String.valueOf(d.getString(exercises_tag_colorColIndex));

            } while (d.moveToNext());

        } else
            Log.d(LOG_TAG, "0 rows");

        d.close();

        dbHelper.close();

        convertView.setBackgroundColor(Color.parseColor(tag_color_text));

        Button button = (Button) convertView.findViewById(R.id.infoTextViewbtn);
        button.setText(name_musculs_button);
        button.setClickable(false);
        button.setFocusable(false);

        return convertView;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return mGroups[groupPosition].getMusculses().length;
    }



    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    //Метод для присвоения цвету более тёмного оттенка
    private int getDarkerShade(int color) {
        return Color.rgb((int) (SHADE_FACTOR * Color.red(color)),
                (int) (SHADE_FACTOR * Color.green(color)),
                (int) (SHADE_FACTOR * Color.blue(color)));
    }

    class DBHelper extends SQLiteOpenHelper {


        public DBHelper(Context context) {
            super(context, "AndroidTrainerDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            Log.d(LOG_TAG, "--- onCreate database ---");

            sqLiteDatabase.execSQL("create table muscles (" + "id_musculses integer primary key," + "name_musculses text" + ");");
            sqLiteDatabase.execSQL("create table exercises (" + "id_exercises integer primary key," + "id_body_part integer," + "name_exercises text," + "exercises_about text," + "exercises_image text," + "exercises_tag_color text," + "exercises_status integer" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }

}
