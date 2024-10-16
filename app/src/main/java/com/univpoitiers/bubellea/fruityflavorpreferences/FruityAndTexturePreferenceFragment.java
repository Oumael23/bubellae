package com.univpoitiers.bubellea.fruityflavorpreferences;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.univpoitiers.bubellea.R;

public class FruityAndTexturePreferenceFragment extends Fragment {

    private ViewPager2 viewPager;
    private RadioGroup radioGroupTexture;
    private Spinner spinnerFruit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fruity_and_texture_preference, container, false);

        radioGroupTexture = view.findViewById(R.id.radioGroupTexture);
        spinnerFruit = view.findViewById(R.id.spinnerFruit);

        // Configurer le spinner pour les choix de fruits
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.fruit_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFruit.setAdapter(adapter);

        // Gérer le choix du fruit dans le spinner
        spinnerFruit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                checkAllAnswered();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Gérer la réponse à "Préférez-vous des boissons fruitées ou crémeuses ?"
        radioGroupTexture.setOnCheckedChangeListener((group, checkedId) -> checkAllAnswered());

        return view;
    }

    // Vérifie si toutes les questions sont répondues
    private void checkAllAnswered() {
        if (allAnswersChecked()) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        }
    }

    public boolean allAnswersChecked() {
        boolean spinner = spinnerFruit.getSelectedItemPosition() != 0;
        boolean radioButton = radioGroupTexture.getCheckedRadioButtonId() != -1;
        return spinner && radioButton;
    }

    public String getSelectedPreferences() {
        StringBuilder selectedPreferences = new StringBuilder();

        // Obtenir la préférence pour la texture
        int selectedTextureId = radioGroupTexture.getCheckedRadioButtonId();
        if (selectedTextureId != -1) {
            String selectedTexture = ((TextView) radioGroupTexture.findViewById(selectedTextureId)).getText().toString();
            selectedPreferences.append("Texture sélectionnée : ").append(selectedTexture).append("<br>");
        }

        // Obtenir la préférence pour le fruit
        String selectedFruit = (String) spinnerFruit.getSelectedItem();
        if (selectedFruit != null && !selectedFruit.equals("Choisissez un fruit")) {
            selectedPreferences.append("Fruit sélectionné : ").append(selectedFruit).append("<br>");
        }

        return selectedPreferences.toString();
    }

    public void setViewPager(ViewPager2 viewPager) {
        this.viewPager = viewPager;
    }
}
