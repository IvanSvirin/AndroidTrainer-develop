package ru.extremefitness.fitness_trainer.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Osipova Ekaterina on 03.03.2016.
 */
public class DataGym {

    @SerializedName("body_parts")
    List<BodyPart> bodyParts;
    List<Exercise> exercises;

    public List<BodyPart> getBodyParts() {
        return bodyParts;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }
}
