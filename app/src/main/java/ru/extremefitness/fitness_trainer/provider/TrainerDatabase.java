package ru.extremefitness.fitness_trainer.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import ru.extremefitness.fitness_trainer.provider.TrainerContract.*;

/**
 * Created by Osipova Ekaterina on 15.02.2016.
 */
public class TrainerDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "trainer.db";

    private static final int VER_2016_RELEASE_1 = 1;
    private static final int CUR_DATABASE_VERSION = VER_2016_RELEASE_1;

    private final Context mContext;

    interface Tables {
        String BODY_PARTS = "body_parts";
        String MUSCULES = "muscules";
        String EXERCISES = "exercises";
        String MUSCULES_EXERCISES = "muscules_exercises";
        String EXERCISES_BODY_PARTS_EFFICIENCY = "exercises_body_part_efficiency";
    }

    public static class TablesNames implements Tables {

    }

    private interface References {
        String BODY_PART_ID = "REFERENCES " + Tables.BODY_PARTS + "(" + BodyPartsColumns.BODY_PART_ID + ")";
        String EXERCISE_ID = "REFERENCES " + Tables.EXERCISES + "(" + ExercisesColumns.EXERCISE_ID + ")";
        String MUSCULE_ID = "REFERENCES " + Tables.MUSCULES + "(" + MusculesColumns.MUSCULE_ID + ")";
    }

    public TrainerDatabase(Context context) {
        super(context, DATABASE_NAME, null, CUR_DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Tables.BODY_PARTS + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + BodyPartsColumns.BODY_PART_ID + " TEXT NOT NULL,"
                + BodyPartsColumns.BODY_PART_NAME + " TEXT NOT NULL,"
                + BodyPartsColumns.BODY_PART_ARCHIVED + " INTEGER NOT NULL,"
                + "UNIQUE (" + BodyPartsColumns.BODY_PART_ID + ") ON CONFLICT REPLACE)");
        db.execSQL("CREATE TABLE " + Tables.MUSCULES + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MusculesColumns.MUSCULE_ID + " TEXT NOT NULL,"
                + MusculesColumns.MUSCULE_NAME + " TEXT NOT NULL,"
                + MusculesColumns.MUSCULE_ABOUT + " TEXT NOT NULL,"
                + MusculesColumns.MUSCULE_ARCHIVED + " INTEGER NOT NULL,"
                + MusculesColumns.MUSCULE_BODY_PART_ID + " TEXT " + References.BODY_PART_ID + ","
                + "UNIQUE (" + MusculesColumns.MUSCULE_ID + ") ON CONFLICT REPLACE)");
        db.execSQL("CREATE TABLE " + Tables.EXERCISES + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ExercisesColumns.EXERCISE_ID + " TEXT NOT NULL,"
                + ExercisesColumns.EXERCISE_NAME + " TEXT NOT NULL,"
                + ExercisesColumns.EXERCISE_ABOUT + " TEXT NOT NULL,"
                + "UNIQUE (" + ExercisesColumns.EXERCISE_ID + ") ON CONFLICT REPLACE)");
        db.execSQL("CREATE TABLE " + Tables.MUSCULES_EXERCISES + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MusculesExercisesColumns.ME_ID + " TEXT NOT NULL,"
                + MusculesExercisesColumns.ME_MUSCULE_ID + " TEXT " + References.MUSCULE_ID + ","
                + MusculesExercisesColumns.ME_EXERCISE_ID + " TEXT " + References.EXERCISE_ID + ","
                + "UNIQUE (" + MusculesExercisesColumns.ME_ID + ") ON CONFLICT REPLACE)");
        db.execSQL("CREATE TABLE " + Tables.EXERCISES_BODY_PARTS_EFFICIENCY + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ExercisesBodyPartsEfficiencyColumns.EBPE_ID + " TEXT NOT NULL,"
                + ExercisesBodyPartsEfficiencyColumns.EBPE_BODY_PART_ID  + " TEXT " + References.BODY_PART_ID + ","
                + ExercisesBodyPartsEfficiencyColumns.EBPE_EXERCISE_ID  + " TEXT " + References.EXERCISE_ID
                + ","
                + ExercisesBodyPartsEfficiencyColumns.EBPE_EFFICIENCY + " INTEGER NOT NULL,"
                + "UNIQUE (" + ExercisesBodyPartsEfficiencyColumns.EBPE_ID + ") ON CONFLICT REPLACE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
