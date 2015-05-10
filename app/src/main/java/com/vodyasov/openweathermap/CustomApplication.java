package com.vodyasov.openweathermap;

import android.app.Application;

import com.mikepenz.iconics.Iconics;
import com.mikepenz.meteocons_typeface_library.Meteoconcs;

public class CustomApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Iconics.registerFont(new Meteoconcs());
    }
}
