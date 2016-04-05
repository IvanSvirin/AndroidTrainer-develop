package ru.extremefitness.fitness_trainer.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by Osipova Ekaterina on 12.02.2016.
 */
@Parcel
public class BodyPartEfficiency {
    @SerializedName("body_part")
    BodyPart bodyPart;
    int efficiency;

    public BodyPartEfficiency() {
    }

    public BodyPart getBodyPart() {
        return bodyPart;
    }

    public int getEfficiency() {
        return efficiency;
    }

    public static String inlineBodyPartEfficiency(List<BodyPartEfficiency> bodyPartEfficiencies) {
        if (bodyPartEfficiencies == null)
            return "";
        StringBuilder stringBuilder = new StringBuilder();
        for (BodyPartEfficiency bodyPartEfficiency:
             bodyPartEfficiencies) {
            stringBuilder.append(bodyPartEfficiency.getBodyPart().getName()).append(" ").append
                    (bodyPartEfficiency.getEfficiency()).append("\n");
        }
        return stringBuilder.toString();
    }
}
