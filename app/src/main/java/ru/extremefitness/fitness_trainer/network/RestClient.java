package ru.extremefitness.fitness_trainer.network;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

/**
 * Created by Osipova Ekaterina on 10.02.2016.
 */
public class RestClient {

    private static final String BASE_URL = "http://www.extremefitness.ru";
    private static ExtremeTrainerService apiService;

    private static void createApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ExtremeTrainerService.class);
    }

    public static ExtremeTrainerService getApiService()
    {
        if (apiService == null) {
            createApiService();
        }
        return apiService;
    }

    public static ExtremeTrainerService getMockApiservice(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit)
//                .backgroundExecutor(Executors.newSingleThreadExecutor())
                .networkBehavior(NetworkBehavior.create()).build();
        BehaviorDelegate<ExtremeTrainerService> delegate = mockRetrofit.create(ExtremeTrainerService.class);
        return new MockExtremeTrainerService(context, delegate);
    }
}
