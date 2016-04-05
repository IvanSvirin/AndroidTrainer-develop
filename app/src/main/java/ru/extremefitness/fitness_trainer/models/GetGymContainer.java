package ru.extremefitness.fitness_trainer.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Александр on 11.11.2015.
 */
public class GetGymContainer extends RootContainer implements Parcelable {

    private GymInfo data;

    protected GetGymContainer(Parcel in) {
    }

    public static final Creator<GetGymContainer> CREATOR = new Creator<GetGymContainer>() {
        @Override
        public GetGymContainer createFromParcel(Parcel in) {
            return new GetGymContainer(in);
        }

        @Override
        public GetGymContainer[] newArray(int size) {
            return new GetGymContainer[size];
        }
    };

    public GymInfo getData() {
        return data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }

    public class GymInfo {

        private BodyParts[] body_parts;

        public BodyParts[] getBody_parts() {
            return body_parts;
        }

        private ExercisesInfo[] exercises;

        public ExercisesInfo[] getExercisesInfo() {
            return exercises;
        }

    }


    public static class BodyParts {

        private Musculs[] musculs;


        public Musculs[] getMusculses() {
            return musculs;
        }


        private Exercises[] exercises;

        public Exercises[] getExercises() {
            return exercises;
        }



        private int id;
        private int uid_group;
        private String name;
        private String[] images;
        private String about;


        public int get_id() {
            return id;
        }

        public int get_uid_group() {
            return uid_group;
        }

        public String get_name() {
            return name;
        }

        public String[] get_images() {
            return images;
        }

        public String get_about() {
            return about;
        }




    }


    public static class Musculs {

        private int id;
        private int id_body_part;
        private String name;
        private String about;
        private String image;
        private String tag_color;
        private int status;
        private Exercises[] exercises;

        public int getId() {
            return id;
        }

        public int getId_body_part() {
            return id_body_part;
        }

        public String getName() {
            return name;
        }

        public String getAbout() {
            return about;
        }

        public String getImage() {
            return image;
        }

        public String getTag_color() {
            return tag_color;
        }

        public int getStatus() {
            return status;
        }

        public Exercises[] getExercises() {
            return exercises;
        }

    }


    public static class ExercisesInfo implements Serializable {
        private int id;
        private String name;
        private String info_about;
        private int group_id;
        private int type;
        private double min_reps;
        private double max_reps;
        private double step_reps;
        private double step_timer;
        private double step_sets;
        private double step_time_relax;
        private double step_weight;
        private double max_sets;
        private double min_sets;
        private double max_timer;
        private double min_timer;
        private double min_relax;
        private double max_relax;
        private double max_weight;
        private double min_weight;
        private String last_update;
        private int status;
        private String[] images;
        private String[] videos;




        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getInfoAbout() {
            return info_about;
        }

        public int getGroup_id() {
            return group_id;
        }

        public int getType() {
            return type;
        }

        public double getMin_reps() {
            return min_reps;
        }

        public double getMax_reps() {
            return max_reps;
        }

        public double getStep_reps() {
            return step_reps;
        }

        public double getStep_timer() {
            return step_timer;
        }

        public double getStep_sets() {
            return step_sets;
        }

        public double getStep_time_relax() {
            return step_time_relax;
        }

        public double getStep_weight() {
            return step_weight;
        }

        public double getMax_sets() {
            return max_sets;
        }

        public double getMin_sets() {
            return min_sets;
        }

        public double getMax_timer() {
            return max_timer;
        }

        public double getMin_timer() {
            return min_timer;
        }

        public double getMin_relax() {
            return min_relax;
        }

        public double getMax_relax() {
            return max_relax;
        }

        public double getMax_weight() {
            return max_weight;
        }

        public double getMin_weight() {
            return min_weight;
        }

        public String getLast_update() {
            return last_update;
        }

        public String[] getImages() {return images;}

        public String[] getVideos() {return videos;}




    }

    public static class Exercises implements Serializable {
        private int id;
        private int id_muscle;
        private int id_exercise;
        private int priority;
        private String last_update;
        private int status;


        public int getId() {
            return id;
        }

        public int getIdMuscule() {
            return id_muscle;
        }

        public int getIdexercise() {
            return id_exercise;
        }

        public int getPriority() {
            return priority;
        }

        public String getLast_update() {
            return last_update;
        }


    }
}
