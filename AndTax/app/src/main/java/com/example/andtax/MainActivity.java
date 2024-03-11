package com.example.andtax;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import org.mariuszgromada.math.mxparser.*;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class MainActivity extends AppCompatActivity {
    //privates
    private EditText numOfPeople;
    private TextView endTotal;
    private EditText beforeTaxTotal;
    private AdView mAdView;
    private TextView percentTax;
    private SeekBar seekBar;
    private static Double total = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mAds
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //intializations -> view
        endTotal = findViewById(R.id.endTotal);
        numOfPeople = findViewById(R.id.numOfPeople);
        beforeTaxTotal = findViewById(R.id.beforeTaxTotal);
        percentTax = findViewById(R.id.percentTax);
        seekBar = findViewById(R.id.seekBar);

        //seekBar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                percentTax.setVisibility(View.VISIBLE);
                percentTax.setText(progress+"% Tip");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    private void updateText(String strToAdd) {
        String oldStr = beforeTaxTotal.getText().toString();

        int CursorPos = beforeTaxTotal.getSelectionStart();

        String leftStr = oldStr.substring(0, CursorPos);
        String rightStr = oldStr.substring (CursorPos);
        beforeTaxTotal.setText(String.format("%s%s", leftStr, strToAdd, rightStr));
        beforeTaxTotal.setSelection(CursorPos + strToAdd.length());

    }

    //button controls
    public void zeroBTNPush (View view)  {
        if (!(beforeTaxTotal.getText().equals("0") || beforeTaxTotal.getText().equals("0.00"))) {
            updateText(getResources().getString(R.string.zero));
        }
    }

    public void oneBTNPush (View view)  {
        updateText(getResources().getString(R.string.one));
    }

    public void twoBTNPush (View view)  {
        updateText(getResources().getString(R.string.two));
    }

    public void threeBTNPush (View view)  {
        updateText(getResources().getString(R.string.three));
    }

    public void fourBTNPush (View view)  {
        updateText(getResources().getString(R.string.four));
    }

    public void fiveBTNPush (View view)  {
        updateText(getResources().getString(R.string.five));
    }

    public void sixBTNPush (View view)  {
        updateText(getResources().getString(R.string.six));
    }

    public void sevenBTNPush (View view)  {
        updateText(getResources().getString(R.string.seven));
    }

    public void eightBTNPush (View view)  {
        updateText(getResources().getString(R.string.eight));
    }

    public void nineBTNPush (View view)  {
        updateText(getResources().getString(R.string.nine));
    }

    public void pointBTNPush (View view)  {
        updateText(getResources().getString(R.string.point));
    }

    public void equalsBTNPush (View view) {
        try {
            boolean add=false, sub=false , multi = false, div= false;
            String userExp1 = beforeTaxTotal.getText().toString();
            Expression exp = new Expression(userExp1);
            String output = String.valueOf(exp.calculate());
            beforeTaxTotal.setText(output);
            Double beforeTotal = Double.parseDouble(beforeTaxTotal.getText().toString());

            //
            Double percentTaxPercent = (Double.parseDouble(percentTax.getText().toString()) / 100);
            Double totalpercentTax = (((beforeTotal * percentTaxPercent) * 100) / 100);
            total = (((beforeTotal + totalpercentTax) * 100) / 100);

            //Output
            endTotal.setText(total.toString());

            //catch NFE (prevents crashes)
        } catch(NumberFormatException nfe){

        }
    }

    public void addBTNPush (View view)  {
        updateText(getResources().getString(R.string.add));
    }

    public void multiplyBTNPush (View view)  {
        updateText(getResources().getString(R.string.multiply));
    }

    public void clearBTNPush (View view) {
        if (total == 0.0) {
            beforeTaxTotal.setText(R.string.zero);
            endTotal.setText(R.string.zero);
        } else {
            total = 0.0;
        }
    }
}