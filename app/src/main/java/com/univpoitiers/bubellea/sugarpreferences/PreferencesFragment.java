package com.univpoitiers.bubellea.sugarpreferences;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.univpoitiers.bubellea.R;

public class PreferencesFragment extends Fragment {

    private ViewPager2 viewPager;

    private RadioGroup radioGroupCalories;
    private RadioGroup radioGroupMilk;
    private RadioGroup radioGroupSugar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preferences, container, false);

        TextView titleTextView = view.findViewById(R.id.titleTextView);
        titleTextView.setText("Préférences de Boisson");

        radioGroupCalories = view.findViewById(R.id.radioGroupCalories);
        radioGroupMilk = view.findViewById(R.id.radioGroupMilk);
        radioGroupSugar = view.findViewById(R.id.radioGroupSugar);

        setupRadioGroupListener(radioGroupCalories);
        setupRadioGroupListener(radioGroupMilk);
        setupRadioGroupListener(radioGroupSugar);

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
                radioGroupMilk.getCheckedRadioButtonId() != -1 &&
                radioGroupSugar.getCheckedRadioButtonId() != -1;
    }

    public String getSelectedPreferences() {
        StringBuilder selectedPreferences = new StringBuilder();

        // Obtenir la préférence pour les calories
        int selectedCaloriesId = radioGroupCalories.getCheckedRadioButtonId();
        if (selectedCaloriesId != -1) {
            String selectedCalories = ((TextView) radioGroupCalories.findViewById(selectedCaloriesId)).getText().toString();
            selectedPreferences.append("Calories sélectionnées : ").append(selectedCalories).append("<br>");
        }

        // Obtenir la préférence pour le lait
        int selectedMilkId = radioGroupMilk.getCheckedRadioButtonId();
        if (selectedMilkId != -1) {
            String selectedMilk = ((TextView) radioGroupMilk.findViewById(selectedMilkId)).getText().toString();
            selectedPreferences.append("Lait sélectionné : ").append(selectedMilk).append("<br>");
        }

        // Obtenir la préférence pour le sucre
        int selectedSugarId = radioGroupSugar.getCheckedRadioButtonId();
        if (selectedSugarId != -1) {
            String selectedSugar = ((TextView) radioGroupSugar.findViewById(selectedSugarId)).getText().toString();
            selectedPreferences.append("Sucre sélectionné : ").append(selectedSugar).append("<br>");
        }

        return selectedPreferences.toString();
    }


    public void setViewPager(ViewPager2 viewPager) {
        this.viewPager = viewPager;
    }
}
