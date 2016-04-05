package ru.extremefitness.fitness_trainer.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Osipova Ekaterina on 10.02.2016.
 */
public class ProgramsResponse {
    List<Program> programs;

    public ProgramsResponse() {
        this.programs = new ArrayList<>();
    }

    public List<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(List<Program> programs) {
        this.programs = programs;
    }
}
