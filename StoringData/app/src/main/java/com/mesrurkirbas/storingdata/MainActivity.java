package com.mesrurkirbas.storingdata;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView textView;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextNumber);
        textView = findViewById(R.id.textView);

        sharedpreferences = this.getSharedPreferences("com.mesrurkirbas.storingdata", Context.MODE_PRIVATE);
        int storedAge = sharedpreferences.getInt("storedAge", 0);
        textView.setText("Your age =" + storedAge);
    }




    public void save(View view){
        if (!editText.getText().toString().matches("")){
            int userAge = Integer.parseInt(editText.getText().toString());
            textView.setText("your age: " + userAge );

            sharedpreferences.edit().putInt("storedAge",userAge).apply();
        }
    }
}