package ru.extremefitness.fitness_trainer.models;

/**
 * Created by Osipova Ekaterina on 02.03.2016.
 */
public class ExtremeRequest {
    String action;
    String platform;
    String router;
    String version;

    public ExtremeRequest(String action, String platform, String router, String version) {
        this.action = action;
        this.platform = platform;
        this.router = router;
        this.version = version;
    }
}
