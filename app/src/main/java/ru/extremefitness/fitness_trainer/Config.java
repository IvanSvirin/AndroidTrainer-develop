package ru.extremefitness.fitness_trainer;

/**
 * Created by Comp on 16.09.2015.
 */
public class Config {

    public static final String BASE_URL = "http://www.extremefitness.ru";

    public static final String VIDEO_LIST_URL = BASE_URL +
            "/api/videos.json";

    public static final String LOGIN = BASE_URL +
            "/api/et_client.php";

    public static final String ANDROID_PLATFORM = "ANDROID";

    public static final String VERSION_API = "/v1.1";

    public static final String ROUTE = "/rout.php";

    public static final String SERVER_URL = BASE_URL + "/systems/api/extreme_trainer" + VERSION_API + ROUTE;

    public static final String SERVER_URL_GET_GYM = SERVER_URL + "?router=get_gym";

    public static final String SERVER_URL_GET_AUTH = SERVER_URL + "?router=get_auth";

    public static final String SERVER_URL_GET_UPDATE = SERVER_URL + "?router=get_gym_update";
}
