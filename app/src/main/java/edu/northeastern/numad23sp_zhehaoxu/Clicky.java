package edu.northeastern.numad23sp_zhehaoxu;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Clicky extends AppCompatActivity implements View.OnClickListener {

    TextView pressed;
    Button buttonA, buttonB, buttonC, buttonD, buttonE, buttonF;


    public void onClick(View view){
        Button cur=findViewById(view.getId());
        String pressing="Pressed: "+cur.getText();
        pressed.setText(pressing);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clicky);
        pressed=findViewById(R.id.pressed);
        buttonA = findViewById(R.id.a);
        buttonB = findViewById(R.id.b);
        buttonC = findViewById(R.id.c);
        buttonD = findViewById(R.id.d);
        buttonE = findViewById(R.id.e);
        buttonF = findViewById(R.id.f);
        buttonA.setOnClickListener(this);
        buttonB.setOnClickListener(this);
        buttonC.setOnClickListener(this);
        buttonD.setOnClickListener(this);
        buttonE.setOnClickListener(this);
        buttonF.setOnClickListener(this);
    }
}
