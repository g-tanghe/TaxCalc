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

import com.google.firebase.crash.FirebaseCrash;

public class MainActivity extends AppCompatActivity {
    public double amount;
    public double taxrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) throws NumberFormatException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-4741956938194475~4063939544");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        //AdRequest adRequest = new AdRequest.Builder().addTestDevice("62DB073E067B8944A650AE9CC2BD9368").build();
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        AdView mAdView2 = (AdView) findViewById(R.id.adView2);
        //AdRequest adRequest2 = new AdRequest.Builder().addTestDevice("62DB073E067B8944A650AE9CC2BD9368").build();
        AdRequest adRequest2 = new AdRequest.Builder().build();
        mAdView2.loadAd(adRequest2);

        //FirebaseCrash.report(new Exception("My first Android non-fatal error"));

        final EditText editText = (EditText) findViewById(R.id.editText);
        final TextView textView2 = (TextView) findViewById(R.id.textView2);
        final TextView textView4 = (TextView) findViewById(R.id.textView4);
        final TextView textView6 = (TextView) findViewById(R.id.textView6);
        final TextView textView8 = (TextView) findViewById(R.id.textView8);
        final EditText editText2 = (EditText) findViewById(R.id.editText2);
        final TextView textView9 = (TextView) findViewById(R.id.textView9);
        final TextView textView10 = (TextView) findViewById(R.id.textView10);
        final TextView textView11 = (TextView) findViewById(R.id.textView11);
        final TextView textView12 = (TextView) findViewById(R.id.textView12);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount = 0.0;
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
                    if (amount > 1000000) {
                        amount = 0.0;
                        editText.setHint("Lower than 1,000,000");
                        editText.setText("");
                        textView2.setText("");
                        textView4.setText("");
                        textView6.setText("");
                        textView8.setText("");
                        textView10.setText("");
                        textView12.setText("");
                    } else {
                        editText.setHint("Type amount here");
                        editText.setText(Double.toString(round2decimals(amount)));
                        textView2.setText(calcIncl(amount,6.00) + " (" + calcTaxIncl(amount,6.00) + ")");
                        textView4.setText(calcIncl(amount,21.00) + " (" + calcTaxIncl(amount,21.00) + ")");
                        textView6.setText(calcExcl(amount,6.00) + " (" + calcTaxExcl(amount,6.00) + ")");
                        textView8.setText(calcExcl(amount,21.00) + " (" + calcTaxExcl(amount,21.00) + ")");
                        if (taxrate != 0.0) {
                            textView10.setText(calcIncl(amount,taxrate) + " (" + calcTaxIncl(amount,taxrate) + ")");
                            textView12.setText(calcExcl(amount,taxrate) + " (" + calcTaxExcl(amount,taxrate) + ")");
                        }
                    }
                }
                catch (NumberFormatException nfe) {
                    amount = 0.0;
                    editText.setHint("Invalid, try again");
                    editText.setText("");
                    textView2.setText("");
                    textView4.setText("");
                    textView6.setText("");
                    textView8.setText("");
                }
                return false;
            }
        });

        editText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taxrate = 0.0;
                editText2.setText("");
                textView9.setText("");
                textView10.setText("");
                textView11.setText("");
                textView12.setText("");
            }
        });

        editText2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                try {
                    taxrate = Double.parseDouble(editText2.getText().toString());
                    if (taxrate > 1000) {
                        editText2.setHint("Lower than 1,000");
                        editText2.setText("");
                        textView9.setText("");
                        textView10.setText("");
                        textView11.setText("");
                        textView12.setText("");
                    } else {
                        editText2.setHint("Type taxrate here");
                        editText2.setText(Double.toString(round2decimals(taxrate)));
                        textView9.setText("Incl. " + taxrate + " %");
                        textView11.setText("Excl. " + taxrate + " %");
                        if (amount != 0.0) {
                            textView10.setText(calcIncl(amount, taxrate) + " (" + calcTaxIncl(amount, taxrate) + ")");
                            textView12.setText(calcExcl(amount, taxrate) + " (" + calcTaxExcl(amount, taxrate) + ")");
                        } else {
                            textView10.setText("");
                            textView12.setText("");
                        }
                    }
                }
                catch (NumberFormatException nfe) {
                    taxrate = 0.0;
                    editText2.setHint("Invalid, try again");
                    editText2.setText("");
                    textView9.setText("");
                    textView10.setText("");
                    textView11.setText("");
                    textView12.setText("");
                }
                return false;
            }
        });
    }

    public double round2decimals(double d) {
        return Math.round(d*100)/100.0;
    }

    public String calcIncl(double amount, double taxrate) {
        return Double.toString(round2decimals(amount*(1+taxrate/100.0)));
    }

    public String calcExcl(double amount,double taxrate) {
        return Double.toString(round2decimals(amount/(1+taxrate/100.0)));
    }

    public String calcTaxIncl(double amount, double taxrate) {
        return Double.toString(round2decimals(amount*taxrate/100.0));
    }

    public String calcTaxExcl(double amount, double taxrate) {
        return Double.toString(round2decimals(amount/(1+taxrate/100.0)*taxrate/100.0));
    }
}