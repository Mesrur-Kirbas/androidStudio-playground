package com.example.landmarkbookjava;

import android.content.Intent; // YENİ EKLENDİ (Bu import satırını ekleyin)
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.landmarkbookjava.databinding.ActivityInfoBinding;

public class InfoActivity extends AppCompatActivity {
    private ActivityInfoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInfoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // --- YENİ EKLENEN KOD BAŞLANGICI ---

        // 1. Gelen Intent'i yakala
        Intent intent = getIntent();

        // 2. Intent'in içinden "landmark" anahtarıyla gönderdiğimiz veriyi al
        // (Serializable olduğu için getSerializableExtra kullanıyoruz)
        Landmark selectedLandmark = (Landmark) intent.getSerializableExtra("landmark");

        // 3. Verinin boş gelme ihtimaline karşı kontrol et (Null check)
        if (selectedLandmark != null) {

            // 4. Veriyi View'lara yerleştir
            // ÖNEMLİ: 'activity_info.xml' dosyanızdaki ID'lere göre 'binding' isimlerini düzeltin.
            // Ben ID'lerin 'nameText', 'locationText' ve 'imageView' olduğunu varsayıyorum.

            binding.nameText.setText(selectedLandmark.name);
            binding.locationText.setText(selectedLandmark.location);
            binding.imageView.setImageResource(selectedLandmark.image);
        }

        // --- YENİ EKLENEN KOD SONU ---
    }
}