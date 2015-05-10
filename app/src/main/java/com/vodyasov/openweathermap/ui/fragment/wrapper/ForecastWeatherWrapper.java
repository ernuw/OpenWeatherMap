package com.vodyasov.openweathermap.ui.fragment.wrapper;

import android.support.annotation.NonNull;

import com.vodyasov.openweathermap.data.ForecastWeather;

public class ForecastWeatherWrapper extends BaseWrapper<ForecastWeather>
{

    @Override
    protected void onLoadData()
    {

    }

    @NonNull
    @Override
    protected String getPreferenceKey()
    {
        return null;
    }

    @NonNull
    @Override
    protected Class<ForecastWeather> getType()
    {
        return null;
    }

    @Override
    protected void onDataLoaded(ForecastWeather data)
    {

    }
}
