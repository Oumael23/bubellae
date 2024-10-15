package com.univpoitiers.bubellea.budget;

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

public class BudgetPreferenceFragment extends Fragment {

    private ViewPager2 viewPager;
    private RadioGroup radioGroupBudget, radioGroupFrequency, radioGroupToppingBudget;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget_preference, container, false);

        viewPager = getActivity().findViewById(R.id.viewPager);
        radioGroupBudget = view.findViewById(R.id.radioGroupBudget);
        radioGroupFrequency = view.findViewById(R.id.radioGroupFrequency);
        radioGroupToppingBudget = view.findViewById(R.id.radioGroupToppingBudget);

        RadioGroup.OnCheckedChangeListener checkAllAnsweredListener = (group, checkedId) -> checkAllAnswered();
        radioGroupBudget.setOnCheckedChangeListener(checkAllAnsweredListener);
        radioGroupFrequency.setOnCheckedChangeListener(checkAllAnsweredListener);
        radioGroupToppingBudget.setOnCheckedChangeListener(checkAllAnsweredListener);

        return view;
    }

    private void checkAllAnswered() {
        if (allAnswersChecked()) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        }
    }

    public boolean allAnswersChecked() {
        return radioGroupBudget.getCheckedRadioButtonId() != -1 &&
                radioGroupFrequency.getCheckedRadioButtonId() != -1 &&
                radioGroupToppingBudget.getCheckedRadioButtonId() != -1;
    }

    public String getSelectedPreferences() {
        StringBuilder selectedPreferences = new StringBuilder();

        // Obtenir le budget
        int selectedBudgetId = radioGroupBudget.getCheckedRadioButtonId();
        if (selectedBudgetId != -1) {
            String selectedBudget = ((TextView) radioGroupBudget.findViewById(selectedBudgetId)).getText().toString();
            selectedPreferences.append("Budget sélectionné : ").append(selectedBudget).append("<br>");
        }

        // Obtenir la fréquence
        int selectedFrequencyId = radioGroupFrequency.getCheckedRadioButtonId();
        if (selectedFrequencyId != -1) {
            String selectedFrequency = ((TextView) radioGroupFrequency.findViewById(selectedFrequencyId)).getText().toString();
            selectedPreferences.append("Fréquence sélectionnée : ").append(selectedFrequency).append("<br>");
        }

        // Obtenir le budget pour les toppings
        int selectedToppingBudgetId = radioGroupToppingBudget.getCheckedRadioButtonId();
        if (selectedToppingBudgetId != -1) {
            String selectedToppingBudget = ((TextView) radioGroupToppingBudget.findViewById(selectedToppingBudgetId)).getText().toString();
            selectedPreferences.append("Budget pour toppings sélectionné : ").append(selectedToppingBudget).append("<br>");
        }

        return selectedPreferences.toString();
    }

    public void setViewPager(ViewPager2 viewPager) {
        this.viewPager = viewPager;
    }
}
