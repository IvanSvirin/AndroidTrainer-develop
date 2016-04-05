package ru.extremefitness.fitness_trainer.models;

/**
 * Created by Comp on 18.08.2015.
 */
public class VideoInfoContainer extends RootContainer {

    private VideoInfo[] data;

    @Override
    public VideoInfo[] getData() {
        return data;
    }

    public class VideoInfo {
        private String title;
        private String text;
        private String v;

        public String getTitle() {
            return title;
        }

        public String getText() {
            return text;
        }

        public String getV() {
            return v;
        }
    }
}
