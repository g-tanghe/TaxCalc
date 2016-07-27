package com.tanghe.garben.taxcalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    public double amount;
    public double taxrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) throws NumberFormatException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-4741956938194475~4063939544");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        final EditText editText = (EditText) findViewById(R.id.editText);
        editText.setHint("Type amount here");
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
        final EditText editText2 = (EditText) findViewById(R.id.editText2);
        editText2.setHint("Type taxrate here");
        final TextView textView9 = (TextView) findViewById(R.id.textView9);
        final TextView textView10 = (TextView) findViewById(R.id.textView10);
        final TextView textView11 = (TextView) findViewById(R.id.textView11);
        final TextView textView12 = (TextView) findViewById(R.id.textView12);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
                textView2.setText("");
                textView4.setText("");
                textView6.setText("");
                textView8.setText("");
                textView10.setText("");
                textView12.setText("");
            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                try {
                    amount = Double.parseDouble(editText.getText().toString());
                }
                catch (NumberFormatException nfe) {
                    amount = 0.0;
                }
                if (amount > 1000000) {
                    amount = 0.0;
                    editText.setHint("Lower than 1,000,000.00");
                    editText.setText("");
                } else if (amount >= 0 && amount < 1000000) {
                    editText.setHint("Type amount here");
                    textView2.setText(Double.toString(calcIncl(amount,6.00)) + " (" + Double.toString(calcTaxIncl(amount,6.00)) + ")");
                    textView4.setText(Double.toString(calcIncl(amount,21.00)) + " (" + Double.toString(calcTaxIncl(amount,21.00)) + ")");
                    textView6.setText(Double.toString(calcExcl(amount,6.00)) + " (" + Double.toString(calcTaxExcl(amount,6.00)) + ")");
                    textView8.setText(Double.toString(calcExcl(amount,21.00)) + " (" + Double.toString(calcTaxExcl(amount,21.00)) + ")");
                    if (taxrate!=0.0) {
                        textView10.setText(Double.toString(calcIncl(amount,taxrate)));
                        textView12.setText(Double.toString(calcExcl(amount,taxrate)));
                    } else {
                        textView10.setText("");
                        textView12.setText("");
                    }
                } else {
                    amount = 0.0;
                    editText.setHint("Invalid, try again");
                    editText.setText("");
                }
                return false;
            }
        });

        editText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText2.setText("");
                textView10.setText("");
                textView12.setText("");
            }
        });

        editText2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                try {
                    taxrate = Double.parseDouble(editText2.getText().toString());
                }
                catch (NumberFormatException nfe) {
                    taxrate = 0.0;
                }
                textView9.setText("Incl. " + taxrate + " %");
                textView11.setText("Excl. " + taxrate + " %");
                textView10.setText(Double.toString(calcIncl(amount,taxrate)) + " (" + Double.toString(calcTaxIncl(amount,taxrate)) + ")");
                textView12.setText(Double.toString(calcExcl(amount,taxrate)) + " (" + Double.toString(calcTaxExcl(amount,taxrate)) + ")");
                return false;
            }
        });
    }

    public double round2decimals(double d) {
        return (Math.round(d*100)/100.00);
    }

    public double calcIncl(double amount, double taxrate) {
        return round2decimals(amount*(1+taxrate/100.00));
    }

    public double calcExcl(double amount,double taxrate) {
        return round2decimals(amount/(1+taxrate/100.00));
    }

    public double calcTaxIncl(double amount, double taxrate) {
        return round2decimals(amount*taxrate/100.00);
    }

    public double calcTaxExcl(double amount, double taxrate) {
        return round2decimals(calcExcl(amount,taxrate)*taxrate/100.00);
    }
}