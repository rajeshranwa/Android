package com.rajeshk.currencyconverter;

import android.content.Intent;
import android.icu.lang.UCharacter;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void convertButtonOnClick(View view){
        TextView textView= (TextView) findViewById(R.id.inputBox);
        String value= textView.getText().toString();
        Log.i("Info","value ="+value);
        String regexStr = "(?<=^| )\\d+(\\.\\d+)?(?=$| )|(?<=^| )\\.\\d+(?=$| )";//"^[0-9]*$";
        if(value.length()<1){
            Toast.makeText(this, "Field can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if(value.matches(regexStr)) {
            double val = Double.parseDouble(value);
            val = val * 66;
            Log.i("Info", "Converted value =" + val);
            Toast.makeText(this, "Rupees: " + String.format("%.2f",val), Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"Please enter only numeric value",Toast.LENGTH_LONG).show();
            textView.setText("");
        }
        /*Intent emailIntent= new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, "rajeshranwa94@gmail.com");
        emailIntent.putExtra(Intent.EXTRA_CC, "");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");
        try{
            startActivity(Intent.createChooser(emailIntent,"Send Email"));
            finish();
            Toast.makeText(this, "Email sent successfully!", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(this, "Error in sending mail", Toast.LENGTH_SHORT).show();
        }*/

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
