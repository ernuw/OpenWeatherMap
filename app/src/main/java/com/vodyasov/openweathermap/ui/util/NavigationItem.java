package com.vodyasov.openweathermap.ui.util;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.meteocons_typeface_library.Meteoconcs;
import com.vodyasov.openweathermap.R;
import com.vodyasov.openweathermap.ui.fragment.pref.SettingsFragment;
import com.vodyasov.openweathermap.ui.fragment.wrapper.CurrentWeatherWrapper;
import com.vodyasov.openweathermap.ui.fragment.wrapper.ForecastWeatherWrapper;
import com.vodyasov.openweathermap.ui.fragment.wrapper.MoreCitiesWrapper;

public enum NavigationItem implements INavigationItem
{
    MY_CURRENT_WEATHER(CurrentWeatherWrapper.class,
            1,
            R.string.title_my_current_weather,
            R.color.primary1,
            R.color.darkPrimary1,
            Meteoconcs.Icon.met_cloud_sun_inv),

    MY_FORECAST(ForecastWeatherWrapper.class,
            2,
            R.string.title_my_forecast,
            R.color.primary2,
            R.color.darkPrimary2,
            Meteoconcs.Icon.met_cloud_moon_inv),

    MORE_CITIES(MoreCitiesWrapper.class,
            3,
            R.string.title_more_cities,
            R.color.primary3,
            R.color.darkPrimary3,
            Meteoconcs.Icon.met_snow_heavy_inv),

    SETTINGS(SettingsFragment.class,
            4,
            R.string.title_settings,
            R.color.primary4,
            R.color.darkPrimary4,
            GoogleMaterial.Icon.gmd_settings);

    private Class<? extends Fragment> mClass;
    private final int mId;
    private @StringRes final int mTitleId;
    private @ColorRes final int mActionBarColorId;
    private @ColorRes final int mStatusBarColorId;
    private IIcon mIcon;

    NavigationItem(Class<? extends Fragment> clz, int id, int titleResId, int actionBarColorId, int statusBarColorId, IIcon icon)
    {
        mClass = clz;
        mId = id;
        mTitleId = titleResId;
        mActionBarColorId = actionBarColorId;
        mStatusBarColorId = statusBarColorId;
        mIcon = icon;
    }

    @Override
    public int getId()
    {
        return mId;
    }

    @Override
    public String getTag()
    {
        return mClass.getName();
    }

    @Override
    public String getTitle(Context context)
    {
        return context.getResources().getString(mTitleId);
    }

    @Override
    public int getActionbarColor(Context context)
    {
        return context.getResources().getColor(mActionBarColorId);
    }

    @Override
    public int getStatusbarColor(Context context)
    {
        return context.getResources().getColor(mStatusBarColorId);
    }

    @Override
    public IIcon getIcon()
    {
        return mIcon;
    }

    @Override
    public void show(Context context, FragmentManager fm, @IdRes int containerId)
    {
        Fragment fragment = fm.findFragmentByTag(getTag());
        if (fragment == null)
        {
            fragment = android.app.Fragment.instantiate(context, mClass.getName());
        }
        fm.beginTransaction()
                .replace(containerId, fragment, getTag())
                .commit();
    }

    @Nullable
    public static NavigationItem getNavigationItemById(int id)
    {
        if (id == MY_CURRENT_WEATHER.getId())
        {
            return MY_CURRENT_WEATHER;
        }
        if (id == MY_FORECAST.getId())
        {
            return MY_FORECAST;
        }
        if (id == MORE_CITIES.getId())
        {
            return MORE_CITIES;
        }
        if (id == SETTINGS.getId())
        {
            return SETTINGS;
        }
        return null;
    }
}
