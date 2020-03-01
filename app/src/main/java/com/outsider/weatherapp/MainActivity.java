package com.outsider.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class MainActivity extends AppCompatActivity {

    TextView cityname, degree, condition;
    EditText editSearch;
    Button btnSearch;
    ImageView iconWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        cityname = findViewById(R.id.cityname);
        degree = findViewById(R.id.weatherdegree);
        condition = findViewById(R.id.weathercondition);
        editSearch = findViewById(R.id.editcity);
        btnSearch = findViewById(R.id.searchbtn);
        iconWeather = findViewById(R.id.weathericon);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String city = editSearch.getText().toString();

                Ion.with(MainActivity.this)
                        .load("https://openweathermap.org/data/2.5/weather?q="+city+"&appid=b6907d289e10d714a6e88b30761fae22")
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {

                                if (e == null){
                                    if(result.get("name") != null){
                                        degree.setText(result.getAsJsonObject("main").get("temp").getAsString()+"°C");
                                        cityname.setText(result.get("name").getAsString());
                                        JsonObject weather = result.getAsJsonArray("weather").get(0).getAsJsonObject();
                                        iconWeather.setImageResource(getImage(weather.get("main").getAsString()));
                                        condition.setText(weather.get("main").getAsString());
                                    }else{
                                        Toast.makeText(MainActivity.this, "City not found!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
        });





    }

    private int getImage(String main) {

        switch(main){
            case  "Clear" :

                return R.drawable.clear_day;

            case "Rain":
                return R.drawable.rain;
            case "Thunderstorm":
                return R.drawable.wind;
            case "Snow":
                return R.drawable.snow;
            case "Clouds":
                return R.drawable.cloudy;
            case "Drizzle":
                return R.drawable.sleet;
            case "Fog":
                return R.drawable.fog;
            default:
                return R.drawable.refresh;
        }
    }
}
