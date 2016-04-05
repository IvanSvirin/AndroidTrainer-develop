package ru.extremefitness.fitness_trainer.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Osipova Ekaterina on 12.02.2016.
 */
@Parcel
public class Training {

    public static final String TRAINING_EXTRA = "training_extra";

    String name;
    String desc;
    @SerializedName("training_efficiency")
    List<BodyPartEfficiency> trainingEfficiency;
    List<ExerciseSet> exercises;

    public Training() {
    }

    public Training(int dayNumber) {
        // TODO: get string from resources
        this.name = "День "+ dayNumber;
        this.trainingEfficiency = new ArrayList<>();
        this.exercises = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<BodyPartEfficiency> getTrainingEfficiency() {
        return trainingEfficiency;
    }

    public List<ExerciseSet> getExercises() {
        return exercises;
    }

    public boolean isRest() {
        return exercises.isEmpty();
    }

    public String getDesc() {
        return desc;
    }
}
