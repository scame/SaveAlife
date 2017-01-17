package com.krestone.savealife.presentation.fragments.contacts;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.krestone.savealife.R;
import com.krestone.savealife.data.entities.responses.SomeoneProfileEntity;
import com.krestone.savealife.presentation.activities.DrawerActivity;
import com.krestone.savealife.presentation.fragments.AbstractFragment;
import com.krestone.savealife.presentation.presenters.contacts.ContactProfilePresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import icepick.State;

public class ContactProfileFragment extends AbstractFragment implements ContactProfilePresenter.ContactProfileView {

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

    @Inject
    ContactProfilePresenter<ContactProfilePresenter.ContactProfileView> presenter;

    private String parsedNumber;

    public static ContactProfileFragment newInstance(String contactNumber) {

        Bundle args = new Bundle();
        args.putString(ContactProfileFragment.class.getCanonicalName(), contactNumber);

        ContactProfileFragment fragment = new ContactProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        presenter.setView(this);
        instantiateFragment();
        parseArgs();

        return fragmentView;
    }

    private void parseArgs() {
        this.parsedNumber = getArguments().getString(ContactProfileFragment.class.getCanonicalName(), "");
    }

    private void instantiateFragment() {
        if (profileEntity == null) {
            presenter.requestProfileInfo(parsedNumber);
        } else {
            displayProfileInfo(profileEntity);
        }
    }

    @Override
    public void displayProfileInfo(SomeoneProfileEntity profileEntity) {
        // TODO: handle profile image
        username.setText(profileEntity.getFirstName());
        statusTv.setText(profileEntity.getRole());
        phoneNumber.setText(profileEntity.getPhoneNumber());
        medicalSkills.setText(profileEntity.getMedicalQualification());
    }

    @Override
    public void onError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void inject() {
        if (getActivity() instanceof DrawerActivity) {
            ((DrawerActivity) getActivity()).provideContactProfileComponent().inject(this);
        }
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
