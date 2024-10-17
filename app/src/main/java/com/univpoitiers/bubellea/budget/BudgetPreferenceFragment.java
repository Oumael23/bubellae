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
import com.univpoitiers.bubellea.log.LogManager;

public class BudgetPreferenceFragment extends Fragment {

    private ViewPager2 viewPager;
    private RadioGroup radioGroupBudget, radioGroupFrequency, radioGroupToppingBudget;
    private TextView budgetFragmentTitle, questionTextBudget, questionTextFrequency, questionTextToppingBudget;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget_preference, container, false);

        // Initialisation des TextViews
        budgetFragmentTitle = view.findViewById(R.id.budgetFragmentTitle);
        questionTextBudget = view.findViewById(R.id.questionTextBudget);
        questionTextFrequency = view.findViewById(R.id.questionTextFrequency);
        questionTextToppingBudget = view.findViewById(R.id.questionTextToppingBudget);

        viewPager = getActivity().findViewById(R.id.viewPager);
        radioGroupBudget = view.findViewById(R.id.radioGroupBudget);
        radioGroupFrequency = view.findViewById(R.id.radioGroupFrequency);
        radioGroupToppingBudget = view.findViewById(R.id.radioGroupToppingBudget);

        // Définir les textes à partir de strings.xml
        budgetFragmentTitle.setText(getString(R.string.title_budget));
        questionTextBudget.setText(getString(R.string.budget_question));
        ((TextView) view.findViewById(R.id.radioBudgetLessThan5)).setText(getString(R.string.budget_less_than_5));
        ((TextView) view.findViewById(R.id.radioBudgetBetween5And8)).setText(getString(R.string.budget_between_5_and_8));
        ((TextView) view.findViewById(R.id.radioBudgetMoreThan8)).setText(getString(R.string.budget_more_than_8));

        questionTextFrequency.setText(getString(R.string.frequency_question));
        ((TextView) view.findViewById(R.id.radioFirstTime)).setText(getString(R.string.first_time));
        ((TextView) view.findViewById(R.id.radioOccasional)).setText(getString(R.string.occasional));
        ((TextView) view.findViewById(R.id.radioRegular)).setText(getString(R.string.regular));

        questionTextToppingBudget.setText(getString(R.string.topping_budget_question));
        ((TextView) view.findViewById(R.id.radioToppingBudget50cents)).setText(getString(R.string.topping_budget_50_cents));
        ((TextView) view.findViewById(R.id.radioToppingBudget1euro)).setText(getString(R.string.topping_budget_1_euro));
        ((TextView) view.findViewById(R.id.radioToppingBudgetNoLimit)).setText(getString(R.string.topping_budget_no_limit));

        RadioGroup.OnCheckedChangeListener checkAllAnsweredListener = (group, checkedId) -> checkAllAnswered();
        radioGroupBudget.setOnCheckedChangeListener(checkAllAnsweredListener);
        radioGroupFrequency.setOnCheckedChangeListener(checkAllAnsweredListener);
        radioGroupToppingBudget.setOnCheckedChangeListener(checkAllAnsweredListener);

        LogManager.logEvent("BudgetPreferenceFragment est cree");


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
            selectedPreferences.append(getString(R.string.budget)).append(getString(R.string.selected)).append(selectedBudget).append("<br>");
        }

        // Obtenir la fréquence
        int selectedFrequencyId = radioGroupFrequency.getCheckedRadioButtonId();
        if (selectedFrequencyId != -1) {
            String selectedFrequency = ((TextView) radioGroupFrequency.findViewById(selectedFrequencyId)).getText().toString();
            selectedPreferences.append(getString(R.string.freuency)).append(getString(R.string.selected)).append(selectedFrequency).append("<br>");
        }

        // Obtenir le budget pour les toppings
        int selectedToppingBudgetId = radioGroupToppingBudget.getCheckedRadioButtonId();
        if (selectedToppingBudgetId != -1) {
            String selectedToppingBudget = ((TextView) radioGroupToppingBudget.findViewById(selectedToppingBudgetId)).getText().toString();
            selectedPreferences.append(getString(R.string.budget_topping)).append(getString(R.string.selected)).append(selectedToppingBudget).append("<br>");
        }

        return selectedPreferences.toString();
    }

    public void setViewPager(ViewPager2 viewPager) {
        this.viewPager = viewPager;
    }
}
