package com.example.jose.proyecto5;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;


public class MainActivity extends Activity {

    private AudioManager audioManager;
    private MediaPlayer mediaPlayer;
    private SharedPreferences pref = null;
    private boolean audio_actual;

    //se hace public para que se pueda acceder desde cualquier parte de la app
    public static final String SETTINGS = "on_off";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //cohesionar elementos interfaz/logica
        ImageButton bt_corazon = (ImageButton) findViewById(R.id.imageButton);
        ImageButton bt_rayo = (ImageButton) findViewById(R.id.imageButton2);
        ImageButton bt_settings = (ImageButton) findViewById(R.id.imageButton3);
        ImageButton bt_on_off = (ImageButton) findViewById(R.id.imageButton4);

        //capturar el registro de preferencias
        pref = getSharedPreferences(SETTINGS, 0);
        //asigna el valor de la preferencia con llave audio
        audio_actual = pref.getBoolean("audio", true);

        //capturar el servivio para gestionar el sonido
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        mediaPlayer = MediaPlayer.create(this, R.raw.cancion);//carga la cancion
        if (audio_actual) {
            mediaPlayer.start();//la inicia
        } else {
            if (mediaPlayer.isPlaying()) {//por si esta sonando y no debiera
                mediaPlayer.stop();
            }
        }


        //imlpementar el boton settings
        bt_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //crear el intent que lanzará el activity settings
                Intent i_settings = new Intent(MainActivity.this, settings.class);
                startActivity(i_settings);

            }
        });

        //implmentar el boton de salir
        bt_on_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //apaga la musica y sale de ala aplicacion
                mediaPlayer.stop();
                finish();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    protected void onResume() {

        super.onResume();

        //capturar el registro de preferencias
        pref = getSharedPreferences(SETTINGS, 0);
        //asigna el valor de la preferencia con llave audio
        audio_actual = pref.getBoolean("audio", true);

        if (audio_actual){

            if (!mediaPlayer.isPlaying()){//si el boton esta en on y no esta sonando...
                mediaPlayer = MediaPlayer.create(this, R.raw.cancion);//carga la cancion de nuevo
                mediaPlayer.start();//la inicia
            }
        }else{
            if (mediaPlayer.isPlaying()){//si el boton esta en off y esta sonando...
                mediaPlayer.stop();
            }
        }
        /**
        if (audio_actual && !mediaPlayer.isPlaying()) {
            //si ya está sonando es porque la aplicacion se ha iniciado con la pref en true
            //y si no se controla esta situacion sonaran dos canciones a la vez
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer = MediaPlayer.create(this, R.raw.cancion);//carga la cancion de nuevo
                mediaPlayer.start();//la inicia
            }
        } else {
            if (mediaPlayer.isPlaying()) {//por si esta sonando y no debiera
                mediaPlayer.stop();
            }


        }
         */
    }
}
