package com.krestone.savealife.presentation.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.krestone.savealife.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardFragment extends Fragment {

    @BindView(R.id.root_dashboard_container)
    RelativeLayout rootContainer;

    @BindView(R.id.dashboard_location_tv)
    TextView locationTv;

    @BindView(R.id.dashboard_map_btn)
    ImageButton mapButton;

    @BindView(R.id.sos_text)
    TextView sosText;

    @BindView(R.id.sos_btn)
    ImageView sosButton;

    @BindView(R.id.driver_mode_switch)
    Switch driverModeSwitch;

    @BindView(R.id.event_details_btn)
    Button eventDetailsBtn;

    private DashboardListener dashboardListener;

    public interface DashboardListener {

        void onOpenMapClick();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DashboardListener) {
            dashboardListener = ((DashboardListener) context);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.dashboard_layout, container, false);
        ButterKnife.bind(this, fragmentView);
        mapButton.setOnClickListener(v -> dashboardListener.onOpenMapClick());

        return fragmentView;
    }

    @OnClick(R.id.sos_btn)
    public void onClick(View view) {
        if (sosButton.getTag() == null) {
            initActiveSosState();
        } else {
            initInactiveSosState();
        }
    }

    private void initActiveSosState() {
        sosButton.setImageDrawable(getResources().getDrawable(R.drawable.black_background));
        sosButton.setTag(new Object());
        rootContainer.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        sosText.setText(getString(R.string.sos_active_text));
    }

    private void initInactiveSosState() {
        sosButton.setImageDrawable(getResources().getDrawable(R.drawable.placeholder));
        sosButton.setTag(null);
        sosText.setText(getString(R.string.sos_inactive_text));
        rootContainer.setBackgroundColor(getResources().getColor(android.R.color.background_light));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
