package com.univpoitiers.bubellea.drinkcomplexity;

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

public class DrinkComplexityFragment extends Fragment {

    private ViewPager2 viewPager;
    private RadioGroup radioGroupComplexity, radioGroupBoldness, radioGroupRecipePreference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drink_complexity, container, false);

        viewPager = getActivity().findViewById(R.id.viewPager);
        radioGroupComplexity = view.findViewById(R.id.radioGroupComplexity);
        radioGroupBoldness = view.findViewById(R.id.radioGroupBoldness);
        radioGroupRecipePreference = view.findViewById(R.id.radioGroupRecipePreference);

        // Check if all questions are answered
        radioGroupComplexity.setOnCheckedChangeListener((group, checkedId) -> checkAllAnswered());
        radioGroupBoldness.setOnCheckedChangeListener((group, checkedId) -> checkAllAnswered());
        radioGroupRecipePreference.setOnCheckedChangeListener((group, checkedId) -> checkAllAnswered());

        return view;
    }

    private void checkAllAnswered() {
        if (allAnswersChecked()) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        }
    }

    public boolean allAnswersChecked() {
        return radioGroupComplexity.getCheckedRadioButtonId() != -1 &&
                radioGroupBoldness.getCheckedRadioButtonId() != -1 &&
                radioGroupRecipePreference.getCheckedRadioButtonId() != -1;
    }

    public String getSelectedPreferences() {
        StringBuilder selectedPreferences = new StringBuilder();

        // Obtenir la complexité de la boisson
        int selectedComplexityId = radioGroupComplexity.getCheckedRadioButtonId();
        if (selectedComplexityId != -1) {
            String selectedComplexity = ((TextView) radioGroupComplexity.findViewById(selectedComplexityId)).getText().toString();
            selectedPreferences.append(getString(R.string.complexity_selected)).append(selectedComplexity).append("<br>");
        }

        // Obtenir la force de la boisson
        int selectedBoldnessId = radioGroupBoldness.getCheckedRadioButtonId();
        if (selectedBoldnessId != -1) {
            String selectedBoldness = ((TextView) radioGroupBoldness.findViewById(selectedBoldnessId)).getText().toString();
            selectedPreferences.append(getString(R.string.boldness_selected)).append(selectedBoldness).append("<br>");
        }

        // Obtenir la préférence de recette
        int selectedRecipeId = radioGroupRecipePreference.getCheckedRadioButtonId();
        if (selectedRecipeId != -1) {
            String selectedRecipe = ((TextView) radioGroupRecipePreference.findViewById(selectedRecipeId)).getText().toString();
            selectedPreferences.append(getString(R.string.recipe_preference_selected)).append(selectedRecipe).append("<br>");
        }

        return selectedPreferences.toString();
    }

    public void setViewPager(ViewPager2 viewPager) {
        this.viewPager = viewPager;
    }
}
