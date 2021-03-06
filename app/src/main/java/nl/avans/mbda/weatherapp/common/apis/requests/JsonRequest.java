package nl.avans.mbda.weatherapp.common.apis.requests;

import androidx.annotation.Nullable;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.IOException;

import nl.avans.mbda.weatherapp.models.Converter;

public abstract class JsonRequest<T> extends Request<T> {

    private final Response.Listener<T> listener;
    private final Class<T> type;

    public JsonRequest(String url, Response.Listener<T> listener, @Nullable Response.ErrorListener errorListener, Class<T> type) {
        super(Method.GET, url, errorListener);
        this.listener = listener;
        this.type = type;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            return Response.success(Converter.fromJsonString(json, type), HttpHeaderParser.parseCacheHeaders(response));
        } catch (IOException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }
}
