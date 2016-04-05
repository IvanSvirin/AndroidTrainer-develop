package ru.extremefitness.fitness_trainer.network;

/**
 * Created: Krylov
 * Date: 16.09.2015
 * Time: 9:59
 */
public enum ParamsKeys {

    VERSION,
    PLATFORM,
    CODE,
    PHONE,
    EMAIL,
    PASSWORD,
    ACTION,
    FIRST_NAME,
    LAST_NAME,;


    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
