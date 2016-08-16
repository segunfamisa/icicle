package com.segunfamisa.icicle.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.segunfamisa.icicle.Icicle;
import com.segunfamisa.icicle.annotations.Freeze;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Freeze String name;
    @Freeze String age;
    @Freeze int houses;
    @Freeze HashMap<String, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icicle.thaw(this, savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            map = new HashMap<>();
            map.put("A", "Apple");
            name = "Segun";
            age = "23";
            houses = 5;
        }

        Log.d("MainActivity", map.toString() + "\n" + name + "\n" + age + "\n" + houses);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Icicle.freeze(this, outState);
        super.onSaveInstanceState(outState);
    }

}
