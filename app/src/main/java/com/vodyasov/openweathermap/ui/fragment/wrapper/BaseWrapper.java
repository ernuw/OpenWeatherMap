package com.vodyasov.openweathermap.ui.fragment.wrapper;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vodyasov.openweathermap.ui.fragment.help.RefreshFragment;

public abstract class BaseWrapper<T> extends RefreshFragment implements SharedPreferences.OnSharedPreferenceChangeListener
{
    private Gson mGson;
    private Class<T> mClassOfT;
    private String mPreferenceKey;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mGson = new GsonBuilder().create();
        mClassOfT = getType();
        mPreferenceKey = getPreferenceKey();
        PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext())
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null)
        {
            String jsonString = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext())
                    .getString(mPreferenceKey, null);
            if (!TextUtils.isEmpty(jsonString))
            {
                T data = mGson.fromJson(jsonString, mClassOfT);
                onDataLoaded(data);
                setRefreshEnabled(true);
            }
            else
            {
                loadData();
            }
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext())
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onRefresh()
    {
        loadData();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        if (key.equals(mPreferenceKey))
        {
            String jsonString = sharedPreferences.getString(mPreferenceKey, null);
            if (!TextUtils.isEmpty(jsonString))
            {
                T data = mGson.fromJson(jsonString, mClassOfT);
                onDataLoaded(data);
                setRefreshEnabled(true);
            }
        }
    }

    public void loadData()
    {
        setRefreshEnabled(false);
        onLoadData();
    }

    protected abstract void onLoadData();
    @NonNull
    protected abstract String getPreferenceKey();
    @NonNull
    protected abstract Class<T> getType();
    protected abstract void onDataLoaded(T data);
}
