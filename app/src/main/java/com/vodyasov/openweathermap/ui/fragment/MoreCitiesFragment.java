package com.vodyasov.openweathermap.ui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vodyasov.openweathermap.R;
import com.vodyasov.openweathermap.data.CurrentWeather;

import java.util.ArrayList;
import java.util.List;

public class MoreCitiesFragment extends Fragment
{
    private static final String KEY_DATA = "data";
    private List<CurrentWeather> mData;

    public static MoreCitiesFragment newInstance(List<CurrentWeather> data)
    {
        MoreCitiesFragment f = new MoreCitiesFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(KEY_DATA, (ArrayList<? extends Parcelable>) data);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mData = getArguments().getParcelableArrayList(KEY_DATA);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view  = inflater.inflate(R.layout.fragment_more_cities, container, false);
        return view;
    }
}
