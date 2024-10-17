package com.univpoitiers.bubellea.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.univpoitiers.bubellea.R;
import com.univpoitiers.bubellea.log.LogManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class ResultActivity extends AppCompatActivity {

    private TextView resultsTextView;
    private Button buttonQuit, buttonEmail;
    private ImageView resultImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultsTextView = findViewById(R.id.resultsTextView);
        Button buttonSave = findViewById(R.id.buttonSave);
        buttonQuit = findViewById(R.id.buttonQuit);
        resultImageView = findViewById(R.id.resultImageView);
        buttonEmail = findViewById(R.id.buttonEmail);


        // Récupérer les données passées
        Intent intent = getIntent();
        String results = intent.getStringExtra("RESULTS");

        // Afficher les résultats
        resultsTextView.setText(results != null ? Html.fromHtml(results, Html.FROM_HTML_MODE_LEGACY) : getString(R.string.no_results));

        // Gérer le clic sur le bouton "Quitter"
        buttonQuit.setOnClickListener(v -> {
            LogManager.logEvent("Utilisateur a cliqué sur le bouton Quitter");

            finishAffinity(); // Quitter l'application
        });

        buttonEmail.setOnClickListener(v -> sendResults(results));


        // Gérer le clic sur le bouton de sauvegarde
        buttonSave.setOnClickListener(v -> saveResultsToFile(results));

        setRandomLogo();

    }

    private void setRandomLogo() {
        // Tableau des logos disponibles
        int[] logos = {R.drawable.tea1, R.drawable.tea2, R.drawable.tea3};

        // Générer un index aléatoire
        Random random = new Random();
        int randomIndex = random.nextInt(logos.length);

        // Afficher l'image correspondante dans l'ImageView
        resultImageView.setImageResource(logos[randomIndex]);
    }


    private void saveResultsToFile(String results) {
        LogManager.logEvent("Utilisateur a cliqué sur le bouton Telecharger le resultat");

        if (results == null || results.isEmpty()) {
            Toast.makeText(this, getString(R.string.results_save_error), Toast.LENGTH_SHORT).show();
            return;
        }

        results = results.replaceAll("<br>", "\n").replaceAll("<b>", "").replaceAll("</b>", "");


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
            Toast.makeText(this, getString(R.string.results_saved_success), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.results_save_error), Toast.LENGTH_SHORT).show();
        }
    }

    private void sendResults(String results) {

        if (results == null || results.isEmpty()) {
            Toast.makeText(this, getString(R.string.results_save_error), Toast.LENGTH_SHORT).show();
            return;
        }

        results = results.replaceAll("<br>", "\n").replaceAll("<b>", "").replaceAll("</b>", "");


        // Créer un Intent pour envoyer un e-mail
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Résultats de votre sélection"); // Sujet de l'e-mail
        emailIntent.putExtra(Intent.EXTRA_TEXT, results); // Corps de l'e-mail

        // Vérifier s'il y a une application de messagerie pour gérer l'Intent
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(emailIntent, "Envoyer l'e-mail"));
        } else {
            Toast.makeText(this, "Aucun client de messagerie trouvé", Toast.LENGTH_SHORT).show();
        }
    }

}
