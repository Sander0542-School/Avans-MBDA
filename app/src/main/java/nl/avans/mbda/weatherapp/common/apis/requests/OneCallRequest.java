package nl.avans.mbda.weatherapp.common.apis.requests;

import androidx.annotation.Nullable;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.IOException;

import nl.avans.mbda.weatherapp.models.Converter;
import nl.avans.mbda.weatherapp.models.OneCall;

public class OneCallRequest extends Request<OneCall> {

    private final Response.Listener<OneCall> mListener;

    public OneCallRequest(String url, Response.Listener<OneCall> listener, @Nullable Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        mListener = listener;
    }

    @Override
    protected Response<OneCall> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            return Response.success(Converter.fromJsonString(json), HttpHeaderParser.parseCacheHeaders(response));
        } catch (IOException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(OneCall response) {
        mListener.onResponse(response);
    }
}
