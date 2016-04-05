package ru.extremefitness.fitness_trainer.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import ru.extremefitness.fitness_trainer.models.ExtremeRequest;
import ru.extremefitness.fitness_trainer.models.ExtremeResponse;
import ru.extremefitness.fitness_trainer.models.ProgramsResponse;

/**
 * Created by Osipova Ekaterina on 10.02.2016.
 */
public interface ExtremeTrainerService {

    @GET("programs")
    Call<ProgramsResponse> getPrograms();

    @POST("/systems/api/extreme_trainer/v1.1/rout.php?router=get_gym")
    Call<ExtremeResponse> getGym(@Body ExtremeRequest request);
}
