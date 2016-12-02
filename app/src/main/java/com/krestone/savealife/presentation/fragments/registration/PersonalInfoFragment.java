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

public class PersonalInfoFragment extends Fragment {

    @BindView(R.id.sign_up_btn)
    Button signUpBtn;

    private PersonalInfoListener personalInfoListener;

    public interface PersonalInfoListener {

        void onSignUpClick();
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
        signUpBtn.setOnClickListener(v -> personalInfoListener.onSignUpClick());

        return fragmentView;
    }
}
