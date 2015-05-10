package com.vodyasov.openweathermap.ui.fragment.help;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.vodyasov.openweathermap.R;

public class ProgressFragment extends Fragment
{
    public static final String TAG = ProgressFragment.class.getName();

    public static final String KEY_COLOR = "color";
    public static final int DEFAULT_COLOR = R.color.background_floating_material_light;

    public static final String KEY_MESSAGE = "message";
    public static final String DEFAULT_MESSAGE = "";

    public static ProgressFragment newInstance(int color)
    {
        ProgressFragment f = new ProgressFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_COLOR, color);
        f.setArguments(args);
        return f;
    }

    public static ProgressFragment newInstance(int color, String message)
    {
        ProgressFragment f = newInstance(color);
        f.getArguments().putString(KEY_MESSAGE, message);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.progressbar, container, false);
        ProgressWheel progressBar = (ProgressWheel) view.findViewById(R.id.progress_wheel);
        int color = getArguments() != null ? getArguments().getInt(KEY_COLOR, DEFAULT_COLOR) : DEFAULT_COLOR;
        progressBar.setBarColor(color);
        String message = getArguments() != null ? getArguments().getString(KEY_MESSAGE, DEFAULT_MESSAGE) : DEFAULT_MESSAGE;
        TextView tv_message = (TextView) view.findViewById(R.id.tv_progress_message);
        tv_message.setText(message);
        return view;
    }
}
