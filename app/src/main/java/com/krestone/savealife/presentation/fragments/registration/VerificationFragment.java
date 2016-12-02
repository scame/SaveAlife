package com.krestone.savealife.presentation.fragments.registration;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.krestone.savealife.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VerificationFragment extends Fragment {

    @BindView(R.id.verification_btn)
    Button verificationButton;

    private VerificationListener verificationListener;

    public interface VerificationListener {

        void onVerificationClick();
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
