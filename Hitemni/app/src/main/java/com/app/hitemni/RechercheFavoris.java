package com.app.hitemni;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class RechercheFavoris extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche_favoris);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recherche_favoris, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent intent;
        int id = item.getItemId();
        switch (item.getItemId()) {

            // page Accueil
            case R.id.title_activity_accueil:
                intent = new Intent(getApplicationContext(), Accueil.class);
                startActivity(intent);
                return true;

            // page favoris favoris
            case R.id.title_activity_favoris:
                intent = new Intent(getApplicationContext(), Favoris.class);
                startActivity(intent);
                return true;

            // page Géolocalisation des favoris
            case R.id.title_activity_geolocalisation_favoris:
                intent = new Intent(getApplicationContext(), GeolocalisationFavoris.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
