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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.krestone.savealife.R;
import com.krestone.savealife.data.entities.responses.SomeoneProfileEntity;
import com.krestone.savealife.presentation.activities.RegistrationActivity;
import com.krestone.savealife.presentation.presenters.entry.SignInPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInFragment extends Fragment implements SignInPresenter.SignInView {

    @BindView(R.id.sign_in_text)
    TextView signInText;

    @BindView(R.id.sign_in_pswd_input)
    EditText passwordInput;

    @Inject
    SignInPresenter<SignInPresenter.SignInView> presenter;

    private SignInListener signInListener;

    private SomeoneProfileEntity profileEntity;

    private ProgressDialog progressDialog;

    public static SignInFragment newInstance(SomeoneProfileEntity profileEntity) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(SignInFragment.class.getCanonicalName(), profileEntity);

        SignInFragment signInFragment = new SignInFragment();
        signInFragment.setArguments(bundle);

        return signInFragment;
    }

    public interface SignInListener {
        void onSignIn();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SignInListener) {
            signInListener = (SignInListener) getActivity();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.sign_in_layout, container, false);
        ButterKnife.bind(this, fragmentView);

        inject();
        parseArgs();
        setupSignInText();
        presenter.setView(this);
        setupEditorActionListener();

        return fragmentView;
    }

    @OnClick(R.id.register_new_acc_btn)
    public void onNewAccountClick(View v) {
        getFragmentManager().popBackStack();
    }

    @OnClick(R.id.sign_in_btn)
    public void onSignInClick(View v) {
        processPassword();
    }

    @OnClick(R.id.forgot_pswd_text)
    public void forgotPasswordClick(View v) {
        // TODO: implement password recovery
    }

    private void processPassword() {
        if (validatePassword()) {
            showProgressDialog();
            presenter.requestSignIn(passwordInput.getText().toString(), profileEntity);
        }
    }

    private boolean validatePassword() {
        String password = passwordInput.getText().toString();
        boolean valid = true;

        if (password.isEmpty() || password.length() < 6 || password.length() > 16) {
            passwordInput.setError("between 6 and 16 characters");
            valid = false;
        }
        return valid;
    }

    private void setupEditorActionListener() {
        passwordInput.setOnEditorActionListener((v, actionId, event) -> {
            boolean isValidKey = event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER;
            boolean isValidAction = actionId == EditorInfo.IME_ACTION_DONE;

            if (isValidKey || isValidAction) {
                processPassword();
            }
            return false;
        });
    }

    private void setupSignInText() {
        String formattedText = getString(R.string.sign_in_text, profileEntity.getFirstName());
        signInText.setText(formattedText);
    }

    private void parseArgs() {
        profileEntity = getArguments().getParcelable(SignInFragment.class.getCanonicalName());
    }

    protected void showProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setOnCancelListener(dialog -> presenter.progressDialogCancel());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Checking...");
        progressDialog.show();
    }

    private void inject() {
        if (getActivity() instanceof RegistrationActivity) {
            ((RegistrationActivity) getActivity()).provideSignInComponent().inject(this);
        }
    }

    @Override
    public void onSignInSuccess() {
        progressDialog.dismiss();
        signInListener.onSignIn();
    }

    @Override
    public void onSignInErr(String error) {
        progressDialog.dismiss();
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView() {
        presenter.destroy();
        super.onDestroyView();
    }
}
