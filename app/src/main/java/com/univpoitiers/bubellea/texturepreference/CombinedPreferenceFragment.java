package com.univpoitiers.bubellea.texturepreference;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.univpoitiers.bubellea.R;

public class CombinedPreferenceFragment extends Fragment {

    private ViewPager2 viewPager;

    private CheckBox checkGelatinous, checkCrunchy, checkCreamy, checkLiquid;

    private RadioGroup radioGroupToppingsQuantity, radioGroupToppingsSweetness;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_combined_preferences, container, false);

        viewPager = getActivity().findViewById(R.id.viewPager);
        checkGelatinous = view.findViewById(R.id.checkGelatinous);
        checkCrunchy = view.findViewById(R.id.checkCrunchy);
        checkCreamy = view.findViewById(R.id.checkCreamy);
        checkLiquid = view.findViewById(R.id.checkLiquid);
        radioGroupToppingsQuantity = view.findViewById(R.id.radioGroupToppingsQuantity);
        radioGroupToppingsSweetness = view.findViewById(R.id.radioGroupToppingsSweetness);

        // Set listeners for each RadioGroup
        radioGroupToppingsQuantity.setOnCheckedChangeListener((group, checkedId) -> checkAllAnswered());
        radioGroupToppingsSweetness.setOnCheckedChangeListener((group, checkedId) -> checkAllAnswered());

        View.OnClickListener checkBoxListener = v -> checkAllAnswered();
        checkGelatinous.setOnClickListener(checkBoxListener);
        checkCrunchy.setOnClickListener(checkBoxListener);
        checkCreamy.setOnClickListener(checkBoxListener);
        checkLiquid.setOnClickListener(checkBoxListener);

        return view;
    }

    private void checkAllAnswered() {
        if (allAnswersChecked()) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        }
    }

    public boolean allAnswersChecked() {
        boolean anyTextureSelected = checkGelatinous.isChecked() || checkCrunchy.isChecked() || checkCreamy.isChecked() || checkLiquid.isChecked();
        boolean toppingsQuantitySelected = radioGroupToppingsQuantity.getCheckedRadioButtonId() != -1;
        boolean toppingsSweetnessSelected = radioGroupToppingsSweetness.getCheckedRadioButtonId() != -1;
        return anyTextureSelected && toppingsQuantitySelected && toppingsSweetnessSelected;
    }

    public String getSelectedPreferences() {
        StringBuilder selectedPreferences = new StringBuilder();

        // Obtenir les textures sélectionnées
        if (checkGelatinous.isChecked()) {
            selectedPreferences.append("Texture gélatineuse sélectionnée.\n");
        }
        if (checkCrunchy.isChecked()) {
            selectedPreferences.append("Texture croquante sélectionnée.\n");
        }
        if (checkCreamy.isChecked()) {
            selectedPreferences.append("Texture crémeuse sélectionnée.\n");
        }
        if (checkLiquid.isChecked()) {
            selectedPreferences.append("Texture liquide sélectionnée.\n");
        }

        // Obtenir la quantité de garnitures sélectionnée
        int selectedQuantityId = radioGroupToppingsQuantity.getCheckedRadioButtonId();
        if (selectedQuantityId != -1) {
            String selectedQuantity = ((TextView) radioGroupToppingsQuantity.findViewById(selectedQuantityId)).getText().toString();
            selectedPreferences.append("Quantité de garnitures sélectionnée : ").append(selectedQuantity).append("<br>");
        }

        // Obtenir la douceur des garnitures sélectionnée
        int selectedSweetnessId = radioGroupToppingsSweetness.getCheckedRadioButtonId();
        if (selectedSweetnessId != -1) {
            String selectedSweetness = ((TextView) radioGroupToppingsSweetness.findViewById(selectedSweetnessId)).getText().toString();
            selectedPreferences.append("Douceur de garnitures sélectionnée : ").append(selectedSweetness).append("<br>");
        }

        return selectedPreferences.toString();
    }


    public void setViewPager(ViewPager2 viewPager) {
        this.viewPager = viewPager;
    }
}
