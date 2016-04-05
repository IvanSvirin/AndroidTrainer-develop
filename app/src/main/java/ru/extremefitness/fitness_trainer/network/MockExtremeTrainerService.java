package ru.extremefitness.fitness_trainer.network;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.mock.BehaviorDelegate;
import ru.extremefitness.fitness_trainer.R;
import ru.extremefitness.fitness_trainer.Utils;
import ru.extremefitness.fitness_trainer.models.ExtremeRequest;
import ru.extremefitness.fitness_trainer.models.ExtremeResponse;
import ru.extremefitness.fitness_trainer.models.ProgramsResponse;

/**
 * Created by Osipova Ekaterina on 10.02.2016.
 */
public class MockExtremeTrainerService implements ExtremeTrainerService {

    Context context;
    private final BehaviorDelegate<ExtremeTrainerService> delegate;

    public MockExtremeTrainerService(Context context, BehaviorDelegate<ExtremeTrainerService>
            delegate) {
        this.context = context;
        this.delegate = delegate;
    }

    @Override
    public Call<ProgramsResponse> getPrograms() {
        try {
            Gson gson = new Gson();
            ProgramsResponse programsResponse = gson.fromJson(Utils.parseResource(context, R.raw
                    .programs), ProgramsResponse.class);
            return delegate.returningResponse(programsResponse).getPrograms();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return delegate.returningResponse(new ProgramsResponse()).getPrograms();
    }

    @Override
    public Call<ExtremeResponse> getGym(@Body ExtremeRequest request) {
        return null;
    }
}
