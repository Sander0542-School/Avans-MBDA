package nl.avans.mbda.weatherapp.common.apis;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.Nullable;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import nl.avans.mbda.weatherapp.common.apis.requests.OneCallRequest;
import nl.avans.mbda.weatherapp.models.OneCall;

public class OpenWeatherMap {

    private static final String API_KEY = "2460339d141426813b9c6d4b631be36d";

    private final RequestQueue requestQueue;

    OpenWeatherMap(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public void OneCall(double lat, double lon, Response.Listener<OneCall> listener, @Nullable Response.ErrorListener errorListener) {
        String url = GetBaseUri()
                .appendPath("onecall")
                .appendQueryParameter("lat", String.valueOf(lat))
                .appendQueryParameter("lon", String.valueOf(lon))
                .appendQueryParameter("exclude", "minutely,hourly,alerts")
                .toString();

        requestQueue.add(new OneCallRequest(url, listener, errorListener));
    }

    private static Uri.Builder GetBaseUri() {
        return new Uri.Builder()
                .scheme("https")
                .authority("api.openweathermap.org")
                .appendPath("data")
                .appendPath("2.5")
                .appendQueryParameter("appid", API_KEY);
    }
}
