package com.vodyasov.openweathermap.ui.fragment.help;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.vodyasov.openweathermap.R;

public abstract class RefreshFragment extends Fragment
{
    private MenuItem refreshItem;
    private boolean isEnabled = true;
    private static final String KEY_READY = "key_ready";


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
        {
            isEnabled = savedInstanceState.getBoolean(KEY_READY, true);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_READY, isEnabled);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.refresh_menu, menu);
        refreshItem = menu.findItem(R.id.action_update);
        refreshItem.setVisible(isEnabled);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.equals(refreshItem))
        {
            refresh();
        }
        return super.onOptionsItemSelected(item);
    }

    private  void refresh()
    {
        setRefreshEnabled(false);
        onRefresh();
    }

    public final void setRefreshEnabled(boolean value)
    {
        isEnabled = value;
        if (refreshItem != null)
        {
            refreshItem.setVisible(value);
        }
    }

    protected abstract void onRefresh();

    public final boolean isRefreshEnabled()
    {
        return isEnabled;
    }
}
