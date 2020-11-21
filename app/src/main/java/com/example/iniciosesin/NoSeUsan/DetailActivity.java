package com.example.iniciosesin.NoSeUsan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.iniciosesin.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView textView = findViewById(R.id.textView_detail);
        textView.setText(getIntent().getStringExtra("param"));
    }
}