package ru.extremefitness.fitness_trainer.models;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.io.IOException;
import java.util.List;

import ru.extremefitness.fitness_trainer.R;
import ru.extremefitness.fitness_trainer.Utils;

/**
 * Created by Osipova Ekaterina on 12.02.2016.
 */
@Parcel
public class Exercise {

    public static final String EXERCISE_EXTRA = "exercise_extra";

    String id;
    String name;
    @SerializedName("info_about")
    String about;
    @SerializedName("group_id")
    String groupId;
    String type;
    @SerializedName("min_reps")
    String minReps;
    @SerializedName("max_reps")
    String maxReps;
    @SerializedName("step_reps")
    String stepReps;
    @SerializedName("min_sets")
    String minSets;
    @SerializedName("max_sets")
    String maxSets;
    @SerializedName("step_sets")
    String stepSets;
    @SerializedName("min_timer")
    String minTimer;
    @SerializedName("max_timer")
    String maxTimer;
    @SerializedName("step_timer")
    String stepTimer;
    @SerializedName("min_relax")
    String minRelax;
    @SerializedName("max_relax")
    String maxRelax;
    @SerializedName("step_time_relax")
    String stepTimeRelax;
    @SerializedName("min_weight")
    String minWeight;
    @SerializedName("max_weight")
    String maxWeight;
    @SerializedName("step_weight")
    String stepWeight;
    @SerializedName("last_update")
    String lastUpdate;
    String status;
    List<String> images;
    List<String> videos;
    @SerializedName("exercise_efficiency")
    List<BodyPartEfficiency> exerciseEfficiency;

    public Exercise() {
    }

    public static Exercise newTestExercise(Context context) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(Utils.parseResource(context, R.raw.exercise), Exercise.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // TODO: return empty Exercise
        return null;
    }

    public String getName() {
        return name;
    }

    public String getAbout() {
        return about;
    }

    public List<BodyPartEfficiency> getExerciseEfficiency() {
        return exerciseEfficiency;
    }
}
