package edu.northeastern.numad23sp_zhehaoxu;

import androidx.appcompat.app.AppCompatActivity;

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
        aboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Name: Zhehao Xu\nEmail: xu.zheh@northeastern.edu", Toast.LENGTH_SHORT).show();
            }
        });
    }

}