package com.example.catchthekennygame;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView timeText;
    TextView scoreText;
    int number = 0;
    ImageView i̇mageView1;
    ImageView i̇mageView2;
    ImageView i̇mageView3;
    ImageView i̇mageView4;
    ImageView i̇mageView5;
    ImageView i̇mageView6;
    ImageView i̇mageView7;
    ImageView i̇mageView8;
    ImageView i̇mageView9;
    ImageView[] imageArray;
    Handler handler;
    Runnable runnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        timeText = findViewById(R.id.timeText);
        scoreText = findViewById(R.id.scoreText);
        i̇mageView1 = findViewById(R.id.imageView1);
        i̇mageView2 = findViewById(R.id.imageView2);
        i̇mageView3 = findViewById(R.id.imageView3);
        i̇mageView4 = findViewById(R.id.imageView4);
        i̇mageView5 = findViewById(R.id.imageView5);
        i̇mageView6 = findViewById(R.id.imageView6);
        i̇mageView7 = findViewById(R.id.imageView7);
        i̇mageView8 = findViewById(R.id.imageView8);
        i̇mageView9 = findViewById(R.id.imageView9);
        imageArray = new ImageView[]{i̇mageView1, i̇mageView2, i̇mageView3, i̇mageView4, i̇mageView5, i̇mageView6, i̇mageView7, i̇mageView8, i̇mageView9};

        hideImages();

        CountDownTimer countDownTimer = new CountDownTimer(10000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timeText.setText("Time " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                timeText.setText("Time Off");
                handler.removeCallbacks(runnable);
                for (ImageView image : imageArray) {
                    image.setVisibility(View.INVISIBLE);
                }
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Restart");
                alert.setMessage("Are you sure to restart game?");
                alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
                alert.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "finish", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.show();

            }
        }.start();

    }

    public void increaseSore(View view) {
        number++;
        scoreText.setText(getString(R.string.score_text, number));

    }

    public void hideImages() {


        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                for (ImageView image : imageArray) {
                    image.setVisibility(View.INVISIBLE);
                }
                Random random = new Random();
                int i = random.nextInt(9);
                imageArray[i].setVisibility(View.VISIBLE);
                handler.postDelayed(this, 500);

            }
        };
        handler.post(runnable);
    }
}
