package com.tanghe.garben.taxcalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-4741956938194475~4063939544");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        final EditText editText = (EditText) findViewById(R.id.editText);
        editText.setHint("Type an amount here");
        final TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("Incl. 6 %");
        final TextView textView2 = (TextView) findViewById(R.id.textView2);
        TextView textView3 = (TextView) findViewById(R.id.textView3);
        textView3.setText("Incl. 21 %");
        final TextView textView4 = (TextView) findViewById(R.id.textView4);
        TextView textView5 = (TextView) findViewById(R.id.textView5);
        textView5.setText("Excl. 6 %");
        final TextView textView6 = (TextView) findViewById(R.id.textView6);
        TextView textView7 = (TextView) findViewById(R.id.textView7);
        textView7.setText("Excl. 21 %");
        final TextView textView8 = (TextView) findViewById(R.id.textView8);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                double amount = Double.parseDouble(editText.getText().toString());
                textView2.setText(Double.toString(round2decimals(amount * 1.06)));
                textView4.setText(Double.toString(round2decimals(amount * 1.21)));
                textView6.setText(Double.toString(round2decimals(amount / 1.06)));
                textView8.setText(Double.toString(round2decimals(amount / 1.21)));
                return false;
            }
        });
    }

    public double round2decimals(double d) {
        return (Math.round(d*100)/100.0);
    }
}