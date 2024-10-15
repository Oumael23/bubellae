package com.univpoitiers.bubellea.allergies;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.univpoitiers.bubellea.R;

public class AllergiesFragment extends Fragment {

    private ViewPager2 viewPager;
    private RadioGroup radioGroupRestrictions;
    private EditText editTextAllergies;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_allergies, container, false);

        viewPager = getActivity().findViewById(R.id.viewPager);
        radioGroupRestrictions = view.findViewById(R.id.radioGroupRestrictions);
        editTextAllergies = view.findViewById(R.id.editTextAllergies);

        // Check restrictions radio buttons
        radioGroupRestrictions.setOnCheckedChangeListener((group, checkedId) -> checkAllAnswered());

        return view;
    }

    private void checkAllAnswered() {
        if (allAnswersChecked()) {
            // Passer à la page suivante
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        }
    }

    public boolean allAnswersChecked() {
        // Vérifier si la saisie des allergies est faite et si une restriction est sélectionnée
        boolean isRestrictionSelected = radioGroupRestrictions.getCheckedRadioButtonId() != -1;
        boolean isAllergiesProvided = !TextUtils.isEmpty(editTextAllergies.getText().toString().trim());
        return isRestrictionSelected && isAllergiesProvided;
    }

    public String getSelectedPreferences() {
        StringBuilder selectedPreferences = new StringBuilder();

        // Obtenir la restriction sélectionnée
        int selectedRestrictionId = radioGroupRestrictions.getCheckedRadioButtonId();
        if (selectedRestrictionId != -1) {
            String selectedTea = ((TextView) radioGroupRestrictions.findViewById(selectedRestrictionId)).getText().toString();
            selectedPreferences.append("Restriction sélectionnée : ").append(selectedTea).append("<br>");
        }

        // Obtenir les allergies saisies
        String allergies = editTextAllergies.getText().toString().trim();
        if (!TextUtils.isEmpty(allergies)) {
            selectedPreferences.append("Allergies mentionnées : ").append(allergies).append("<br>");
        }

        return selectedPreferences.toString();
    }


    public void setViewPager(ViewPager2 viewPager) {
        this.viewPager = viewPager;
    }
}
