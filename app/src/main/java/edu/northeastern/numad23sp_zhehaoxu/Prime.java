package edu.northeastern.numad23sp_zhehaoxu;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Prime extends AppCompatActivity {
    private volatile boolean isSearching=false;
    private boolean isPacifierOn=false;
    private int curNumData=3;
    private int curPrimeData;
    private TextView curNum;
    private TextView curPrime;
    private Thread searchPrime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prime);
        Button start = findViewById(R.id.startPrime);
        Button stop = findViewById(R.id.stopPrime);
        CheckBox pacifier = findViewById(R.id.pacifierSwitch);
        curNum=findViewById(R.id.curNum);
        curPrime=findViewById(R.id.curPrime);
        curNum.setText("Current number is -");
        curPrime.setText("Latest Prime is -");
        // load state if not first time start
        if(savedInstanceState!=null){
            curNumData=savedInstanceState.getInt("curNum");
            curPrimeData=savedInstanceState.getInt("curPrime");
            isPacifierOn=savedInstanceState.getBoolean("pacifier");
            isSearching=savedInstanceState.getBoolean("isSearching");
            if(isSearching){
                searchPrime=new Thread(new SearchThread());
                searchPrime.start();
            }
            pacifier.setChecked(isPacifierOn);
        }

        pacifier.setOnCheckedChangeListener((compoundButton, b) -> isPacifierOn=b);

        start.setOnClickListener(view -> {
            if(!isSearching){
                isSearching=true;
                searchPrime=new Thread(new SearchThread());
                searchPrime.start();
            }
        });

        stop.setOnClickListener(view -> {
            if(isSearching){
                isSearching=false;
                curNumData=3;
                searchPrime.interrupt();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curNum", curNumData);
        outState.putInt("curPrime", curPrimeData);
        outState.putBoolean("pacifier", isPacifierOn);
        outState.putBoolean("isSearching", isSearching);
    }

    @Override
    public void onBackPressed(){
        if(isSearching){
            new AlertDialog.Builder(this)
                    //.setTitle("Terminate Search")
                    .setMessage("Are you sure you want to terminate the search and close the activity?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        searchPrime.interrupt();
                        super.onBackPressed();
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        else{
            super.onBackPressed();
        }
    }

    class SearchThread implements Runnable{
        @Override
        public void run() {
            updateCurrentNumberView();
            updateLatestPrimeView();
            while(isSearching){
                updateCurrentNumberView();
                if (isPrime(curNumData)) {
                    curPrimeData = curNumData;
                    updateLatestPrimeView();
                }
                curNumData += 2;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private boolean isPrime(int n) {
            for (int i = 2; i <= Math.sqrt(n); i++) {
                if (n % i == 0) {
                    return false;
                }
            }
            return true;
        }

        private void updateCurrentNumberView() {
            String numText="Current number is "+ curNumData;
            curNum.post(() -> curNum.setText(numText));
        }

        private void updateLatestPrimeView() {
            String primeText="Latest prime is "+ curPrimeData;
            curPrime.post(() -> curPrime.setText(primeText));
        }
    }
}
