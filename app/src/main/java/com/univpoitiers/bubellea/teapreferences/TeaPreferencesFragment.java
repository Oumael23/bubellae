package com.univpoitiers.bubellea.teapreferences;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.univpoitiers.bubellea.R;
import com.univpoitiers.bubellea.log.LogManager;

public class TeaPreferencesFragment extends Fragment {

    private ViewPager2 viewPager;
    private RadioGroup radioGroupTea;
    private RadioGroup radioGroupIntensity;
    private RadioGroup radioGroupTemperature;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tea_preferences, container, false);

        TextView titleTextView = view.findViewById(R.id.titleTextView);
        titleTextView.setText(R.string.tea_preferences_title);
        LogManager.logEvent("TeaPreferencesFragment est cree");

        radioGroupTea = view.findViewById(R.id.radioGroupTea);
        radioGroupIntensity = view.findViewById(R.id.radioGroupIntensity);
        radioGroupTemperature = view.findViewById(R.id.radioGroupTemperature);

        setupRadioGroupListener(radioGroupTea);
        setupRadioGroupListener(radioGroupIntensity);
        setupRadioGroupListener(radioGroupTemperature);

        return view;
    }

    public boolean allRadioGroupsChecked() {
        return radioGroupTea.getCheckedRadioButtonId() != -1 &&
                radioGroupIntensity.getCheckedRadioButtonId() != -1 &&
                radioGroupTemperature.getCheckedRadioButtonId() != -1;
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

    public String getSelectedPreferences() {
        StringBuilder selectedPreferences = new StringBuilder();

        // Obtenir la préférence pour le type de thé
        int selectedTeaId = radioGroupTea.getCheckedRadioButtonId();
        if (selectedTeaId != -1) {
            String selectedTea = ((TextView) radioGroupTea.findViewById(selectedTeaId)).getText().toString();
            selectedPreferences.append(getString(R.string.tea)).append(getString(R.string.selected)).append(selectedTea).append("<br>");
        }

        // Obtenir la préférence pour l'intensité
        int selectedIntensityId = radioGroupIntensity.getCheckedRadioButtonId();
        if (selectedIntensityId != -1) {
            String selectedIntensity = ((TextView) radioGroupIntensity.findViewById(selectedIntensityId)).getText().toString();
            selectedPreferences.append(getString(R.string.intensity)).append(getString(R.string.selected)).append(selectedIntensity).append("<br>");
        }

        // Obtenir la préférence pour la température
        int selectedTemperatureId = radioGroupTemperature.getCheckedRadioButtonId();
        if (selectedTemperatureId != -1) {
            String selectedTemperature = ((TextView) radioGroupTemperature.findViewById(selectedTemperatureId)).getText().toString();
            selectedPreferences.append(getString(R.string.temp)).append(getString(R.string.selected)).append(selectedTemperature).append("<br>");
        }

        return selectedPreferences.toString();
    }

    public void setViewPager(ViewPager2 viewPager) {
        this.viewPager = viewPager;
        disableSwipeInViewPager(viewPager);
    }

    private void disableSwipeInViewPager(ViewPager2 viewPager) {
        viewPager.setUserInputEnabled(false); // Désactive uniquement le swipe
    }
}
