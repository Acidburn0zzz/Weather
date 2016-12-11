package com.marechal.weather;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by marechal on 10/12/16.
 */

class Weather {

    private String city;
    private String icon;
    private float temp;
    private String date;
    private String weather;
    private String weather_detail;
    private String pression;
    private String humidity;
    private String cloudnes;
    private String winSpeed;

    private boolean passed = false;

    Weather() {
    }

    void setWeather(JsonObject result, int pos) {
        passed = true;
        JsonArray list;

        setCity(result.getAsJsonObject("city"));

        list = result.getAsJsonArray("list");
        result = list.get(pos).getAsJsonObject();

        setDate(result.get("dt"));
        setTemp(result.getAsJsonObject("temp"));
        setWeather(result.getAsJsonArray("weather"));
        setIcon(result.getAsJsonArray("weather"));
        setPression(result.get("pressure"));
        setHumidity(result.get("humidity"));
        setCloudnes(result.get("speed"));
        setWinSpeed(result.get("clouds"));
        setWeather_detail(result.getAsJsonArray("weather"));
    }

    private void setCity(JsonObject jCity) {
        city = jCity.get("name").getAsString();
    }

    private void setTemp(JsonObject jTemp) {
        temp = jTemp.get("day").getAsFloat();
        System.out.println("temp = "+temp);
    }

    private void setWeather(JsonArray wthr) {
        weather = wthr.get(0).getAsJsonObject().get("main").getAsString();
    }

    private void setWeather_detail(JsonArray wthr) {
        weather_detail = wthr.get(0).getAsJsonObject().get("description").getAsString();
        System.out.println(weather_detail);
    }

    private void setDate(JsonElement elem) {
        long unixSeconds = elem.getAsLong();
        Date dates = new Date(unixSeconds*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE dd MMM yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        date = sdf.format(dates);
    }

    private void setIcon(JsonArray jIcon) {
        JsonObject ic = jIcon.get(0).getAsJsonObject();
        icon = "http://openweathermap.org/img/w/" + ic.get("icon").getAsString()+".png";
    }

    private void setPression(JsonElement elem) {
        pression = elem.getAsString();
    }

    private void setHumidity(JsonElement elem) {
        humidity = elem.getAsString();
    }

    private void setCloudnes(JsonElement elem) {
        cloudnes = elem.getAsString();
    }

    private void setWinSpeed(JsonElement elem) {
        winSpeed = elem.getAsString();
    }

    String getIcon() {
        return icon;
    }

    String getCityName() {
        return city;
    }

    float getTemp() {
        return temp;
    }

    String getDate() {
        return date;
    }

    String getWeather() {
        return weather;
    }

    String getPression() {
        return pression;
    }

    String getHumidity() {
        return humidity;
    }

    String getCloudnes() {
        return cloudnes;
    }

    String getWinSpeed() {
        return winSpeed;
    }

    String getWeather_detail() {
        return weather_detail;
    }

    boolean isPassed() {
        return passed;
    }
}
