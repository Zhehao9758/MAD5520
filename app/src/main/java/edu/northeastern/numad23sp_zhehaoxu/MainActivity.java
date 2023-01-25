package edu.northeastern.numad23sp_zhehaoxu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button aboutMe=findViewById(R.id.About_me);
        String aboutMeText="Name: Zhehao Xu\nEmail: xu.zheh@northeastern.edu";
        aboutMe.setOnClickListener(view -> Toast.makeText(MainActivity.this, aboutMeText, Toast.LENGTH_SHORT).show());
        Button clickyButton = findViewById(R.id.clicky);
        clickyButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this,Clicky.class)));
    }

}