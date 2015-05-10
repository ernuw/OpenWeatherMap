package com.vodyasov.openweathermap.common;

import java.io.IOException;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.vodyasov.openweathermap.data.Coords;
import com.vodyasov.openweathermap.data.CurrentWeather;
import com.vodyasov.openweathermap.data.ForecastDay;
import com.vodyasov.openweathermap.data.ForecastWeather;
import com.vodyasov.openweathermap.data.MainParameters;
import com.vodyasov.openweathermap.data.StateParameters;
import com.vodyasov.openweathermap.data.WindParameters;
import com.vodyasov.openweathermap.des.CoordsDes;
import com.vodyasov.openweathermap.des.CurrentWeatherDes;
import com.vodyasov.openweathermap.des.ForecastDayDes;
import com.vodyasov.openweathermap.des.ForecastWeatherDes;
import com.vodyasov.openweathermap.des.MainParametersDes;
import com.vodyasov.openweathermap.des.StateParametersDes;
import com.vodyasov.openweathermap.des.WindParametersDes;

public class MeteoStation
{
	public static final String TAG = MeteoStation.class.getName();

	private static final String AUTHORITY = "api.openweathermap.org";

	private static final Uri FORECAST = new Uri.Builder().scheme("http").authority(AUTHORITY)
			.appendPath("data").appendPath("2.5").appendPath("forecast").appendPath("daily").build();

	private static final Uri CURRENT = new Uri.Builder().scheme("http").authority(AUTHORITY)
			.appendPath("data").appendPath("2.5").appendPath("weather").build();

	private static MeteoStation sInstance;

	private final OkHttpClient mClient;
	private final Gson mGson;

	private MeteoStation()
	{
		mClient = new OkHttpClient();
		mGson = new GsonBuilder()
				.registerTypeAdapter(Coords.class, new CoordsDes())
				.registerTypeAdapter(MainParameters.class, new MainParametersDes())
				.registerTypeAdapter(StateParameters.class, new StateParametersDes())
				.registerTypeAdapter(WindParameters.class, new WindParametersDes())
				.registerTypeAdapter(CurrentWeather.class, new CurrentWeatherDes())
				.registerTypeAdapter(ForecastDay.class, new ForecastDayDes())
				.registerTypeAdapter(ForecastWeather.class, new ForecastWeatherDes())
				.create();
	}

	public static synchronized MeteoStation getInstance()
	{
		if (sInstance == null)
		{
			sInstance = new MeteoStation();
		}
		return sInstance;
	}

	@Nullable
	public CurrentWeather getCurrentWeather(String city, String country) throws IOException
	{
		if (TextUtils.isEmpty(city) && TextUtils.isEmpty(country))
		{
			throw new IllegalArgumentException();
		}
		String location = city;
		if (!TextUtils.isEmpty(country))
		{
			location = new StringBuilder(location).append(",").append(country).toString();
		}

		String url= CURRENT.buildUpon().appendQueryParameter("q", location).build().toString();
		Request request = new Request.Builder().url(url).build();
		Response response = mClient.newCall(request).execute();
		CurrentWeather result =null;
		if (response.isSuccessful())
		{
			result = mGson.fromJson(response.body().charStream(), CurrentWeather.class);
		}
		return result;
	}

	@Nullable
	public CurrentWeather getCurrentWeather(double lat, double lon) throws IOException
	{		
		String url = CURRENT.buildUpon().appendQueryParameter("lat", String.valueOf(lat))
				.appendQueryParameter("lon", String.valueOf(lon)).build().toString();
		Request request = new Request.Builder().url(url).build();
		Response response = mClient.newCall(request).execute();
		CurrentWeather result = null;
		if (response.isSuccessful())
		{
			result = mGson.fromJson(response.body().charStream(), CurrentWeather.class);
		}
		return result;
	}

	@Nullable
	public ForecastWeather getForecastWeather(String city, String country, int days) throws IOException
	{
		if (TextUtils.isEmpty(city) && TextUtils.isEmpty(country))
		{
			throw new IllegalArgumentException();
		}
		if (days < 1 || days > 16)
		{
			throw new IllegalArgumentException("1 <= days <= 16");
		}

		String location = city;
		if (!TextUtils.isEmpty(country))
		{
			location = new StringBuilder(location).append(",").append(country).toString();
		}

		String url= FORECAST.buildUpon()
                .appendQueryParameter("q", location)
                .appendQueryParameter("cnt", String.valueOf(days))
                .build().toString();
		Request request = new Request.Builder().url(url).build();
		Response response = mClient.newCall(request).execute();
		ForecastWeather result = null;
		if (response.isSuccessful())
		{
			result = mGson.fromJson(response.body().charStream(), ForecastWeather.class);
		}
		return result;
	}

