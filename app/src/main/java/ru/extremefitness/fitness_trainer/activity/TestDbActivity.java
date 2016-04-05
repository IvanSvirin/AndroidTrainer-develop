package ru.extremefitness.fitness_trainer.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import ru.extremefitness.fitness_trainer.R;
import ru.extremefitness.fitness_trainer.provider.TrainerDatabase;
import ru.extremefitness.fitness_trainer.provider.TrainerContract.*;

public class TestDbActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_db);
        textView = (TextView) findViewById(R.id.text);
        testDb();
    }

    private void testDb() {
        TrainerDatabase trainerDatabase = new TrainerDatabase(this);
        SQLiteDatabase db = trainerDatabase.getWritableDatabase();

        ContentValues bodyPart1Values = new ContentValues();
        bodyPart1Values.put(BodyParts.BODY_PART_ID, "a");
        bodyPart1Values.put(BodyParts.BODY_PART_NAME, "arms");
        bodyPart1Values.put(BodyParts.BODY_PART_ARCHIVED, 0);

        ContentValues bodyPart2Values = new ContentValues();
        bodyPart2Values.put(BodyParts.BODY_PART_ID, "b");
        bodyPart2Values.put(BodyParts.BODY_PART_NAME, "legs");
        bodyPart2Values.put(BodyParts.BODY_PART_ARCHIVED, 0);

        db.insert(TrainerDatabase.TablesNames.BODY_PARTS, null, bodyPart1Values);
        db.insert(TrainerDatabase.TablesNames.BODY_PARTS, null, bodyPart2Values);

        ContentValues muscule1Values = new ContentValues();
        muscule1Values.put(Muscules.MUSCULE_ID, "a");
        muscule1Values.put(Muscules.MUSCULE_NAME, "arm muscule 1");
        muscule1Values.put(Muscules.MUSCULE_ABOUT, "about arm muscule 1");
        muscule1Values.put(Muscules.MUSCULE_ARCHIVED, 0);
        muscule1Values.put(Muscules.MUSCULE_BODY_PART_ID, "a");

        ContentValues muscule2Values = new ContentValues();
        muscule2Values.put(Muscules.MUSCULE_ID, "b");
        muscule2Values.put(Muscules.MUSCULE_NAME, "arm muscule 2");
        muscule2Values.put(Muscules.MUSCULE_ABOUT, "about arm muscule 2");
        muscule2Values.put(Muscules.MUSCULE_ARCHIVED, 0);
        muscule2Values.put(Muscules.MUSCULE_BODY_PART_ID, "b");

        db.insert(TrainerDatabase.TablesNames.MUSCULES, null, muscule1Values);
        db.insert(TrainerDatabase.TablesNames.MUSCULES, null, muscule2Values);
        db.close();

        SQLiteDatabase readdb = trainerDatabase.getReadableDatabase();

        String[] projection = {BodyParts.BODY_PART_ID, BodyParts.BODY_PART_NAME};
        Cursor cursor = readdb.query(TrainerDatabase.TablesNames.BODY_PARTS, projection, null,
                null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            textView.append(cursor.getString(cursor.getColumnIndex(BodyParts.BODY_PART_ID)));
            textView.append("\n");
            textView.append(cursor.getString(cursor.getColumnIndex(BodyParts.BODY_PART_NAME)));
            textView.append("\n");
            cursor.moveToNext();
        }
        cursor.close();

        String[] projection2 = {Muscules.MUSCULE_ID, Muscules.MUSCULE_NAME, Muscules.MUSCULE_BODY_PART_ID};
        Cursor cursor2 = readdb.query(TrainerDatabase.TablesNames.MUSCULES, projection2, null,
                null, null, null, null);
        cursor2.moveToFirst();
        while (!cursor2.isAfterLast()) {
            textView.append(cursor2.getString(cursor2.getColumnIndex(Muscules.MUSCULE_ID)));
            textView.append("\n");
            textView.append(cursor2.getString(cursor2.getColumnIndex(Muscules.MUSCULE_NAME)));
            textView.append("\n");
            textView.append(cursor2.getString(cursor2.getColumnIndex(Muscules.MUSCULE_BODY_PART_ID)));
            textView.append("\n");
            cursor2.moveToNext();
        }
        cursor2.close();
        readdb.close();
        trainerDatabase.close();
    }
}
