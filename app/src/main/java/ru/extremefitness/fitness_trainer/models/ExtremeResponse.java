package ru.extremefitness.fitness_trainer.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Osipova Ekaterina on 02.03.2016.
 */
public class ExtremeResponse {
    String status;
    @SerializedName("error_code")
    String errorCode;
    @SerializedName("error_message")
    String errorMessage;
    DataGym data;

    public String getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public DataGym getData() {
        return data;
    }
}
