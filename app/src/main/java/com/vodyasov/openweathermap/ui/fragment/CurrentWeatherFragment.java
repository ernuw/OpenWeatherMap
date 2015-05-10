package com.vodyasov.openweathermap.ui.fragment;

import com.vodyasov.openweathermap.R;
import com.vodyasov.openweathermap.data.CurrentWeather;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CurrentWeatherFragment extends Fragment
{
	public static final String TAG = CurrentWeatherFragment.class.getName();
	public static final String KEY_DATA = "key";
	private CurrentWeather mData;
	
	public static CurrentWeatherFragment newInstance(CurrentWeather w)
	{
		CurrentWeatherFragment f = new CurrentWeatherFragment();
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
		View view = inflater.inflate(R.layout.fragment_current_weather, container, false);
		return view;
	}
}
