package ru.extremefitness.fitness_trainer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.extremefitness.fitness_trainer.R;
import ru.extremefitness.fitness_trainer.adapter.ProgramCatalogRecyclerViewAdapter;
import ru.extremefitness.fitness_trainer.adapter.ProgramRecyclerViewAdapter;
import ru.extremefitness.fitness_trainer.models.Program;
import ru.extremefitness.fitness_trainer.models.ProgramsResponse;
import ru.extremefitness.fitness_trainer.network.RestClient;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Osipova Ekaterina on 10.02.2016.
 */
public class ProgramsCatalogActivity extends AppCompatActivity {

    List<Program> programs = new ArrayList<>();
    private ProgramCatalogRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programs_catalog);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.program_list);

        adapter = new ProgramCatalogRecyclerViewAdapter(programs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        Call<ProgramsResponse> programsResponseCall = RestClient.getMockApiservice(this)
                .getPrograms();
        programsResponseCall.enqueue(new Callback<ProgramsResponse>() {
            @Override
            public void onResponse(Call<ProgramsResponse> call, Response<ProgramsResponse> response) {
                if (response.isSuccess()) {
                    ProgramsResponse programsResponse = response.body();
                    programs.clear();
                    programs.addAll(programsResponse.getPrograms());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ProgramsResponse> call, Throwable t) {

            }
        });
    }

    public void addProgram(View view) {
        startActivity(new Intent(this, AddProgramActivity.class));
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
