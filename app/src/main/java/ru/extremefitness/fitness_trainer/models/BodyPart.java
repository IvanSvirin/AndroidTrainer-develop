package ru.extremefitness.fitness_trainer.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by Osipova Ekaterina on 12.02.2016.
 */
@Parcel
public class BodyPart {
    String id;
    String name;
    String status;
    // TODO: must be Date
    @SerializedName("last_update")
    String lastUpdate;
    @SerializedName("musculs")
    List<Muscle> muscles;

    public BodyPart() {
    }

    public String getName() {
        return name;
    }
}
