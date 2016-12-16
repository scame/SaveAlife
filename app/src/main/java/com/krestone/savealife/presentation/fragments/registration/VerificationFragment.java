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

import com.krestone.savealife.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VerificationFragment extends Fragment {

    @BindView(R.id.verification_btn)
    Button verificationButton;

    @BindView(R.id.verification_input)
    EditText verificationInput;

    private VerificationListener verificationListener;

    public interface VerificationListener {

        void onVerificationClick();
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
        verificationButton.setOnClickListener(v -> verificationListener.onVerificationClick());

        return fragmentView;
    }
}
