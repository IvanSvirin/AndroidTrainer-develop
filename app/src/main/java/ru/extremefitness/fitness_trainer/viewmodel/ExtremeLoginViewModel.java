package ru.extremefitness.fitness_trainer.viewmodel;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ru.extremefitness.fitness_trainer.R;
import ru.extremefitness.fitness_trainer.network.ModelsEnum;
import ru.extremefitness.fitness_trainer.network.NetworkDispatcher;
import ru.extremefitness.fitness_trainer.network.ParamsKeys;
import ru.extremefitness.fitness_trainer.ui.BaseActivity;
import ru.extremefitness.fitness_trainer.ui.BaseViewModel;

/**
 * Created by Comp on 15.09.2015.
 */
public final class ExtremeLoginViewModel extends BaseViewModel {

    private View rootView;
    private EditText loginEditText;
    private EditText phoneEditText;
    private Button signUpButton;

    public ExtremeLoginViewModel(BaseActivity activity) {
        super(activity);
    }

    @Override
    protected void initViews() {
        rootView = View.inflate(activity, R.layout.screen_extreme_login_activity, null);
        loginEditText = (EditText) rootView.findViewById(R.id.login_edit_text);
        phoneEditText = (EditText) rootView.findViewById(R.id.phone_edit_text);
        signUpButton = (Button) rootView.findViewById(R.id.sign_up_button);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signUp() {
        final String code = loginEditText.getText().toString();
        final String phone = phoneEditText.getText().toString();
        NetworkDispatcher.invoke(ModelsEnum.LOGIN
                .with(ParamsKeys.CODE.toString(), code, ParamsKeys.PHONE.toString(), phone));
    }

    public View getView() {
        return rootView;
    }
}
