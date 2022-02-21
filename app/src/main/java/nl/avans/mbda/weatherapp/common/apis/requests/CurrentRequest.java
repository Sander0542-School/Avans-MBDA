package nl.avans.mbda.weatherapp.common.apis.requests;

import androidx.annotation.Nullable;

import com.android.volley.Response;

import nl.avans.mbda.weatherapp.models.current.Current;

public class CurrentRequest extends JsonRequest<Current> {
    public CurrentRequest(String url, Response.Listener<Current> listener, @Nullable Response.ErrorListener errorListener) {
        super(url, listener, errorListener, Current.class);
    }
}
