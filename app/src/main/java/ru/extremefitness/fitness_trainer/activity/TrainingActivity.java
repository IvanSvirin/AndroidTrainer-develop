package ru.extremefitness.fitness_trainer.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.parceler.Parcels;

import ru.extremefitness.fitness_trainer.R;
import ru.extremefitness.fitness_trainer.models.Program;
import ru.extremefitness.fitness_trainer.models.Training;
import ru.extremefitness.fitness_trainer.view.EfficiencyChart;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Osipova Ekaterina on 24.03.2016.
 */
public class TrainingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        Training training = Parcels.unwrap(getIntent().getParcelableExtra(Training.TRAINING_EXTRA));
        String programNameExtra = getIntent().getStringExtra(Program.PROGRAM_NAME_EXTRA);

        TextView programName = (TextView) findViewById(R.id.program_name);
        TextView trainingDay = (TextView) findViewById(R.id.training_day);
        TextView trainingDesc = (TextView) findViewById(R.id.training_desc);
        EfficiencyChart efficiencyChart = (EfficiencyChart) findViewById(R.id.training_efficiency);

        programName.setText(programNameExtra);
        trainingDay.setText(training.getName());
        trainingDesc.setText(training.getDesc());
        efficiencyChart.setData(training.getTrainingEfficiency());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
