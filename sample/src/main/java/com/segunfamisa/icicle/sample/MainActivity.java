package com.segunfamisa.icicle.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.segunfamisa.icicle.Icicle;
import com.segunfamisa.icicle.annotations.Freeze;

public class MainActivity extends AppCompatActivity {

    public static final int DEFAULT_COUNT = 2;

    @Freeze
    int mCount = DEFAULT_COUNT;

    private TextView mTextCount;
    private Button mButtonDecr;
    private Button mButtonIncr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Icicle.thaw(this, savedInstanceState);

        mTextCount = (TextView) findViewById(R.id.text_count);
        mButtonDecr = (Button) findViewById(R.id.button_decrement);
        mButtonIncr = (Button) findViewById(R.id.button_increment);

        updateCount();
        mButtonDecr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCount--;
                updateCount();
            }
        });

        mButtonIncr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCount++;
                updateCount();
            }
        });
    }

    private void updateCount() {
        mTextCount.setText(String.valueOf(mCount));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Icicle.freeze(this, outState);
        super.onSaveInstanceState(outState);
    }

}
