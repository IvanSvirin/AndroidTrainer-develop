package ru.extremefitness.fitness_trainer.models;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Osipova Ekaterina on 12.02.2016.
 */
@Parcel
public class ExerciseSet {
    Exercise exercise;
    Set set;
    @SerializedName("set_repeat")
    int setRepeat;

    public ExerciseSet() {
    }

    public ExerciseSet(Exercise exercise, Set set) {
        this.exercise = exercise;
        this.set = set;
    }

    public ExerciseSet(Exercise exercise) {
        this.exercise = exercise;
        this.set = new Set();
    }

    public static ExerciseSet newTestExerciseSet(Context context) {
        Exercise exercise = Exercise.newTestExercise(context);
        if (exercise != null) {
            return new ExerciseSet(exercise);
        }
        // TODO: return empty ExerciseSet
        return null;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public Set getSet() {
        return set;
    }

    public int getSetRepeat() {
        return setRepeat;
    }
}
