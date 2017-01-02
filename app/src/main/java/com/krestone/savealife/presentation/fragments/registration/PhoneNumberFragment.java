package com.krestone.savealife.presentation.fragments.registration;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.krestone.savealife.R;
import com.krestone.savealife.presentation.activities.RegistrationActivity;
import com.krestone.savealife.presentation.presenters.entry.RegistrationNumberPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhoneNumberFragment extends Fragment implements RegistrationNumberPresenter.RegistrationNumberView {

    @BindView(R.id.phone_number_btn)
    Button phoneNumberBtn;

    @BindView(R.id.phone_number_input)
    EditText phoneNumberInput;

    @Inject
    RegistrationNumberPresenter<RegistrationNumberPresenter.RegistrationNumberView> presenter;

    private PhoneNumberListener phoneNumberListener;

    private ProgressDialog progressDialog;

    public interface PhoneNumberListener {

        void onPhoneNumberContinue(String phoneNumber);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PhoneNumberListener) {
            phoneNumberListener = ((PhoneNumberListener) context);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.phone_number_layout, container, false);
        ButterKnife.bind(this, fragmentView);

        inject();
        presenter.setView(this);

        setPhoneNumber();
        setupEditorActionListener();
        setupPhoneNumberBtn();

        return fragmentView;
    }

    private void setupEditorActionListener() {
        phoneNumberInput.setOnEditorActionListener((v, actionId, event) -> {
            boolean isValidKey = event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER;
            boolean isValidAction = actionId == EditorInfo.IME_ACTION_DONE;

            if (isValidKey || isValidAction) {
                continueWithNumber();
            }
            return false;
        });
    }

    private void setupPhoneNumberBtn() {
        phoneNumberBtn.setOnClickListener(v -> continueWithNumber());
    }

    private void continueWithNumber() {
        if (!phoneNumberInput.getText().toString().isEmpty()) {
            showProgressDialog();
            presenter.sendRegistrationNumber(phoneNumberInput.getText().toString());
        } else {
            phoneNumberInput.setError("invalid number");
        }
    }

    protected void showProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setOnCancelListener(dialog -> presenter.progressDialogCancel());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Validating...");
        progressDialog.show();
    }

    private void inject() {
        if (getActivity() instanceof RegistrationActivity) {
            ((RegistrationActivity) getActivity()).provideRegistrationNumberComponent().inject(this);
        }
    }

    private void setPhoneNumber() {
        TelephonyManager telephonyManager = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNumber = telephonyManager.getLine1Number();
        if (phoneNumber == null) phoneNumber = "";

        phoneNumberInput.setText(phoneNumber);
    }

    @Override
    public void onRegistrationNumberSent() {
        progressDialog.dismiss();
        phoneNumberListener.onPhoneNumberContinue(phoneNumberInput.getText().toString());
    }

    @Override
    public void onRegistrationNumberError(String error) {
        progressDialog.dismiss();
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        presenter.destroy();
        super.onDestroyView();
    }
}
