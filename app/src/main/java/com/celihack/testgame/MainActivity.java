package com.celihack.testgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Impostiamo la content View a quella del gioco, affinchè gli oggetti nella classe Game possano essere renderizzati
        setContentView(new Game(this));
    }
}