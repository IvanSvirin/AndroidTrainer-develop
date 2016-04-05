package ru.extremefitness.fitness_trainer.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import ru.extremefitness.fitness_trainer.R;
import ru.extremefitness.fitness_trainer.UserPreferences;
import ru.extremefitness.fitness_trainer.Utils;
import ru.extremefitness.fitness_trainer.loader.LoaderService;
import ru.extremefitness.fitness_trainer.loader.UrlLoader;
import ru.extremefitness.fitness_trainer.models.RootContainer;
import ru.extremefitness.fitness_trainer.models.login.LoginRequestContainer;
import ru.extremefitness.fitness_trainer.network.LoaderRequestQueue;
import ru.extremefitness.fitness_trainer.network.ModelsEnum;
import ru.extremefitness.fitness_trainer.network.NetworkDispatcher;
import ru.extremefitness.fitness_trainer.ui.settings.ScreenSettingsActivity;
import ru.extremefitness.fitness_trainer.ui.widgets.CircleImageView;
import ru.extremefitness.fitness_trainer.utils.SignOutHelper;

/**
 * Created by Krylov on 06.09.2015.
 */

public class MyDashBoardModelView extends BaseViewModel {

    enum ViewModes {
        LOAD {
            @Override
            void apply(MyDashBoardModelView viewModel) {
                viewModel.showProgress();
            }
        },
        CONTENT {
            @Override
            void apply(MyDashBoardModelView viewModel) {
                viewModel.showContent();
            }
        };

        abstract void apply(final MyDashBoardModelView viewModel);
    }

    private FrameLayout miniFrameLayout;
    private TextView weightViewValue;
    private TextView workoutViewValue;
    private View view;
    private View content;
    private TextView nameView;
    private ProgressBar progressBar;
    private ProgressBar imageProgressBar;
    private CircleImageView circleImageView;
    private ImageView backgroundImageView;
    private LoaderService loaderService;

    MyDashBoardModelView(final BaseActivity activity) {
        super(activity);
    }

    private void showContent() {
        progressBar.setVisibility(View.GONE);
        content.setVisibility(View.VISIBLE);
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        content.setVisibility(View.GONE);
    }


