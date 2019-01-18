package com.avaj.weather;

public class WeatherProvider {
    private static WeatherProvider weatherProvider = new WeatherProvider();
    private static String[] weather = {
            "RAIN",
            "FOG",
            "SUN",
            "SNOW"
    };

    private WeatherProvider() {}

    public static WeatherProvider getProvider() {
        return WeatherProvider.weatherProvider;
    }

    public String getCurrentWeather(Coordinates coordinates) {
        int seed = coordinates.getLongitude() + coordinates.getLatitude() + coordinates.getHeight();
        return weather[seed % 4];
    }
}
