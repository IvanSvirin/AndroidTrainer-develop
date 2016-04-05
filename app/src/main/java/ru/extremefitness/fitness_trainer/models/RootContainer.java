package ru.extremefitness.fitness_trainer.models;

/**
 * Created by Comp on 18.08.2015.
 */
public abstract class RootContainer {

    public boolean isSuccess() {

        return null != status && !status.equals("error");
    }

    public enum StatusModes {
        OK, ERROR
    }

    private String status;

    private String error_code;

    private String error_message;

    public String getErrorCode() {
        return error_code;
    }

    public String getErrorMessage() {
        return error_message;
    }

    public String getStatus() {
        return status;
    }

    public abstract <T> T getData();
}
