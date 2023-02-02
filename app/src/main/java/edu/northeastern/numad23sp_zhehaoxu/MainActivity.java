package edu.northeastern.numad23sp_zhehaoxu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button aboutMe=findViewById(R.id.About_me);
        aboutMe.setOnClickListener(view -> startActivity(new Intent(MainActivity.this,AboutMe.class)));
        Button clickyButton = findViewById(R.id.clicky);
        clickyButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this,Clicky.class)));
    }

}