package com.vodyasov.openweathermap.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vodyasov.openweathermap.R;
import com.vodyasov.openweathermap.data.ForecastWeather;

public class ForecastWeatherFragment extends Fragment
{
	public static final String TAG = ForecastWeatherFragment.class.getName();
	public static final String KEY_DATA = "key";
	private ForecastWeather mData;
	
	public static ForecastWeatherFragment newInstance(ForecastWeather w)
	{
		ForecastWeatherFragment f = new ForecastWeatherFragment();
		Bundle args = new Bundle();
		args.putParcelable(KEY_DATA, w);
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mData = getArguments().getParcelable(KEY_DATA);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_forecast_weather, container, false);
		return view;
	}
}
