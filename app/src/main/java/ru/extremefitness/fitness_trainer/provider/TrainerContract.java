package ru.extremefitness.fitness_trainer.provider;

import android.provider.BaseColumns;

/**
 * Created by Osipova Ekaterina on 15.02.2016.
 */
public final class TrainerContract {

    public static final String CONTENT_TYPE_APP_BASE = "extremetrainer.";

    public static final String CONTENT_TYPE_BASE = "vnd.android.cursor.dir/vnd."
            + CONTENT_TYPE_APP_BASE;

    public static final String CONTENT_ITEM_TYPE_BASE = "vnd.android.cursor.item/vnd."
            + CONTENT_TYPE_APP_BASE;

    interface BodyPartsColumns {
        String BODY_PART_ID = "body_part_id";
        String BODY_PART_NAME = "body_part_name";
        String BODY_PART_ARCHIVED = "body_part_archived";
    }

    interface MusculesColumns {
        String MUSCULE_ID = "muscule_id";
        String MUSCULE_NAME = "muscule_name";
        String MUSCULE_ARCHIVED = "muscule_archived";
        String MUSCULE_ABOUT = "muscule_about";
        String MUSCULE_BODY_PART_ID = BodyPartsColumns.BODY_PART_ID;
    }

    interface ExercisesColumns {
        String EXERCISE_ID = "exercise_id";
        String EXERCISE_NAME = "exercise_name";

        String EXERCISE_ABOUT = "exercise_about";
        /*String EXERCISE_IMAGES = "exercise_images";
        String EXERCISE_VIDEOS = "exercise_videos";

        String EXERCISE_MAX_WEIGHT = "exercise_max_weight";
        String EXERCISE_MIN_WEIGHT = "exercise_min_weight";
        String EXERCISE_STEP_WEIGHT = "exercise_step_weight";
        String EXERCISE_MAX_REPEAT = "exercise_max_repeat";
        String EXERCISE_MIN_REPEAT = "exercise_min_repeat";
        String EXERCISE_STEP_REPEAT = "exercise_step_repeat";
        String EXERCISE_MAX_SET_REPEAT = "exercise_max_set_repeat";
        String EXERCISE_MIN_SET_REPEAT = "exercise_min_set_repeat";
        String EXERCISE_STEP_SET_REPEAT = "exercise_step_set_repeat";
        String EXERCISE_MAX_TIME = "exercise_max_time";
        String EXERCISE_MIN_TIME = "exercise_min_time";
        String EXERCISE_STEP_TIME = "exercise_step_time";
        String EXERCISE_TIME_RELAX = "exercise_time_relax";*/
    }

    interface MusculesExercisesColumns {
        String ME_ID = "me_id";
        String ME_MUSCULE_ID = MusculesColumns.MUSCULE_ID;
        String ME_EXERCISE_ID = ExercisesColumns.EXERCISE_ID;
    }

    interface ExercisesBodyPartsEfficiencyColumns {
        String EBPE_ID = "ebpe_id";
        String EBPE_EXERCISE_ID = ExercisesColumns.EXERCISE_ID;
        String EBPE_BODY_PART_ID = BodyPartsColumns.BODY_PART_ID;
        String EBPE_EFFICIENCY = "ebpe_efficiency";
    }

    public static class BodyParts implements BodyPartsColumns, BaseColumns {

    }

    public static class Exercises implements ExercisesColumns, BaseColumns {

    }

    public static class Muscules implements MusculesColumns, BaseColumns {

    }

    public static class MusculesExercises implements MusculesExercisesColumns, BaseColumns {

    }

    public static class ExercisesBodyPartsEfficiency implements
            ExercisesBodyPartsEfficiencyColumns, BaseColumns {

    }
}
