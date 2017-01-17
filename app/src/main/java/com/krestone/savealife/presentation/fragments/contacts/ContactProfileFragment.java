package com.krestone.savealife.presentation.fragments.contacts;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.krestone.savealife.R;
import com.krestone.savealife.data.entities.responses.SomeoneProfileEntity;
import com.krestone.savealife.presentation.fragments.AbstractFragment;

import butterknife.BindView;
import butterknife.OnClick;
import icepick.State;

public class ContactProfileFragment extends AbstractFragment {

    @BindView(R.id.remove_contact_btn)
    ImageButton removeContactBtn;

    @BindView(R.id.profile_image)
    ImageView profileImage;

    @BindView(R.id.username_tv)
    TextView username;

    @BindView(R.id.status_tv)
    TextView statusTv;

    @BindView(R.id.phone_number_tv)
    TextView phoneNumber;

    @BindView(R.id.skills_tv)
    TextView medicalSkills;

    @State
    SomeoneProfileEntity profileEntity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        return fragmentView;
    }

    @Override
    protected void inject() {

    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.others_profile_layout;
    }

    @OnClick(R.id.call_btn)
    public void onCallClick(View v) {
        // TODO: handle on call click
    }
}
