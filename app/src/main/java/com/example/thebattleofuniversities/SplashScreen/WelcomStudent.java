package com.example.thebattleofuniversities.SplashScreen;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.thebattleofuniversities.CatalogActivity;
import com.example.thebattleofuniversities.MainActivity;
import com.example.thebattleofuniversities.R;

/**
 * Created by Олжас on 04.12.2017.
 */

public class WelcomStudent extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startwindow);

        GoRunnable goRunnable = new GoRunnable();
        goRunnable.execute();


    }

    private class GoRunnable extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected Void doInBackground(Void... params) {
            Intent intent = new Intent(getApplicationContext(), CatalogActivity.class);
            try {
                Thread.sleep(4000);
                startActivity(intent);
                finish();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }



        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

}
