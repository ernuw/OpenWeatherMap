package com.vodyasov.openweathermap.ui.util;

import android.app.FragmentManager;
import android.content.Context;
import android.support.annotation.IdRes;

import com.mikepenz.iconics.typeface.IIcon;

public interface INavigationItem
{
    int getId();
    String getTag();
    String getTitle(Context context);
    int getActionbarColor(Context context);
    int getStatusbarColor(Context context);
    IIcon getIcon();
    void show(Context context, FragmentManager fm, @IdRes int resId);
}
