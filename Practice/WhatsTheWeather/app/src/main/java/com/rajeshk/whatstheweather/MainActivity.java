package com.rajeshk.whatstheweather;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    TextView cityName;

    public class GetWeatherDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                int data = reader.read();
                String result = "";
                while (data != -1) {
                    result += (char) data;
                    data = reader.read();
                }
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void getWeatherInfo(View view) {

        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(cityName.getWindowToken(), 0);

        String city = cityName.getText().toString();
        String encodedCityName = "";
        if (cityName.length() > 0) {

            GetWeatherDetails getWeatherDetails = new GetWeatherDetails();
            try {
                encodedCityName = URLEncoder.encode(city, "UTF-8");
                String url = "http://api.openweathermap.org/data/2.5/weather?q=" + encodedCityName + "&appid=542ffd081e67f4512b705f89d2a611b2";


                String result = getWeatherDetails.execute(url).get();
                if (result != null) {
                    JSONObject jsonObject = new JSONObject(result);

                    JSONArray arr = new JSONArray(jsonObject.getString("weather"));
                    String output = "";

                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject partialObject = arr.getJSONObject(i);
                        String main = "";
                        String description = "";
                        main = partialObject.getString("main");
                        description = partialObject.getString("description");
                        if (main != "" && description != "") {
                            output += main + ":" + description + "\r\n";
                        }
                    }
                    if (output != "") {
                        ((TextView) findViewById(R.id.weatherDetails)).setText(output);
                    }

                } else {
                    ((TextView) findViewById(R.id.weatherDetails)).setText("");
                    Toast.makeText(getApplicationContext(), "Incorrect City Name", Toast.LENGTH_LONG).show();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            ((TextView) findViewById(R.id.weatherDetails)).setText("");
            Toast.makeText(getApplicationContext(), "Please enter city Name", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityName = (TextView) findViewById(R.id.cityName);


    }
}
