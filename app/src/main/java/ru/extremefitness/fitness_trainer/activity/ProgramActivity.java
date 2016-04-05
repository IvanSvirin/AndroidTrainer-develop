package ru.extremefitness.fitness_trainer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import ru.extremefitness.fitness_trainer.AdapterItemSelectedListener;
import ru.extremefitness.fitness_trainer.R;
import ru.extremefitness.fitness_trainer.adapter.TrainingRecyclerViewAdapter;
import ru.extremefitness.fitness_trainer.models.BodyPartEfficiency;
import ru.extremefitness.fitness_trainer.models.Program;
import ru.extremefitness.fitness_trainer.models.Training;
import ru.extremefitness.fitness_trainer.view.EfficiencyChart;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Osipova Ekaterina on 23.02.2016.
 */
public class ProgramActivity extends AppCompatActivity implements
        AdapterItemSelectedListener<Training> {

    public static final String EDITABLE_EXTRA = "editable_extra";

    List<Training> trainings = new ArrayList<>();

    ScrollView scrollView;
    private TrainingRecyclerViewAdapter adapter;
    private boolean editable;
    private Program program;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);

        program = Parcels.unwrap(getIntent().getParcelableExtra(Program.PROGRAM_EXTRA));
        editable = getIntent().getBooleanExtra(EDITABLE_EXTRA, false);

        TextView programNameTextView = (TextView) findViewById(R.id.program_name);
        TextView programProgressTextView = (TextView) findViewById(R.id.program_progress);
        TextView programAboutTextView = (TextView) findViewById(R.id.program_about);
        EfficiencyChart programEfficiencyChart = (EfficiencyChart) findViewById(R.id
                .program_efficiency);
        RecyclerView daysRecyclerView = (RecyclerView) findViewById(R.id.days_list);
        Button addTrainingBtn = (Button) findViewById(R.id.add_training);
        scrollView = (ScrollView) findViewById(R.id.scroll_view);

        programNameTextView.setText(program.getName());
        programProgressTextView.setText(program.getProgress());
        programAboutTextView.setText(program.getAbout());
        programEfficiencyChart.setData(program.getProgramEfficiency());
        addTrainingBtn.setVisibility(editable ? View.VISIBLE : View.GONE);

        daysRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TrainingRecyclerViewAdapter(trainings, this);
        daysRecyclerView.setAdapter(adapter);

        trainings.clear();
        trainings.addAll(program.getTrainings());
        adapter.notifyDataSetChanged();
    }

    public void addTraining(View view) {
        trainings.add(new Training(trainings.size() + 1));
        adapter.notifyDataSetChanged();
        scrollView.post(new Runnable() {
            public void run() {
                scrollView.smoothScrollTo(0, scrollView.getBottom());
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onItemSelected(Training item) {
        Intent intent = new Intent(this, ExercisesActivity.class);
        intent.putExtra(Training.TRAINING_EXTRA, Parcels.wrap(item));
        intent.putExtra(ProgramActivity.EDITABLE_EXTRA, editable);
        startActivity(intent);
    }

    public void startTraining(View view) {
        if (editable) {
            Toast.makeText(this, R.string.save_maker_program, Toast.LENGTH_SHORT).show();
            return;
        }
        if (trainings.isEmpty()) {
            Toast.makeText(this, R.string.empty_trainings, Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO: here will be current training not first
        Intent intent = new Intent(this, TrainingActivity.class);
        intent.putExtra(Training.TRAINING_EXTRA, Parcels.wrap(trainings.get(0)));
        intent.putExtra(Program.PROGRAM_NAME_EXTRA, program.getName());
        startActivity(intent);
    }
}
