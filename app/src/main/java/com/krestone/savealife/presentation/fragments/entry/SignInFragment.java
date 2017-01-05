package com.krestone.savealife.presentation.fragments.entry;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SignInFragment extends Fragment {

    public static SignInFragment newInstance(String phoneNumber) {
        Bundle bundle = new Bundle();
        bundle.putString(SignInFragment.class.getCanonicalName(), phoneNumber);

        SignInFragment signInFragment = new SignInFragment();
        signInFragment.setArguments(bundle);

        return signInFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
