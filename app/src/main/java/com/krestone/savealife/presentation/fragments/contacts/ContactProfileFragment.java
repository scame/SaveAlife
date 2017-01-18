package com.krestone.savealife.presentation.fragments.contacts;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    @BindView(R.id.refresh_btn)
    Button refreshButton;

    @BindView(R.id.filled_profile_ll)
    LinearLayout filledProfileLl;

    @BindView(R.id.not_filled_profile_ll)
    LinearLayout notFilledProfileLl;

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

    private ProgressDialog progressDialog;

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
        parseArgs();

        return fragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchData();
    }

    private void parseArgs() {
        this.parsedNumber = getArguments().getString(ContactProfileFragment.class.getCanonicalName(), "");
    }

    private void fetchData() {
        if (profileEntity == null) {
            showProgressDialog();
            presenter.requestProfileInfo(parsedNumber);
        } else {
            displayProfileInfo(profileEntity);
        }
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setOnCancelListener(dialog -> presenter.progressDialogCancel());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Fetching...");
        progressDialog.show();
    }

    @Override
    public void displayProfileInfo(SomeoneProfileEntity profileEntity) {
        checkVisibility();
        progressDialog.dismiss();

        // TODO: handle profile image
        username.setText(profileEntity.getFirstName());
        statusTv.setText(profileEntity.getRole());
        phoneNumber.setText(profileEntity.getPhoneNumber());
        medicalSkills.setText(profileEntity.getMedicalQualification());
    }

    private void checkVisibility() {
        if (filledProfileLl.getVisibility() == View.GONE) {
            filledProfileLl.setVisibility(View.VISIBLE);
            notFilledProfileLl.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.refresh_btn)
    public void onRefreshClick(View v) {
        showProgressDialog();
        presenter.requestProfileInfo(parsedNumber);
    }

    @OnClick(R.id.remove_contact_btn)
    public void onRemoveContactClick(View v) {
        presenter.removeContact(parsedNumber);
        removeContactBtn.setImageDrawable(null);
    }

    @Override
    public void onError(String error) {
        progressDialog.dismiss();

        if (error.equals(getString(R.string.internet_connection_check))
                && notFilledProfileLl.getVisibility() == View.GONE) {
            filledProfileLl.setVisibility(View.GONE);
            notFilledProfileLl.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void inject() {
        if (getActivity() instanceof DrawerActivity) {
            ((DrawerActivity) getActivity()).provideContactProfileComponent().inject(this);
        }
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.contact_profile_layout;
    }

    @OnClick(R.id.call_btn)
    public void onCallClick(View v) {
        // TODO: handle on call click
    }
}
