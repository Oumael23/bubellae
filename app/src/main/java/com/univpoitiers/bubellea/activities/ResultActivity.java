package com.univpoitiers.bubellea.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.univpoitiers.bubellea.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ResultActivity extends AppCompatActivity {

    private TextView resultsTextView;

    private Button buttonQuit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultsTextView = findViewById(R.id.resultsTextView);
        Button buttonSave = findViewById(R.id.buttonSave);
        buttonQuit = findViewById(R.id.buttonQuit);


        // Récupérer les données passées
        Intent intent = getIntent();
        String results = intent.getStringExtra("RESULTS");

        // Afficher les résultats
        resultsTextView.setText(results != null ? Html.fromHtml(results, Html.FROM_HTML_MODE_LEGACY) : "Aucun résultat");

        // Gérer le clic sur le bouton "Quitter"
        buttonQuit.setOnClickListener(v -> {
            finishAffinity(); // Quitter l'application
        });

        // Gérer le clic sur le bouton de sauvegarde
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveResultsToFile(results);
            }
        });
    }

    private void saveResultsToFile(String results) {
            try {
                // Obtenir le chemin du dossier Download
                File downloadDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File file = new File(downloadDirectory, "results.txt");

                // Créer le fichier si nécessaire
                if (!file.exists()) {
                    file.createNewFile();
                }

                // Écrire les résultats dans le fichier
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(results.getBytes());
                fos.close();
                Toast.makeText(this, "Résultats sauvegardés avec succès dans le dossier Download !", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Erreur lors de la sauvegarde des résultats.", Toast.LENGTH_SHORT).show();
            }
        }

}
