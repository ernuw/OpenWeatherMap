package com.vodyasov.openweathermap.ui.fragment.wrapper;

import android.support.annotation.NonNull;

import com.vodyasov.openweathermap.ui.fragment.MoreCitiesFragment;

public class MoreCitiesWrapper extends BaseWrapper<MoreCitiesFragment>
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
    protected Class<MoreCitiesFragment> getType()
    {
        return null;
    }

    @Override
    protected void onDataLoaded(MoreCitiesFragment data)
    {

    }
}
