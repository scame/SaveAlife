package com.krestone.savealife.presentation.fragments.entry;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.krestone.savealife.presentation.presenters.entry.VerificationPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VerificationFragment extends Fragment implements VerificationPresenter.VerificationView {

    @BindView(R.id.verification_btn)
    Button verificationButton;

    @BindView(R.id.verification_input)
    EditText verificationInput;

    @Inject
    VerificationPresenter<VerificationPresenter.VerificationView> presenter;

    private ProgressDialog progressDialog;

    private VerificationListener verificationListener;

    private String phoneNumber;

    public interface VerificationListener {

        void onVerificationClick(String phoneNumber, String verificationCode);
    }

    public static VerificationFragment newInstance(String phoneNumber) {
        Bundle bundle = new Bundle();
        bundle.putString(VerificationFragment.class.getCanonicalName(), phoneNumber);

        VerificationFragment verificationFragment = new VerificationFragment();
        verificationFragment.setArguments(bundle);
        return verificationFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof VerificationListener) {
            verificationListener = ((VerificationListener) context);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.phone_verification_layout, container, false);
        ButterKnife.bind(this, fragmentView);
        inject();
        presenter.setView(this);

        parsePhoneNumber();
        setupVerificationBtn();
        setupEditorActionListener();
        ((RegistrationActivity) getActivity()).getSupportActionBar().show();

        return fragmentView;
    }

    private void setupVerificationBtn() {
        verificationButton.setOnClickListener(v -> tryToVerify());
    }

    private void setupEditorActionListener() {
        verificationInput.setOnEditorActionListener((v, actionId, event) -> {
            boolean isValidKey = event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER;
            boolean isValidAction = actionId == EditorInfo.IME_ACTION_DONE;

            if (isValidKey || isValidAction) {
                tryToVerify();
            }
            return false;
        });
    }

    private void tryToVerify() {
        if (verificationInput.getText().toString().isEmpty()) {
            verificationInput.setError("invalid code");
        } else {
            showProgressDialog();
            presenter.verify(phoneNumber, verificationInput.getText().toString());
        }
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setOnCancelListener(dialog -> presenter.progressDialogCancel());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
    }

    @Override
    public void onVerificationSuccess() {
        progressDialog.dismiss();
        verificationListener.onVerificationClick(phoneNumber, verificationInput.getText().toString());
    }

    @Override
    public void onVerificationErr(String error) {
        progressDialog.dismiss();
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    private void inject() {
        if (getActivity() instanceof RegistrationActivity) {
            ((RegistrationActivity) getActivity()).provideVerificationComponent().inject(this);
        }
    }

    private void parsePhoneNumber() {
        phoneNumber = getArguments().getString(getClass().getCanonicalName(), "");
    }

    @Override
    public void onDestroyView() {
        presenter.destroy();
        super.onDestroyView();
    }
}
