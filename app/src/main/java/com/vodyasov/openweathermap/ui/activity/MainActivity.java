package com.vodyasov.openweathermap.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.vodyasov.openweathermap.R;
import com.vodyasov.openweathermap.ui.util.NavigationItem;

public class MainActivity extends AppCompatActivity implements Drawer.OnDrawerItemClickListener, IActionbar
{
    private Toolbar mToolbar;
    private Drawer.Result mDrawer;
    private int mDrawerPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mDrawer = new Drawer()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withDisplayBelowToolbar(true)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withSavedInstance(savedInstanceState)
                .addDrawerItems(
                    new PrimaryDrawerItem()
                        .withName(NavigationItem.MY_CURRENT_WEATHER.getTitle(this))
                        .withIdentifier(NavigationItem.MY_CURRENT_WEATHER.getId())
                        .withIcon(NavigationItem.MY_CURRENT_WEATHER.getIcon()),
                    new PrimaryDrawerItem()
                        .withName(NavigationItem.MY_FORECAST.getTitle(this))
                        .withIdentifier(NavigationItem.MY_FORECAST.getId())
                        .withIcon(NavigationItem.MY_FORECAST.getIcon()),
                    new PrimaryDrawerItem()
                        .withName(NavigationItem.MORE_CITIES.getTitle(this))
                        .withIdentifier(NavigationItem.MORE_CITIES.getId())
                        .withIcon(NavigationItem.MORE_CITIES.getIcon()),
                    new DividerDrawerItem(),
                    new SecondaryDrawerItem()
                        .withName(NavigationItem.MORE_CITIES.getTitle(this))
                        .withIdentifier(NavigationItem.MORE_CITIES.getId())
                        .withIcon(NavigationItem.MORE_CITIES.getIcon())
                 )
                .withOnDrawerItemClickListener(this)
                .build();
        mDrawer.setSelection(0);
    }

    @Override
    public void onBackPressed()
    {
        if (mDrawer.isDrawerOpen())
        {
            mDrawer.closeDrawer();
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        mDrawer.saveInstanceState(outState);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id, IDrawerItem iDrawerItem)
    {
        if (mDrawerPosition == position)
        {
            return;
        }
        NavigationItem navigationItem = NavigationItem.getNavigationItemById(iDrawerItem.getIdentifier());
        if (navigationItem != null)
        {
            setActionbarTitle(navigationItem.getTitle(this));
            setActionbarColor(navigationItem.getActionbarColor(this));
            setStatusbarColor(navigationItem.getStatusbarColor(this));
            navigationItem.show(getApplicationContext(), getFragmentManager(), R.id.fragment_container);
        }
        mDrawerPosition = position;
    }

    @Override
    public void setActionbarTitle(CharSequence title)
    {
        mToolbar.setTitle(title);
    }

    @Override
    public void setActionbarColor(int color)
    {
        mToolbar.setBackgroundColor(color);
    }

    @Override
    public void setStatusbarColor(int color)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setStatusBarColor(color);
        }
    }
}
