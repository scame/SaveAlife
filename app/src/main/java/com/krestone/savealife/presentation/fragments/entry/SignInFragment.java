package com.krestone.savealife.presentation.fragments.entry;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.krestone.savealife.data.entities.responses.SomeoneProfileEntity;
import com.krestone.savealife.presentation.activities.RegistrationActivity;
import com.krestone.savealife.presentation.presenters.entry.SignInPresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class SignInFragment extends Fragment implements SignInPresenter.SignInView {

    private SignInListener signInListener;

    private SomeoneProfileEntity profileEntity;

    @Inject
    SignInPresenter<SignInPresenter.SignInView> presenter;

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
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, fragmentView);

        inject();
        parseArgs();
        presenter.setView(this);

        return fragmentView;
    }

    private void parseArgs() {
        profileEntity = getArguments().getParcelable(SignInFragment.class.getCanonicalName());
    }

    private void inject() {
        if (getActivity() instanceof RegistrationActivity) {
            ((RegistrationActivity) getActivity()).provideSignInComponent().inject(this);
        }
    }

    @Override
    public void onSignInSuccess() {
        signInListener.onSignIn();
    }

    @Override
    public void onSignInErr(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }
}
