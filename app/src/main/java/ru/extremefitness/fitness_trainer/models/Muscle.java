package ru.extremefitness.fitness_trainer.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by Osipova Ekaterina on 03.03.2016.
 */
@Parcel
public class Muscle {
    String id;
    @SerializedName("id_body_part")
    String idBodyPart;
    String name;
    String about;
    String image;
    @SerializedName("tag_color")
    String tagColor;
    // TODO: must be Date
    @SerializedName("last_update")
    String lastUpdate;
    String status;
    List<ExerciseMuscle> exercises;

    public Muscle() {
    }
}
