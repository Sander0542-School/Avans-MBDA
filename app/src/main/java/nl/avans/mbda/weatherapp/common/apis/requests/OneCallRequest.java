package nl.avans.mbda.weatherapp.common.apis.requests;

import androidx.annotation.Nullable;

import com.android.volley.Response;

import nl.avans.mbda.weatherapp.models.onecall.OneCall;

public class OneCallRequest extends JsonRequest<OneCall> {
    public OneCallRequest(String url, Response.Listener<OneCall> listener, @Nullable Response.ErrorListener errorListener) {
        super(url, listener, errorListener, OneCall.class);
    }
}
