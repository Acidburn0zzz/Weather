package com.marechal.weather;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by marechal on 10/12/16.
 */

public class MyCustomListAdapter extends BaseAdapter{

    private int NUM_DAYS = 5;
    private static LayoutInflater inflater = null;
    private Activity activity;
    private ArrayList<Weather> weathers;
    private AlphaAnimation fadeOutAnimation;

    MyCustomListAdapter(Activity myActivity, ArrayList<Weather> wthrs, AlphaAnimation anim) {
        activity = myActivity;
        weathers = wthrs;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        fadeOutAnimation = anim;
    }

    @Override
    public int getCount() {
        return NUM_DAYS;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (view == null)
            v = inflater.inflate(R.layout.weather_list_content, null);
        if (weathers.get(i).isPassed()) {
            RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.listLayout);
            TextView city = (TextView) v.findViewById(R.id.city);
            TextView degree = (TextView) v.findViewById(R.id.degree);
            TextView date = (TextView) v.findViewById(R.id.date);
            TextView typeWeather = (TextView) v.findViewById(R.id.typeWthr);
            ImageView icon = (ImageView) v.findViewById(R.id.iconWeather);

            String tempCel = String.valueOf(weathers.get(i).getTemp())+"°C";
            typeWeather.setText(weathers.get(i).getWeather());
            city.setText(weathers.get(i).getCityName());
            degree.setText(tempCel);
            date.setText(weathers.get(i).getDate());
            Picasso.with(activity)
                    .load(weathers.get(i).getIcon())
                    .into(icon);
            setAnimation(layout);
        }
        return v;
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
