package ru.extremefitness.fitness_trainer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import org.parceler.Parcels;

import ru.extremefitness.fitness_trainer.R;
import ru.extremefitness.fitness_trainer.models.Program;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Osipova Ekaterina on 24.02.2016.
 */
public class AddProgramActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_program);
    }

    public void openCatalog(View view) {
        startActivity(new Intent(this, ProgramsCatalogActivity.class));
    }

    public void openMaker(View view) {
        Intent intent = new Intent(this, ProgramActivity.class);
        intent.putExtra(Program.PROGRAM_EXTRA, Parcels.wrap(Program.newTestExercise(this)));
        intent.putExtra(ProgramActivity.EDITABLE_EXTRA, true);
        startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
