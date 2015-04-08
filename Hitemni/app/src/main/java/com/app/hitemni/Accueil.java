package com.app.hitemni;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class Accueil extends Activity {

    ImageButton imgb_ecole ;
    ImageButton imgb_favoris ;
    ImageButton imgb_profil ;
    ImageButton imgb_message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        //ECOLE
        imgb_ecole = (ImageButton) findViewById(R.id.imgb_ecole);
        imgb_ecole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Ecole.class);
                startActivity(intent);
            }
        });

        //FAVORIS
        imgb_favoris = (ImageButton) findViewById(R.id.imgb_favoris);
        imgb_favoris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Favoris.class);
                startActivity(intent);
            }
        });

        //PROFIL
        imgb_profil = (ImageButton) findViewById(R.id.imgb_profil);
        imgb_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfilPerso.class);
                startActivity(intent);
            }
        });

        //MESSAGERIE
        imgb_message = (ImageButton) findViewById(R.id.imgb_message);
        imgb_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Messages.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_accueil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
