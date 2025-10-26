package com.example.landmarkbookjava;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.landmarkbookjava.databinding.ActivityInfoBinding;
import com.example.landmarkbookjava.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    ArrayList<Landmark> landmarkArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Landmark kavun = new Landmark("Kavun","Kırkagac", R.drawable.kavun);
        Landmark ceviz = new Landmark("Ceviz","Kaman", R.drawable.ceviz);
        Landmark leblebi = new Landmark("Leblebi","Tavşanlı", R.drawable.leblebi);
        Landmark tespih = new Landmark("Tespih","Erzurum", R.drawable.tespih);
        Landmark kofte = new Landmark("Köfte","İnegöl", R.drawable.inegolkoftesi);
        Landmark nasrettin = new Landmark("Nasrettin Hoca","Konya-Afyankarahisar", R.drawable.nasrettinhoca);
        Landmark bazlama = new Landmark("Bazlama","Kızılcıhamam", R.drawable.bazlama);
        Landmark uzum = new Landmark("Üzüm","Sarıgöl", R.drawable.uzum);
        Landmark testi = new Landmark("Testi","Yozgat", R.drawable.testi);
        Landmark inek = new Landmark("İnek","Bigadiç", R.drawable.inek);
        Landmark mantar = new Landmark("Mantar","Gölköy", R.drawable.mantar);
        Landmark aycicek = new Landmark("Ayçiçek","İskenderun", R.drawable.aycicegi);
        Landmark armut = new Landmark("Armut","Korkuteli", R.drawable.armut);
        Landmark horoz = new Landmark("Horoz ve Biberler ","Denizli", R.drawable.horoz);
        Landmark findik = new Landmark("Fındık","Giresun", R.drawable.findik);
        Landmark havuc = new Landmark("Havuç","Beypazarı", R.drawable.havuc);
        Landmark elma = new Landmark("Elma", "Eğirdir",R.drawable.elma);
        Landmark isot = new Landmark("İsot", "Şanlıurfa", R.drawable.isot);
        Landmark sehzade = new Landmark("Selfie Çeken Şehzade", "Amasya", R.drawable.selfiecekensehzade);
        Landmark sekerpancari = new Landmark("Şeker Pancarı", "Ilgın", R.drawable.sekerpancari);
        Landmark karpuz = new Landmark("Karpuz", "Adana", R.drawable.karpuz);

        landmarkArrayList = new ArrayList<>();

        landmarkArrayList.add(kavun);
        landmarkArrayList.add(ceviz);
        landmarkArrayList.add(leblebi);
        landmarkArrayList.add(tespih);
        landmarkArrayList.add(kofte);
        landmarkArrayList.add(nasrettin);
        landmarkArrayList.add(bazlama);
        landmarkArrayList.add(uzum);
        landmarkArrayList.add(testi);
        landmarkArrayList.add(inek);
        landmarkArrayList.add(mantar);
        landmarkArrayList.add(aycicek);
        landmarkArrayList.add(armut);
        landmarkArrayList.add(horoz);
        landmarkArrayList.add(findik);
        landmarkArrayList.add(havuc);
        landmarkArrayList.add(elma);
        landmarkArrayList.add(isot);
        landmarkArrayList.add(sehzade);
        landmarkArrayList.add(sekerpancari);
        landmarkArrayList.add(karpuz);


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LandmarkAdapter landmarkAdapter = new LandmarkAdapter(landmarkArrayList);
        binding.recyclerView.setAdapter(landmarkAdapter);


    }
}