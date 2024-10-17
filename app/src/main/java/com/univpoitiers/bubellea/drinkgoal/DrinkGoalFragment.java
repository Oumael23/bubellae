package com.univpoitiers.bubellea.drinkgoal;

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
import com.univpoitiers.bubellea.log.LogManager;

public class DrinkGoalFragment extends Fragment {

    private ViewPager2 viewPager;
    private RadioGroup radioGroupGoal, radioGroupNutrition, radioGroupMood;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drink_goal, container, false);

        viewPager = getActivity().findViewById(R.id.viewPager);
        radioGroupGoal = view.findViewById(R.id.radioGroupGoal);
        radioGroupNutrition = view.findViewById(R.id.radioGroupNutrition);
        radioGroupMood = view.findViewById(R.id.radioGroupMood);

        LogManager.logEvent("DrinkGoalFragment est cree");


        RadioGroup.OnCheckedChangeListener checkAllAnsweredListener = (group, checkedId) -> checkAllAnswered();
        radioGroupGoal.setOnCheckedChangeListener(checkAllAnsweredListener);
        radioGroupNutrition.setOnCheckedChangeListener(checkAllAnsweredListener);
        radioGroupMood.setOnCheckedChangeListener(checkAllAnsweredListener);

        return view;
    }

    private void checkAllAnswered() {
        if (allAnswersChecked()) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        }
    }

    public boolean allAnswersChecked() {
        return radioGroupGoal.getCheckedRadioButtonId() != -1 &&
                radioGroupNutrition.getCheckedRadioButtonId() != -1 &&
                radioGroupMood.getCheckedRadioButtonId() != -1;
    }

    public String getSelectedPreferences() {
        StringBuilder selectedPreferences = new StringBuilder();

        // Obtenir l'objectif
        int selectedGoalId = radioGroupGoal.getCheckedRadioButtonId();
        if (selectedGoalId != -1) {
            String selectedGoal = ((TextView) radioGroupGoal.findViewById(selectedGoalId)).getText().toString();
            selectedPreferences.append(getString(R.string.goal_selected)).append(selectedGoal).append("<br>");
        }

        // Obtenir la préférence nutritionnelle
        int selectedNutritionId = radioGroupNutrition.getCheckedRadioButtonId();
        if (selectedNutritionId != -1) {
            String selectedNutrition = ((TextView) radioGroupNutrition.findViewById(selectedNutritionId)).getText().toString();
            selectedPreferences.append(getString(R.string.nutrition_selected)).append(selectedNutrition).append("<br>");
        }

        // Obtenir l'humeur
        int selectedMoodId = radioGroupMood.getCheckedRadioButtonId();
        if (selectedMoodId != -1) {
            String selectedMood = ((TextView) radioGroupMood.findViewById(selectedMoodId)).getText().toString();
            selectedPreferences.append(getString(R.string.mood_selected)).append(selectedMood).append("<br>");
        }

        return selectedPreferences.toString();
    }

    public void setViewPager(ViewPager2 viewPager) {
        this.viewPager = viewPager;
    }
}
