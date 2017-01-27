package com.krestone.savealife.presentation.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.krestone.savealife.R;
import com.krestone.savealife.presentation.activities.DrawerActivity;
import com.krestone.savealife.presentation.presenters.DashboardPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import icepick.State;

public class DashboardFragment extends AbstractFragment implements DashboardPresenter.DashboardView {

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

    @Inject
    DashboardPresenter<DashboardPresenter.DashboardView> presenter;

    @State
    boolean lastState;

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
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        mapButton.setOnClickListener(v -> dashboardListener.onOpenMapClick());
        presenter.setView(this);
        presenter.getMyProfileInfo();
        driverModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> presenter.updateMyProfileInfo(isChecked));
        restoreSosState(savedInstanceState);

        return fragmentView;
    }

    private void restoreSosState(Bundle savedInstanceState) {
        boolean isActivated;
        if (savedInstanceState != null) {
            isActivated = savedInstanceState.getBoolean(getString(R.string.dashboard_is_activated));
        } else {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
            isActivated = prefs.getBoolean(getString(R.string.dashboard_is_activated), false);
        }
        if (isActivated) {
            initActiveSosState();
        }
    }

    private void persistSosState() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        prefs.edit().putBoolean(getString(R.string.dashboard_is_activated), sosButton.getTag() != null).apply();
    }

    @Override
    protected void inject() {
        if (getActivity() instanceof DrawerActivity) {
            ((DrawerActivity) getActivity()).provideDashboardComponent().inject(this);
        }
    }

    @Override
    public void onStartSosCompleted() {
        initActiveSosState();
    }

    @Override
    public void onStopSosCompleted() {
        initInactiveSosState();
    }

    @Override
    public void onError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayIsDriverState(boolean isDriver) {
        this.lastState = isDriver;
        driverModeSwitch.setChecked(isDriver);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.dashboard_layout;
    }

    @OnClick(R.id.sos_btn)
    public void onSosButtonClick(View v) {
        if (sosButton.getTag() == null) {
            presenter.startSos();
        } else {
            presenter.stopSos();
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(getString(R.string.dashboard_is_activated), sosButton.getTag() != null);
    }

    @Override
    public void onDestroyView() {
        persistSosState();
        presenter.destroy();
        super.onDestroyView();
    }
}
