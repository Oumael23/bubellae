package com.univpoitiers.bubellea.log;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LogManager {

    private static final String LOG_FILE_NAME = "app_log.txt";
    private static final String TAG = "LogManager";

    // Enregistrer un événement dans le fichier de log
    public static void logEvent(String message) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        String logMessage = timeStamp + " - " + message + "\n";

        // Enregistrer dans le stockage interne
        File downloadDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File logFile = new File(downloadDirectory, LOG_FILE_NAME);

        try (FileOutputStream fos = new FileOutputStream(logFile, true)) {
            fos.write(logMessage.getBytes());
            Log.d(TAG, "Log écrit : " + logMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    // Lire le fichier de log (pour afficher l'historique)
//    public static String getLogHistory(Context context) {
//        File logFile = new File(context.getFilesDir(), LOG_FILE_NAME);
//        StringBuilder logContent = new StringBuilder();
//
//        try {
//            byte[] buffer = new byte[(int) logFile.length()];
//            FileInputStream fis = new FileInputStream(logFile);
//            fis.read(buffer);
//            fis.close();
//            logContent.append(new String(buffer));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return logContent.toString();
//    }
//
//    // Effacer le fichier de log
//    public static void clearLog(Context context) {
//        File logFile = new File(context.getFilesDir(), LOG_FILE_NAME);
//        if (logFile.exists()) {
//            logFile.delete();
//        }
//    }
}
