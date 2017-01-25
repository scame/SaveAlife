package com.krestone.savealife.presentation.fragments;


import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.krestone.savealife.R;
import com.krestone.savealife.data.entities.responses.MyProfileInfoEntity;
import com.krestone.savealife.data.sync.SyncService;
import com.krestone.savealife.data.sync.events.BroadcastsMeta;
import com.krestone.savealife.data.sync.events.SyncType;
import com.krestone.savealife.presentation.activities.DrawerActivity;
import com.krestone.savealife.presentation.presenters.MyProfilePresenter;
import com.krestone.savealife.presentation.receivers.SyncEventReceiver;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import icepick.State;

public class MyProfileFragment extends AbstractFragment implements MyProfilePresenter.MyProfileView {

    @BindView(R.id.swipe_view)
    SwipeRefreshLayout swipeView;

    @BindView(R.id.contact_profile_image)
    ImageView contactImage;

    @BindView(R.id.status_switch)
    Switch statusSwitch;

    @BindView(R.id.profile_first_name)
    TextView profileFirstName;

    @BindView(R.id.profile_last_name)
    TextView profileLastName;

    @BindView(R.id.profile_number)
    TextView profileNumber;

    @BindView(R.id.profile_email)
    TextView profileEmail;

    @BindView(R.id.profile_sex)
    TextView profileSex;

    @BindView(R.id.profile_age)
    TextView profileAge;

    @BindView(R.id.profile_diseases)
    TextView profileDiseases;

    @BindView(R.id.profile_qualifications_spinner)
    MaterialSpinner qualificationSpinner;

    @Inject
    MyProfilePresenter<MyProfilePresenter.MyProfileView> presenter;

    @State
    MyProfileInfoEntity profileInfo;

    private MyProfileListener myProfileListener;

    private SyncEventReceiver receiver;

    public interface MyProfileListener {

        void onSignOutClick();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        setupListener();
        presenter.setView(this);
        instantiateFragment();
        qualificationSpinner.setItems("No medical qualification", "Surgeon", "Nurse");
        swipeView.setOnRefreshListener(() -> SyncService.requestSync(SyncType.PROFILE, getContext()));
        createReceiver();

        return fragmentView;
    }

    private void createReceiver() {
        receiver = new SyncEventReceiver(swipeView, SyncType.PROFILE) {
            @Override
            public void syncLocally() {
                presenter.requestMyProfileInfo();
            }
        };
    }

    private void instantiateFragment() {
        if (profileInfo == null) {
            presenter.requestMyProfileInfo();
        } else {
            displayMyProfileInfo(profileInfo);
        }
    }

    private void setupListener() {
        if (getActivity() instanceof MyProfileListener) {
            myProfileListener = (MyProfileListener) getActivity();
        }
    }

    protected void inject() {
        if (getActivity() instanceof DrawerActivity) {
            ((DrawerActivity) getActivity()).provideMyProfileComponent().inject(this);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getContext())
                .registerReceiver(receiver, new IntentFilter(BroadcastsMeta.SYNC_RESPONSE));
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.profile_layout;
    }

    @Override
    public void displayMyProfileInfo(MyProfileInfoEntity profileInfo) {
        this.profileInfo = profileInfo;

        profileFirstName.setText(profileInfo.getFirstName());
        profileLastName.setText(profileInfo.getLastName());
        profileNumber.setText(profileInfo.getPhoneNumber());

        if (profileInfo.getRole().equals("driver")) {
            statusSwitch.setChecked(true);
        } else {
            statusSwitch.setChecked(false);
        }
    }

    @OnClick(R.id.sign_out_btn)
    void onSignOutClick(View view) {
        presenter.signOut();
    }

    @Override
    public void onSignOut() {
        myProfileListener.onSignOutClick();
    }

    @Override
    public void onDestroyView() {
        presenter.destroy();
        super.onDestroyView();
    }
}