	@Nullable
	public ForecastWeather getForecastWeather(double lat, double lon, int days) throws IOException
	{
        if (days < 1 || days > 16)
        {
            throw new IllegalArgumentException("1 <= days <= 16");
        }

		String url = FORECAST.buildUpon()
                .appendQueryParameter("lat", String.valueOf(lat))
				.appendQueryParameter("lon", String.valueOf(lon))
                .appendQueryParameter("cnt", String.valueOf(days))
                .build().toString();
		Request request = new Request.Builder().url(url).build();
		Response response = mClient.newCall(request).execute();
		ForecastWeather result = null;
		if (response.isSuccessful())
		{
			result = mGson.fromJson(response.body().charStream(), ForecastWeather.class);
		}
		return result;
	}

	public void getCurrentWeather(String city, String country, @NonNull final MeteoCallback<CurrentWeather> callback)
	{
		if (TextUtils.isEmpty(city) && TextUtils.isEmpty(country))
		{
			throw new IllegalArgumentException();
		}
		String location = city;
		if (!TextUtils.isEmpty(country))
		{
			location = new StringBuilder(location).append(",").append(country).toString();
		}

		String url= CURRENT.buildUpon().appendQueryParameter("q", location).build().toString();
		Request request = new Request.Builder().url(url).build();
		mClient.newCall(request).enqueue(new Callback()
		{
			@Override
			public void onFailure(Request request, IOException e)
			{
				callback.onFail(e);
			}

			@Override
			public void onResponse(Response response) throws IOException
			{
				if (response.isSuccessful())
				{
					CurrentWeather result = mGson.fromJson(response.body().charStream(), CurrentWeather.class);
					callback.onSuccess(result);
				}
			}
		});
	}

	public void getCurrentWeather(double lat, double lon, @NonNull final MeteoCallback<CurrentWeather> callback)
	{
		String url = CURRENT.buildUpon().appendQueryParameter("lat", String.valueOf(lat))
				.appendQueryParameter("lon", String.valueOf(lon)).build().toString();
		Request request = new Request.Builder().url(url).build();
		mClient.newCall(request).enqueue(new Callback()
		{
			@Override
			public void onFailure(Request request, IOException e)
			{
				callback.onFail(e);
			}

			@Override
			public void onResponse(Response response) throws IOException
			{
				if (response.isSuccessful())
				{
					CurrentWeather result = mGson.fromJson(response.body().charStream(), CurrentWeather.class);
					callback.onSuccess(result);
				}
			}
		});
	}

	public void getForecastWeather(String city, String country, int days, @NonNull final MeteoCallback<ForecastWeather> callback)
	{
		if (TextUtils.isEmpty(city) && TextUtils.isEmpty(country))
		{
			throw new IllegalArgumentException();
		}
		if (days < 1 || days > 16)
		{
			throw new IllegalArgumentException("1 <= days <= 16");
		}

		String location = city;
		if (!TextUtils.isEmpty(country))
		{
			location = new StringBuilder(location).append(",").append(country).toString();
		}

		String url= FORECAST.buildUpon()
				.appendQueryParameter("q", location)
				.appendQueryParameter("cnt", String.valueOf(days))
				.build().toString();
		Request request = new Request.Builder().url(url).build();
		mClient.newCall(request).enqueue(new Callback()
		{
			@Override
			public void onFailure(Request request, IOException e)
			{
				callback.onFail(e);
			}

			@Override
			public void onResponse(Response response) throws IOException
			{
				if (response.isSuccessful())
				{
					ForecastWeather result = mGson.fromJson(response.body().charStream(), ForecastWeather.class);
					callback.onSuccess(result);
				}
			}
		});
	}

	public void getForecastWeather(double lat, double lon, int days, @NonNull final MeteoCallback<ForecastWeather> callback)
	{
		if (days < 1 || days > 16)
		{
			throw new IllegalArgumentException("1 <= days <= 16");
		}

		String url = FORECAST.buildUpon()
				.appendQueryParameter("lat", String.valueOf(lat))
				.appendQueryParameter("lon", String.valueOf(lon))
				.appendQueryParameter("cnt", String.valueOf(days))
				.build().toString();
		Request request = new Request.Builder().url(url).build();
		mClient.newCall(request).enqueue(new Callback()
		{
			@Override
			public void onFailure(Request request, IOException e)
			{
				callback.onFail(e);
			}

			@Override
			public void onResponse(Response response) throws IOException
			{
				if (response.isSuccessful())
				{
					ForecastWeather result = mGson.fromJson(response.body().charStream(), ForecastWeather.class);
					callback.onSuccess(result);
				}
			}
		});
	}

	public interface MeteoCallback<T>
	{
		void onSuccess(T result);
		void onFail(Exception e);
	}
}