package ru.extremefitness.fitness_trainer;

import android.app.Application;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.vk.sdk.VKSdk;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Comp on 17.08.2015.
 */
public class ExtremeTrainerApplication extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "bLndfeJS15GeKz53L2JtxcFxA";
    private static final String TWITTER_SECRET = " yEeCaALHCj0WZQHon3gYDBVFimuMw4I7rWNTQLYXtRiWySTL0h";


    public ExtremeTrainerApplication() {
        instance = this;
    }

    private static ExtremeTrainerApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        VKSdk.initialize(getApplicationContext());

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/HelveticaNeueCyr-Heavy.otf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }

    public static ExtremeTrainerApplication getInstance() {
        return instance;
    }
}
