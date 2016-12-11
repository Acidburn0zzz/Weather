package com.marechal.weather;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by marechal on 10/12/2016
 */
public class MainActivity extends AppCompatActivity {

    private int NUM_DAYS = 5;
    private ListView weatherList;
    private MyCustomListAdapter adapter;
    private ArrayList<Weather> weathers;
    private AlphaAnimation fadeOutAnimation;
    private Dialog dialog;
    private WindowManager.LayoutParams lp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setDialog();
        setFadeAnimation();
        weathers = new ArrayList<>();
        for (int i = 0; i < NUM_DAYS; i++)
            weathers.add(new Weather());
        weatherList = (ListView) findViewById(R.id.weatherList);
        adapter = new MyCustomListAdapter(this, weathers, fadeOutAnimation);
        weatherList.setAdapter(adapter);
        weatherList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                secondScreen(i, dialog);
            }
        });
    }

    private void setDialog() {
        dialog = new Dialog(this);
        lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle("Détail");
    }

    private void setFadeAnimation() {
        fadeOutAnimation = new AlphaAnimation(0, 1);
        fadeOutAnimation.setDuration(300);
        fadeOutAnimation.setFillAfter(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Ion.with(this)
                .load("http://api.openweathermap.org/data/2.5/forecast/daily?q=Paris&units=metric&cnt=5&appid=8194ea842a9aef80a798c8ac0c320ec4")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result != null) {
                            for (int i = 0; i < NUM_DAYS; i++)
                                weathers.get(i).setWeather(result, i);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    protected void secondScreen(int i, Dialog dialog) {
        RelativeLayout layout = (RelativeLayout) dialog.findViewById(R.id.secondLayout);
        TextView date = (TextView) dialog.findViewById(R.id.textView19);
        TextView city = (TextView) dialog.findViewById(R.id.textView20);
        TextView temp = (TextView) dialog.findViewById(R.id.textView21);
        TextView weather = (TextView) dialog.findViewById(R.id.textView22);
        TextView pression = (TextView) dialog.findViewById(R.id.textView23);
        TextView humidity = (TextView) dialog.findViewById(R.id.textView24);
        TextView cloudnes = (TextView) dialog.findViewById(R.id.textView25);
        TextView winSpeed = (TextView) dialog.findViewById(R.id.textView26);
        TextView weather_detail = (TextView) dialog.findViewById(R.id.textView);
        ImageView icon = (ImageView) dialog.findViewById(R.id.iconWeather);

        String tempCel = String.valueOf(weathers.get(i).getTemp())+"°C";
        String cloud = weathers.get(i).getCloudnes()+"%";
        String wethr = weathers.get(i).getWeather()+" : ";

        weather_detail.setText(weathers.get(i).getWeather_detail());
        date.setText(weathers.get(i).getDate());
        city.setText(weathers.get(i).getCityName());
        temp.setText(tempCel);
        weather.setText(wethr);
        pression.setText(weathers.get(i).getPression());
        humidity.setText(weathers.get(i).getHumidity());
        cloudnes.setText(cloud);
        winSpeed.setText(weathers.get(i).getWinSpeed());
        Picasso.with(this)
                .load(weathers.get(i).getIcon())
                .into(icon);
        setAnimation(layout);
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void setAnimation(final RelativeLayout layout) {
        fadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                layout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }
        });
        layout.startAnimation(fadeOutAnimation);
    }
}
