package com.krestone.savealife.presentation.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.krestone.savealife.R;
import com.krestone.savealife.presentation.activities.DrawerActivity;
import com.krestone.savealife.presentation.presenters.MyProfilePresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyProfileFragment extends Fragment implements MyProfilePresenter.MyProfileView {

    private MyProfileListener myProfileListener;

    @BindView(R.id.profile_qualifications_spinner)
    MaterialSpinner qualificationSpinner;

    @Inject
    MyProfilePresenter<MyProfilePresenter.MyProfileView> presenter;

    public interface MyProfileListener {

        void onSignOutClick();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.profile_layout, container, false);
        ButterKnife.bind(this, fragmentView);

        inject();
        setupListener();
        presenter.setView(this);
        qualificationSpinner.setItems("No medical qualification", "Surgeon", "Nurse");

        return fragmentView;
    }

    private void setupListener() {
        if (getActivity() instanceof MyProfileListener) {
            myProfileListener = (MyProfileListener) getActivity();
        }
    }

    private void inject() {
        if (getActivity() instanceof DrawerActivity) {
            ((DrawerActivity) getActivity()).provideMyProfileComponent().inject(this);
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
}
