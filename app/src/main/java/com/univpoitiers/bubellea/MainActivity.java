package com.univpoitiers.bubellea;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.univpoitiers.bubellea.activities.FormObjectiveActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText firstNameEditText;
    private Button continueButton;
    private Button menuButton;
    private ImageView menuImageView;
    private ImageView logoImageView;

    private Switch languageSwitch;
    private TextView currentLanguageTextView;


    private static final String PREFS_NAME = "language_prefs";
    private static final String LANGUAGE_KEY = "selected_language";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        menuImageView = findViewById(R.id.menuImageView);
        logoImageView = findViewById(R.id.logoImageView);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        continueButton = findViewById(R.id.continueButton);
        menuButton = findViewById(R.id.menuButton);
        languageSwitch = findViewById(R.id.languageSwitch);
        currentLanguageTextView = findViewById(R.id.currentLanguageTextView);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String savedLanguage = prefs.getString(LANGUAGE_KEY, "fr"); // Défaut : français
        setLocale(savedLanguage);

        languageSwitch.setChecked(savedLanguage.equals("en"));

        languageSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                setLocale("en"); // Changer en anglais
            } else {
                setLocale("fr"); // Changer en français
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameEditText.getText().toString().trim();

                // Vérifie si le champ est vide
                if (firstName.isEmpty()) {
                    Toast.makeText(MainActivity.this, getString(R.string.error_empty_name), Toast.LENGTH_SHORT).show();
                } else {
                    // Passer à l'activité suivante (FormObjectiveActivity)
                    Intent intent = new Intent(MainActivity.this, FormObjectiveActivity.class);
                    intent.putExtra("USER_FIRST_NAME", firstName);  // Envoyer le prénom à la prochaine activité
                    startActivity(intent);
                }
            }
        });

        // Bouton "Visiter le menu", ouverture d'une page Web externe
        menuButton.setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://static.wixstatic.com/media/8103a5_6660cbc6352940ddb9e8e01f03ab2757~mv2.jpg"));
            startActivity(browserIntent);
        });
    }

    private void setLocale(String languageCode) {
        // Obtenir la langue actuelle
        String currentLanguage = getResources().getConfiguration().locale.getLanguage();

        // Ne redémarrer l'activité que si la langue a changé
        if (!currentLanguage.equals(languageCode)) {
            Locale locale = new Locale(languageCode);
            Locale.setDefault(locale);

            Configuration config = new Configuration();

            // Utiliser LocaleList pour API 24+
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                config.setLocales(new android.os.LocaleList(locale));
            } else {
                config.locale = locale;
            }

            getResources().updateConfiguration(config, getResources().getDisplayMetrics());

            // Sauvegarder la langue sélectionnée dans SharedPreferences
            SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
            editor.putString(LANGUAGE_KEY, languageCode);
            editor.apply(); // Appliquer les modifications

            // Redémarrer l'activité pour appliquer la langue
            Intent refresh = new Intent(this, MainActivity.class);
            startActivity(refresh);
            finish();  // Terminer l'activité actuelle pour éviter les empilements
        }

        // Mettre à jour le texte pour la langue actuelle
        updateLanguageTextView(new Locale(languageCode));
    }



    // Fonction pour mettre à jour le TextView avec la langue actuelle
    private void updateLanguageTextView(Locale locale) {
        String language = locale.getDisplayLanguage(locale);
        currentLanguageTextView.setText(getString(R.string.current_language_label) + " " + language);
    }
}
