package com.app.hitemni;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class ProfilPerso extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_perso);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profil_perso, menu);
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

            // page CV
            case R.id.title_activity_cv:
                intent = new Intent(getApplicationContext(), CV.class);
                startActivity(intent);
                return true;

            // page Experience Pro
            case R.id.title_activity_experience_pro:
                intent = new Intent(getApplicationContext(), ExperiencePro.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
