package com.app.hitemni;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import java.lang.*;
import android.view.View;
import android.view.MenuItem;
import android.widget.EditText;


public class Ecole extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecole);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ecole, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent;
        switch (item.getItemId()) {
                case R.id.title_activity_event_ecole:
                    intent = new Intent(getApplicationContext(), EventEcole.class);
                    startActivity(intent);
                return true;

                case R.id.title_activity_accueil:
                    intent = new Intent(getApplicationContext(), Accueil.class);
                    startActivity(intent);
                    return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
