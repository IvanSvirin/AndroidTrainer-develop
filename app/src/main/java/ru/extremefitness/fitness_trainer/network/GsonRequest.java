package ru.extremefitness.fitness_trainer.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import ru.extremefitness.fitness_trainer.Config;
import ru.extremefitness.fitness_trainer.Utils;

/**
 * Created by Comp on 18.08.2015.
 */
public class GsonRequest<T> extends Request<T> {

    private static final int TIMEOUT = 60000;

    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private final Map<String, String> params;
    private final Response.Listener<T> listener;
    private Context mContext;

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url   URL of the request to make
     * @param clazz Relevant class object, for Gson's reflection
     */
    private GsonRequest(final Context context, final int method, final String url, final Class<T> clazz,
                        final Map<String, String> params,
                        final Response.Listener<T> listener, final Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.clazz = clazz;
        this.params = null == params ? new HashMap<String, String>() : params;
        this.listener = listener;
        mContext = context;

        setCustomParams();

        setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Log.i("GSON_REQUEST", "params: " + params.toString());
    }

    private void setCustomParams() {
        params.put(ParamsKeys.VERSION.toString(), Utils.getAppVersionName(mContext));
        params.put(ParamsKeys.PLATFORM.toString(), Config.ANDROID_PLATFORM);
    }

    public static <T> GsonRequest<T> create(final Context context, final int method, final String url,
                                            final Class<T> clazz, Map<String, String> params,
                                            final Response.Listener<T> listener, final Response.ErrorListener errorListener) {
        return new GsonRequest<>(context, method, url, clazz, params, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return super.getHeaders();
    }

    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        return params != null ? params : super.getParams();
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String json;
        try {
            json = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            Log.i(getClass().getName(), "json = " + json);
            Log.i(getClass().getName(), String.valueOf(response.data));


            return Response.success(gson.fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        } catch (Throwable e) {
            return Response.error(new ParseError(e));
        }
    }
}
