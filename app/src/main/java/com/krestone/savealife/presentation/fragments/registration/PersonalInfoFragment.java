package com.krestone.savealife.presentation.fragments.registration;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.krestone.savealife.R;
import com.krestone.savealife.presentation.activities.RegistrationActivity;
import com.krestone.savealife.presentation.presenters.PersonalInfoPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonalInfoFragment extends Fragment implements PersonalInfoPresenter.PersonalInfoView {

    @BindView(R.id.first_name_input)
    EditText firstNameInput;

    @BindView(R.id.last_name_input)
    EditText lastNameInput;

    @BindView(R.id.password_input)
    EditText passwordInput;

    @BindView(R.id.password_input_confirm)
    EditText passwordInputConfirm;

    @BindView(R.id.sign_up_btn)
    Button signUpBtn;

    @BindView(R.id.qualifications_spinner)
    MaterialSpinner qualificationSpinner;

    @Inject
    PersonalInfoPresenter<PersonalInfoPresenter.PersonalInfoView> presenter;

    private String phoneNumber;

    private String verifCode;

    private PersonalInfoListener personalInfoListener;

    public interface PersonalInfoListener {

        void onSignUpClick();
    }

    public static PersonalInfoFragment newInstance(String phoneNumber, String verifCode) {
        Bundle bundle = new Bundle();
        bundle.putString(PersonalInfoFragment.class.getCanonicalName() + "phone", phoneNumber);
        bundle.putString(PersonalInfoFragment.class.getCanonicalName() + "code", verifCode);

        PersonalInfoFragment personalInfoFragment = new PersonalInfoFragment();
        personalInfoFragment.setArguments(bundle);

        return personalInfoFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PersonalInfoListener) {
            personalInfoListener = ((PersonalInfoListener) context);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.sign_up_layout, container, false);
        ButterKnife.bind(this, fragmentView);
        parseArgs();
        inject();
        presenter.setView(this);
        setupSignUpBtn();

        qualificationSpinner.setItems("No medical qualification", "Surgeon", "Nurse");

        return fragmentView;
    }

    @Override
    public void onPersonalInfoSent() {
        personalInfoListener.onSignUpClick();
    }

    @Override
    public void onPersonalInfoErr(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    private void setupSignUpBtn() {
        signUpBtn.setOnClickListener(v -> {
            if (validate()) {
                presenter.sendPersonalInfo(firstNameInput.getText().toString(), lastNameInput.getText().toString(),
                        passwordInput.getText().toString(), phoneNumber, "No medical qualification", verifCode);
            }
        });
    }

    private void parseArgs() {
        phoneNumber = getArguments().getString(this.getClass().getCanonicalName() + "phone", "");
        verifCode = getArguments().getString(this.getClass().getCanonicalName() + "code", "");
    }

    private void inject() {
        if (getActivity() instanceof RegistrationActivity) {
            ((RegistrationActivity) getActivity()).providePersonalInfoComponent().inject(this);
        }
    }

    // TODO: implement validation
    private boolean validate() {
        return true;
    }
}
