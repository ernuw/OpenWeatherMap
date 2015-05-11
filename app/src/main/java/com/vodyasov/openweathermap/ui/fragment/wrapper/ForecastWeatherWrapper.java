package com.vodyasov.openweathermap.ui.fragment.wrapper;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.vodyasov.openweathermap.common.MeteoServiceManager;
import com.vodyasov.openweathermap.data.ForecastWeather;
import com.vodyasov.openweathermap.ui.fragment.ForecastWeatherFragment;
import com.vodyasov.openweathermap.ui.fragment.help.ProgressFragment;

public class ForecastWeatherWrapper extends BaseWrapper<ForecastWeather> implements LocationListener
{
    private Context mContext;
    private Location mLocation;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();
        LocationManager lManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        mLocation = lManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (mLocation == null)
        {
            lManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null);
        }
    }

    @Override
    protected void onLoadData()
    {
        if (mLocation != null)
        {
            MeteoServiceManager.getForecastWeather(
                    mContext, mLocation.getLatitude(), mLocation.getLongitude(), getPreferenceKey());
        }
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new ProgressFragment(), ProgressFragment.class.getName())
                .commit();
    }

    @NonNull
    @Override
    protected String getPreferenceKey()
    {
        return ForecastWeather.class.getName();
    }

    @NonNull
    @Override
    protected Class<ForecastWeather> getType()
    {
        return ForecastWeather.class;
    }

    @Override
    protected void onDataLoaded(ForecastWeather data)
    {
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, ForecastWeatherFragment.newInstance(data), ForecastWeatherFragment.class.getName())
                .commitAllowingStateLoss();
    }

    @Override
    public void onLocationChanged(Location location)
    {
        mLocation = location;
        MeteoServiceManager.getForecastWeather(
                mContext, mLocation.getLatitude(), mLocation.getLongitude(), getPreferenceKey());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}
}
