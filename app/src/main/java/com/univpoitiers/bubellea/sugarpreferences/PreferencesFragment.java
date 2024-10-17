package com.univpoitiers.bubellea.sugarpreferences;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.univpoitiers.bubellea.R;
import com.univpoitiers.bubellea.log.LogManager;

public class PreferencesFragment extends Fragment {

    private ViewPager2 viewPager;

    private RadioGroup radioGroupCalories;
    private RadioGroup radioGroupMilk;

    private SeekBar sugarSeekBar;

    private TextView sugarValueTextView;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preferences, container, false);

        TextView titleTextView = view.findViewById(R.id.titleTextView);
        titleTextView.setText(R.string.drink_preferences_title);

        radioGroupCalories = view.findViewById(R.id.radioGroupCalories);
        radioGroupMilk = view.findViewById(R.id.radioGroupMilk);
        sugarSeekBar = view.findViewById(R.id.sugarSeekBar);
        sugarValueTextView = view.findViewById(R.id.sugarValueTextView);
        LogManager.logEvent("PreferencesFragment est cree");

//        setupRadioGroupListener(radioGroupCalories);
//        setupRadioGroupListener(radioGroupMilk);

        sugarSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sugarValueTextView.setText(String.valueOf(progress)); // Mettre à jour le TextView avec la valeur actuelle
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        return view;
    }

    private void setupRadioGroupListener(RadioGroup radioGroup) {
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (allRadioGroupsChecked()) {
                // Passer à la page suivante si toutes les réponses sont sélectionnées
                if (viewPager != null) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }
            }
        });
    }

    public boolean allRadioGroupsChecked() {
        return radioGroupCalories.getCheckedRadioButtonId() != -1 &&
                radioGroupMilk.getCheckedRadioButtonId() != -1;
    }

    public String getSelectedPreferences() {
        StringBuilder selectedPreferences = new StringBuilder();

        // Obtenir la préférence pour les calories
        int selectedCaloriesId = radioGroupCalories.getCheckedRadioButtonId();
        if (selectedCaloriesId != -1) {
            String selectedCalories = ((TextView) radioGroupCalories.findViewById(selectedCaloriesId)).getText().toString();
            selectedPreferences.append(getString(R.string.calories)).append(getString(R.string.complexity_selected)).append(selectedCalories).append("<br>");
        }

        // Obtenir la préférence pour le lait
        int selectedMilkId = radioGroupMilk.getCheckedRadioButtonId();
        if (selectedMilkId != -1) {
            String selectedMilk = ((TextView) radioGroupMilk.findViewById(selectedMilkId)).getText().toString();
            selectedPreferences.append(getString(R.string.milk)).append(getString(R.string.complexity_selected)).append(selectedMilk).append("<br>");
        }

        // Obtenir la préférence pour le sucre
        int sugarValue = sugarSeekBar.getProgress(); // Obtenir la valeur actuelle du SeekBar
        selectedPreferences.append(getString(R.string.sugar)).append(getString(R.string.complexity_selected)).append(sugarValue).append("%<br>");

        return selectedPreferences.toString();
    }

    public void setViewPager(ViewPager2 viewPager) {
        this.viewPager = viewPager;
    }
}
