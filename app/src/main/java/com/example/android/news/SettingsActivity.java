package com.example.android.news;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


/**
 * Created by android on 2018.03.04..
 */

public class SettingsActivity extends AppCompatActivity {

    public static final String theme1Key = "theme1Key";
    public static final String theme2Key = "theme2Key";
    public static final String textColorKey = "textColorKey";
    public static final String prefsName = "settingsPref";
    public static final int defaultColor = Color.parseColor("#000000");

    private RadioGroup radioGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        //set up the RadioGroup for the Text Color Settings
        radioGroup = findViewById(R.id.radioGroup);
        int[] colorIds = {getResources().getColor(R.color.textBlue),
                getResources().getColor(R.color.textGreen), getResources().getColor(R.color.textYellow),
                getResources().getColor(R.color.textDefaultColor)};

        for (int i = 0; i < colorIds.length; i++) {

            final int color = colorIds[i];

            RadioButton button = new RadioButton(this);

            button.setText(R.string.set_color);
            button.setTextColor(color);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveTextColor(color);
                    Toast.makeText(SettingsActivity.this, R.string.settings_saved_toast, Toast.LENGTH_LONG).show();
                }
            });
            radioGroup.addView(button);

        }
    }


    //save the textcolor
    private void saveTextColor(int mColor){
        SharedPreferences sharedPrefs = this.getSharedPreferences(prefsName, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putInt(textColorKey, mColor);
        editor.apply();

    }

    // get the text color, from every class (static method)
    public static int getTextColor(Context context){
            SharedPreferences sharedPref = context.getSharedPreferences(prefsName, MODE_PRIVATE);
            int color = sharedPref.getInt(textColorKey, defaultColor);

            return color;
    }



    public static class NewsPreferenceFragment extends PreferenceFragment
            implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);

            Preference thema = findPreference(theme1Key);
            bindPreferenceSummaryToValue(thema);

            Preference thema2 = findPreference(theme2Key);
            bindPreferenceSummaryToValue(thema2);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();
            preference.setSummary(stringValue);
            return true;
        }

        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);
        }
    }
}
