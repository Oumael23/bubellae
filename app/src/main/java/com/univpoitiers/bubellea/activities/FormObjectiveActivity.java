package com.univpoitiers.bubellea.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.univpoitiers.bubellea.R;

public class FormObjectiveActivity extends AppCompatActivity {

    private TextView greetingTextView, objectiveTextView;
    private Button startFormButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_objective);

        // Récupérer le prénom passé de l'activité précédente
        String firstName = getIntent().getStringExtra("USER_FIRST_NAME");

        // Initialiser les vues
        greetingTextView = findViewById(R.id.greetingTextView);
        objectiveTextView = findViewById(R.id.objectiveTextView);
        startFormButton = findViewById(R.id.startFormButton);

        // Afficher le message de bienvenue avec le prénom
        if (firstName != null && !firstName.isEmpty()) {
            greetingTextView.setText(getString(R.string.greeting_text) + " " + firstName + " !");
        } else {
            greetingTextView.setText(getString(R.string.greeting_text));
        }

        // Définir le texte expliquant l'objectif du formulaire
        objectiveTextView.setText(getString(R.string.form_objective));

        // Gérer le clic sur le bouton "Commencer le formulaire"
        startFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormObjectiveActivity.this, FormActivity.class);
                intent.putExtra("USER_FIRST_NAME", firstName); // Passer le prénom à la prochaine activité
                startActivity(intent);
            }
        });
    }
}
