package ru.extremefitness.fitness_trainer.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import ru.extremefitness.fitness_trainer.R;

/**
 * Created: Krylov
 * Date: 24.09.2015
 * Time: 10:42
 */
public class SettingsListItem extends LinearLayout {

    private static final int VALUE = 0;
    private static final int SPINNER = 1;
    private final int hintRes;
    private final int type;
    private TextView title;
    private EditText value;
    private Spinner spinner;

    public SettingsListItem(Context context) {
        this(context, null);
    }

    public SettingsListItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingsListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SettingsListItem);

        type = array.getInt(R.styleable.SettingsListItem_listType, VALUE);
        if (type == VALUE) {
            View.inflate(getContext(), R.layout.settings_list_item, this);
        } else {
            View.inflate(getContext(), R.layout.settings_list_item_spinner, this);
        }

        title = (TextView) findViewById(R.id.list_item_title);
        final int titleRes = array.getResourceId(R.styleable.SettingsListItem_itemTitle, 0);
        title.setText(titleRes);

        hintRes = array.getResourceId(R.styleable.SettingsListItem_itemHint, R.string.not_determined);

        if (type == VALUE) {
            value = (EditText) findViewById(R.id.list_item_value);
            value.setFocusable(array.getBoolean(R.styleable.SettingsListItem_editable, true));

            value.setHint(hintRes);

            value.setInputType(array.getInt(R.styleable.SettingsListItem_android_inputType, InputType.TYPE_TEXT_FLAG_CAP_SENTENCES));

        } else {
            spinner = (Spinner) findViewById(R.id.list_item_spinner);
        }
    }

    public void setTitle(final CharSequence title) {
        this.title.setText(title);
    }

    public void setTitle(final int titleRes) {
        this.title.setText(titleRes);
    }

    public void setValue(final CharSequence value) {

        if (type == VALUE) {
            this.value.setText(value);

            final String hint = TextUtils.isEmpty(value) ? getContext().getString(hintRes) : "";
            this.value.setHint(hint);
        }
    }

    public void setSpinner(final int position, final String... args) {
        spinner.setAdapter(new SpinnerAdapter(getContext(), args));
        spinner.setSelection(position);
    }

    public String getValue() {
        if (type == VALUE) {
            return value.getText().toString();
        } else {
            return (String) spinner.getSelectedItem();
        }
    }

    public void setInputType(final int type) {
        value.setInputType(type);
    }

    public void setValuesListener(OnTouchListener valuesListener) {
        value.setOnTouchListener(valuesListener);
    }

    private static class SpinnerAdapter extends ArrayAdapter<String> {
        private Context mContext;

        public SpinnerAdapter(Context context, String[] list) {
            super(context, 0, list);
            mContext = context;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = View.inflate(mContext, android.R.layout.simple_list_item_1, null);
            }
            String s = getItem(position);
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(s);

            return convertView;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.support_simple_spinner_dropdown_item, null);
            }
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(getItem(position));
            return convertView;
        }
    }

}