    void loadUserData() {
        LoginRequestContainer.UserData userData = UserPreferences.getUserData(activity);
        nameView.setText(userData.getFirstName() + " " + userData.getLastName());

        final ImageLoader imageLoader = LoaderRequestQueue.getInstance(activity).getImageLoader();
        imageLoader.get(userData.getAvatar(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                setPhotos(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        workoutViewValue.setText(userData.getHeight());
        weightViewValue.setText(userData.getWeight());

    }

    public void setLoaderService(LoaderService loaderService) {
        this.loaderService = loaderService;
        loadUserData();
    }

    private void setPhotos(final Bitmap photo) {
        circleImageView.setImageBitmap(photo);
        imageProgressBar.setVisibility(View.GONE);
        backgroundImageView.setImageBitmap(photo);
        miniFrameLayout.setBackgroundColor(getDominantColor(photo));
    }

    public static int getDominantColor(Bitmap bitmap) {
        if (null == bitmap) return Color.TRANSPARENT;

        int redBucket = 0;
        int greenBucket = 0;
        int blueBucket = 0;
        int alphaBucket = 0;

        boolean hasAlpha = bitmap.hasAlpha();
        int pixelCount = bitmap.getWidth() * bitmap.getHeight();
        int[] pixels = new int[pixelCount];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        for (int y = 0, h = bitmap.getHeight(); y < h; y++) {
            for (int x = 0, w = bitmap.getWidth(); x < w; x++) {
                int color = pixels[x + y * w]; // x + y * width
                redBucket += (color >> 16) & 0xFF; // Color.red
                greenBucket += (color >> 8) & 0xFF; // Color.greed
                blueBucket += (color & 0xFF); // Color.blue
                if (hasAlpha) alphaBucket += (color >>> 24); // Color.alpha
            }
        }

        return Color.argb(
                (hasAlpha) ? (alphaBucket / pixelCount) : 255,
                redBucket / pixelCount,
                greenBucket / pixelCount,
                blueBucket / pixelCount);
    }


    public void loaderOnReceive(Intent intent) {

        final String action = intent.getAction();

        if (LoaderService.ACTION_LOAD_COMPLETE.equals(action)) {
            setPhotos(loaderService.getCurrentBitmap());
            imageProgressBar.setVisibility(View.GONE);
        } else if (LoaderService.ACTION_LOAD_ERROR.equals(action)) {

        } else if (LoaderService.ACTION_UPDATE_PROGRESS.equals(action)) {
            final String id = intent.getStringExtra(LoaderService.EXTRA_PROGRESS_ID);
            if (UrlLoader.UrlIds.MAIN_AVATAR.name().equals(id)) {
                final int progress = intent.getIntExtra(UrlLoader.EXTRA_URL, -1);
                if (progress != -1) {
                    imageProgressBar.setProgress(progress);
                }
            }
        } else {
            throw new IllegalArgumentException("Not support broadcast action, " + action);
        }
    }


    @Override
    protected void initViews() {
        view = View.inflate(activity, R.layout.screen_main, null);
        miniFrameLayout = (FrameLayout) view.findViewById(R.id.mini_framelayout);
        imageProgressBar = (ProgressBar) view.findViewById(R.id.imageProgressBar);
        circleImageView = (CircleImageView) view.findViewById(R.id.circle_photo);
        backgroundImageView = (ImageView) view.findViewById(R.id.background_photo);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        nameView = (TextView) view.findViewById(R.id.name);
        TextView weightView = (TextView) view.findViewById(R.id.weight_view);
        TextView workoutView = (TextView) view.findViewById(R.id.workout_view);
        workoutViewValue = (TextView) view.findViewById(R.id.workout_view_value);
        weightViewValue = (TextView) view.findViewById(R.id.weight_view_value);
        content = view.findViewById(R.id.content);
        ImageButton settingsButton = (ImageButton) view.findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScreenSettingsActivity.start(activity);
            }
        });


        final LoginRequestContainer.UserData userData = UserPreferences.getUserData(activity);

        workoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.showDialogPicker(activity, Utils.HEIGHT, new Utils.NumberPickerResultListerner() {
                    @Override
                    public void onResult(String result) {
                        workoutViewValue.setText(result);
                    }
                });
            }
        });

        weightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.showDialogPicker(activity, Utils.WEIGHT, new Utils.NumberPickerResultListerner() {
                    @Override
                    public void onResult(String result) {
                        weightViewValue.setText(result);
                    }
                });
            }
        });

        circleImageView.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Выберите действие")
                        .setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                activity.startActivityForResult(loaderService.startLoad(LoaderService.PhotoSource.CAMERA), 0);
                            }
                        })
                        .setPositiveButton("Store", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                activity.startActivityForResult(loaderService.startLoad(LoaderService.PhotoSource.STORE), 0);
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();
            }
        }

        );

    }



    /*public void saveChanges() {

        final String height = workoutViewValue.getText().toString();
        final String weight = weightViewValue.getText().toString();

        final Context context = activity;

        new Thread(new Runnable() {
            @Override
            public void run() {
                LoginRequestContainer.UserData userData = UserPreferences.getUserData(context);
                userData.setHeight(height.replaceAll("\\D+", ""));
                userData.setWeight(weight.replaceAll("\\D+", ""));


                NetworkDispatcher.invoke(ModelsEnum.LOGIN.with(userData).with("action", "update"),
                        new Response.Listener<RootContainer>() {
                            @Override
                            public void onResponse(RootContainer response) {
                                if (response.isSuccess()) {
                                    final LoginRequestContainer.UserData userData = response.getData();
                                    UserPreferences.saveUserData(context, userData);
                                    MainActivity.sendUpdateUser(context);
                                }
                            }
                        }, null);
            }
        }).start();

    }*/

    public void saveChanges() {

        if (!SignOutHelper.isLogin(activity)) {
            return;
        }

        final Context context = activity;

        new Thread(new Runnable() {
            @Override
            public void run() {
                NetworkDispatcher.invoke(ModelsEnum.UPDATE_SETTINGS.with(
                                "id", UserPreferences.getUserData(context).getId(),
                                "avatar", UserPreferences.getUserData(context).getAvatar(),
                                "height", UserPreferences.getUserData(context).getHeight().replaceAll("\\D+", ""),
                                "weight", UserPreferences.getUserData(context).getWeight().replaceAll("\\D+", ""),
                                "action", "update"
                        ),
                        new Response.Listener<RootContainer>() {
                            @Override
                            public void onResponse(RootContainer response) {
                                if (response.isSuccess()) {

                                    final String avatar = UserPreferences.getUserData(context).getAvatar();

                                    final LoginRequestContainer.UserData userData = response.getData();
                                    userData.setAvatar(avatar);
                                    UserPreferences.saveUserData(context, userData);
                                    MainActivity.sendUpdateUser(context);
                                }
                            }
                        }, null);
            }
        }).start();
    }


    public View getView() {
        return view;
    }
}



