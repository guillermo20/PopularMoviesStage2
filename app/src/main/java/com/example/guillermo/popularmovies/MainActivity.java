package com.example.guillermo.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.guillermo.popularmovies.fragments.MainGridFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState==null){
            getFragmentManager().beginTransaction().add(R.id.main_id,new MainGridFragment()).commit();
        }
    }


}
