package ru.extremefitness.fitness_trainer.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Osipova Ekaterina on 03.03.2016.
 */
@Parcel
public class ExerciseMuscle {
    String id;
    @SerializedName("id_muscle")
    String idMuscle;
    @SerializedName("id_exercise")
    String idExercise;
    String priority;
    // TODO: must be date
    @SerializedName("last_update")
    String lastUpdate;
    String status;

    public ExerciseMuscle() {
    }
}
