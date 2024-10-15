package com.univpoitiers.bubellea;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.univpoitiers.bubellea.activities.FormObjectiveActivity;

public class MainActivity extends AppCompatActivity {

    private EditText firstNameEditText;
    private Button continueButton;

    private Button menuButton;

    private ImageView menuImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        menuImageView = findViewById(R.id.menuImageView);
        //menuButton.setVisibility(View.GONE);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        continueButton = findViewById(R.id.continueButton);
        menuButton = findViewById(R.id.menuButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameEditText.getText().toString().trim();

                // Vérifie si le champ est vide
                if (firstName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Veuillez entrer votre prénom", Toast.LENGTH_SHORT).show();
                } else {
                    // Passer à l'activité suivante (FormObjectiveActivity)
                    Intent intent = new Intent(MainActivity.this, FormObjectiveActivity.class);
                    intent.putExtra("USER_FIRST_NAME", firstName);  // Envoyer le prénom à la prochaine activité
                    startActivity(intent);
                }
            }
        });

        // Gestionnaire de clic pour le bouton "Visiter notre menu"
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                menuButton.setVisibility(View.VISIBLE);
                // URL de l'image
                String imageUrl = "https://postimg.cc/vczCptHM";

                // Charger l'image depuis Internet avec Glide
                Glide.with(MainActivity.this)
                        .load(imageUrl)
                        .into(menuImageView);

            }
        });
    }
}