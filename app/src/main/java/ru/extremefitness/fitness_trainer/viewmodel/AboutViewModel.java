package ru.extremefitness.fitness_trainer.viewmodel;

import android.view.View;

import ru.extremefitness.fitness_trainer.R;
import ru.extremefitness.fitness_trainer.ui.BaseActivity;
import ru.extremefitness.fitness_trainer.ui.BaseViewModel;
import ru.extremefitness.fitness_trainer.ui.MainActivity;
import ru.extremefitness.fitness_trainer.utils.SignOutHelper;

/**
 * Created by Osipova Ekaterina on 08.02.2016.
 */
public class AboutViewModel extends BaseViewModel implements View.OnClickListener {

    private View rootView;

    public AboutViewModel(BaseActivity activity) {
        super(activity);
    }

    @Override
    protected void initViews() {
        rootView = View.inflate(activity, R.layout.activity_about, null);
        rootView.findViewById(R.id.btn_next).setOnClickListener(this);
    }

    @Override
    public View getView() {
        return rootView;
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.btn_next:
                if (SignOutHelper.isLogin(activity)) {
                    MainActivity.start(activity);
                }
                break;
        }
    }
}
