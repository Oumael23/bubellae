package com.univpoitiers.bubellea.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.univpoitiers.bubellea.R;
import com.univpoitiers.bubellea.allergies.AllergiesFragment;
import com.univpoitiers.bubellea.budget.BudgetPreferenceFragment;
import com.univpoitiers.bubellea.drinkcomplexity.DrinkComplexityFragment;
import com.univpoitiers.bubellea.drinkgoal.DrinkGoalFragment;
import com.univpoitiers.bubellea.fruityflavorpreferences.FruityAndTexturePreferenceFragment;
import com.univpoitiers.bubellea.sugarpreferences.PreferencesFragment;
import com.univpoitiers.bubellea.teapreferences.TeaPreferencesFragment;
import com.univpoitiers.bubellea.texturepreference.CombinedPreferenceFragment;

public class FormActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private Button previousButton;

    private Button buttonResult;

    private Button nextButton;

    private Vibrator vibrator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        viewPager = findViewById(R.id.viewPager);
        previousButton = findViewById(R.id.previousButton);
        buttonResult = findViewById(R.id.buttonResult);
        nextButton = findViewById(R.id.nextButton);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Configurer l'adaptateur pour le ViewPager
        FormPagerAdapter adapter = new FormPagerAdapter(this, viewPager);
        viewPager.setAdapter(adapter);

        // Gérer le clic sur le bouton "Précédent"
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Vérifier si on peut revenir en arrière
                if (viewPager.getCurrentItem() > 0) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                }
            }
        });

        // Ajouter un listener pour afficher "Quitter" seulement à la dernière étape
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // Vérifier si l'utilisateur est à la dernière page
                if (position == adapter.getItemCount() - 1) {
                    nextButton.setVisibility(View.GONE);
                    buttonResult.setVisibility(View.VISIBLE); // Afficher le bouton
                } else {
                    if (position == 0) {
                        previousButton.setVisibility(View.GONE);
                    } else {
                        previousButton.setVisibility(View.VISIBLE);
                    }
                    buttonResult.setVisibility(View.GONE); // Cacher le bouton
                }
            }
        });



        // Gestionnaire d'événement pour le bouton Résultat
        buttonResult.setOnClickListener(v -> {
            if (vibrator != null) {
                vibrator.vibrate(200);
            }
            showResults();
        });


        // Gérer le clic sur le bouton "Suivant"
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allQuestionsAnswered()) {
                    // Passer au formulaire suivant
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                } else {
                    // Afficher un Toast si toutes les questions ne sont pas répondues
                    Toast.makeText(FormActivity.this, getString(R.string.toast_all_questions), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean allQuestionsAnswered() {
        int currentItem = viewPager.getCurrentItem();

        // Récupérer l'adaptateur
        FormPagerAdapter adapter = (FormPagerAdapter) viewPager.getAdapter();

        // Vérifier si l'adaptateur est valide
        if (adapter != null) {
            Fragment fragment = adapter.getFragmentAt(currentItem); // Créer le fragment
            if (fragment instanceof TeaPreferencesFragment) {
                return ((TeaPreferencesFragment) fragment).allRadioGroupsChecked();
            } else if (fragment instanceof FruityAndTexturePreferenceFragment) {
                return ((FruityAndTexturePreferenceFragment) fragment).allAnswersChecked();
            } else if (fragment instanceof PreferencesFragment) {
                return ((PreferencesFragment) fragment).allRadioGroupsChecked();
            } else if (fragment instanceof CombinedPreferenceFragment) {
                return ((CombinedPreferenceFragment) fragment).allAnswersChecked();
            } else if (fragment instanceof AllergiesFragment) {
                return ((AllergiesFragment) fragment).allAnswersChecked();
            } else if (fragment instanceof DrinkComplexityFragment) {
                return ((DrinkComplexityFragment) fragment).allAnswersChecked();
            } else if (fragment instanceof BudgetPreferenceFragment) {
                return ((BudgetPreferenceFragment) fragment).allAnswersChecked();
            } else if (fragment instanceof DrinkGoalFragment) {
                return ((DrinkGoalFragment) fragment).allAnswersChecked();
            }
        }

        return false; // Retourne false si l'adaptateur est nul ou si aucune réponse n'est donnée
    }

    private void showResults() {
        StringBuilder results = new StringBuilder();

        // Récupérer les réponses des fragments
        FormPagerAdapter adapter = (FormPagerAdapter) viewPager.getAdapter();

        for (int i = 0; i < adapter.getItemCount(); i++) {
            Fragment fragment = adapter.getFragmentAt(i);
            if (fragment != null && fragment.isAdded()) {
                if (fragment instanceof TeaPreferencesFragment) {
                    results.append("<b>").append(getString(R.string.tea_preference)).append("</b><br>").append(((TeaPreferencesFragment) fragment).getSelectedPreferences()).append("<br>");
                } else if (fragment instanceof FruityAndTexturePreferenceFragment) {
                    results.append("<b>").append(getString(R.string.fruit_texture_intensity_question)).append("</b><br>").append(((FruityAndTexturePreferenceFragment) fragment).getSelectedPreferences()).append("<br>");
                } else if (fragment instanceof PreferencesFragment) {
                    results.append("<b>").append(getString(R.string.sugar_preference)).append("</b><br>").append(((PreferencesFragment) fragment).getSelectedPreferences()).append("<br>");
                } else if (fragment instanceof CombinedPreferenceFragment) {
                    results.append("<b>").append(getString(R.string.combined_preferences)).append("</b><br>").append(((CombinedPreferenceFragment) fragment).getSelectedPreferences()).append("<br>");
                } else if (fragment instanceof AllergiesFragment) {
                    results.append("<b>").append(getString(R.string.allergies_preference)).append("</b><br>").append(((AllergiesFragment) fragment).getSelectedPreferences()).append("<br>");
                } else if (fragment instanceof DrinkComplexityFragment) {
                    results.append("<b>").append(getString(R.string.drink_complexity_question)).append("</b><br>").append(((DrinkComplexityFragment) fragment).getSelectedPreferences()).append("<br>");
                } else if (fragment instanceof BudgetPreferenceFragment) {
                    results.append("<b>").append(getString(R.string.budget_preference)).append("</b><br>").append(((BudgetPreferenceFragment) fragment).getSelectedPreferences()).append("<br>");
                } else if (fragment instanceof DrinkGoalFragment) {
                    results.append("<b>").append(getString(R.string.drink_goal)).append("</b><br>").append(((DrinkGoalFragment) fragment).getSelectedPreferences()).append("<br>");
                }
            }
        }

        // Passer les résultats à l'activité ResultActivity
        Intent intent = new Intent(FormActivity.this, ResultActivity.class);
        intent.putExtra("RESULTS", results.toString());
        startActivity(intent);
    }

}
