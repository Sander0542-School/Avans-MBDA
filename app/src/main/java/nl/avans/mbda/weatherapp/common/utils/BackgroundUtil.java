package nl.avans.mbda.weatherapp.common.utils;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.InputStream;

public class BackgroundUtil {
    private final ActivityResultLauncher<String> permissionLauncher;
    private final ActivityResultLauncher<String> imagePickerLauncher;

    private final AppCompatActivity activity;

    public BackgroundUtil(AppCompatActivity activity, View view) {
        this.activity = activity;

        imagePickerLauncher = activity.registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
            try {
                InputStream stream = activity.getContentResolver().openInputStream(uri);
                Drawable drawable = Drawable.createFromStream(stream, uri.toString());
                view.setBackground(drawable);
            } catch (Exception ignored) {
            }
        });

        permissionLauncher = activity.registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                imagePickerLauncher.launch("image/*");
            } else {
                Toast.makeText(activity, "The app needs storage access to select the background image.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void change() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            return;
        }
        imagePickerLauncher.launch("image/*");
    }
}
