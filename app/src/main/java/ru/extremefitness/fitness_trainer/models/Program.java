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
 * Created by Osipova Ekaterina on 10.02.2016.
 */
@Parcel
public class Program {

    public static final String PROGRAM_EXTRA = "program_extra";
    public static final String PROGRAM_NAME_EXTRA = "program_name_extra";

    String name;
    String progress;
    int time;
    String about;
    @SerializedName("program_efficiency")
    List<BodyPartEfficiency> programEfficiency;
    List<Training> trainings;

    public Program() {
    }

    public Program(String name, String progress) {
        this.name = name;
        this.progress = progress;
    }

    public static Program newTestExercise(Context context) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(Utils.parseResource(context, R.raw.program), Program.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // TODO: return empty Program
        return null;
    }

    public String getName() {
        return name;
    }

    public String getProgress() {
        return progress;
    }

    public int getTime() {
        return time;
    }

    public String getAbout() {
        return about;
    }

    public List<BodyPartEfficiency> getProgramEfficiency() {
        return programEfficiency;
    }

    public List<Training> getTrainings() {
        return trainings;
    }
}
