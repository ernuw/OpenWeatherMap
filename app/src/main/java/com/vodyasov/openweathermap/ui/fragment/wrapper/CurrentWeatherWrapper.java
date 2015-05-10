package com.vodyasov.openweathermap.ui.fragment.wrapper;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vodyasov.openweathermap.R;
import com.vodyasov.openweathermap.common.MeteoServiceManager;
import com.vodyasov.openweathermap.data.CurrentWeather;
import com.vodyasov.openweathermap.ui.fragment.CurrentWeatherFragment;
import com.vodyasov.openweathermap.ui.fragment.help.ProgressFragment;

public class CurrentWeatherWrapper extends BaseWrapper<CurrentWeather> implements LocationListener, OnMapReadyCallback
{
    private Context mContext;
    private Location mLocation;
    private GoogleMap mGoogleMap;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_current_weather_wrapper, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null)
        {
            MapFragment mapFragment = MapFragment.newInstance();
            mapFragment.getMapAsync(this);
            getFragmentManager().beginTransaction()
                    .add(R.id.header_container, mapFragment, MapFragment.class.getName())
                    .commit();
        }
    }

    @Override
    protected void onLoadData()
    {
        if (mLocation != null)
        {
            MeteoServiceManager.getCurrentWeather(
                    mContext, mLocation.getLatitude(), mLocation.getLongitude(), getPreferenceKey());
        }
        getFragmentManager().beginTransaction()
                .replace(R.id.main_container, new ProgressFragment(), ProgressFragment.class.getName())
                .commit();
    }

    @NonNull
    @Override
    protected String getPreferenceKey()
    {
        return CurrentWeather.class.getName();
    }

    @NonNull
    @Override
    protected Class<CurrentWeather> getType()
    {
        return CurrentWeather.class;
    }

    @Override
    protected void onDataLoaded(CurrentWeather data)
    {
        getFragmentManager().beginTransaction()
                .replace(R.id.main_container, CurrentWeatherFragment.newInstance(data), CurrentWeatherFragment.class.getName())
                .commitAllowingStateLoss();
    }

    @Override
    public void onLocationChanged(Location location)
    {
        mLocation = location;
        MeteoServiceManager.getCurrentWeather(mContext, mLocation.getLatitude(), mLocation.getLongitude(), getPreferenceKey());
        setupMap();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mGoogleMap = googleMap;
        setupMap();
    }

    private void setupMap()
    {
        if (mGoogleMap != null && mLocation != null)
        {
            int zoom = 15;
            LatLng point = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, zoom));
            mGoogleMap.addMarker(new MarkerOptions()
                    .title(getString(R.string.text_you_are_here))
                    .position(point));
        }
    }
}
