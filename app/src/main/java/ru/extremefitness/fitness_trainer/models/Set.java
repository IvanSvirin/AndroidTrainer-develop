package ru.extremefitness.fitness_trainer.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Osipova Ekaterina on 12.02.2016.
 */
@Parcel
public class Set {
    int weight;
    int repeat;
    @SerializedName("rest_time")
    int restTime;

    public Set() {
    }

    public int getWeight() {
        return weight;
    }

    public int getRepeat() {
        return repeat;
    }

    public int getRestTime() {
        return restTime;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(weight).append(" кг\n").append(repeat).append(" повторов")
                .toString();
    }
}
