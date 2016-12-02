package com.krestone.savealife.presentation.fragments.registration;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.krestone.savealife.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhoneNumberFragment extends Fragment {

    @BindView(R.id.phone_number_btn)
    Button phoneNumberBtn;

    @BindView(R.id.phone_number_input)
    EditText phoneNumberInput;

    private PhoneNumberListener phoneNumberListener;

    public interface PhoneNumberListener {

        void onPhoneNumberContinue();
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
        phoneNumberBtn.setOnClickListener(v -> phoneNumberListener.onPhoneNumberContinue());
        setPhoneNumber();

        return fragmentView;
    }

    private void setPhoneNumber() {
        TelephonyManager telephonyManager = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNumber = telephonyManager.getLine1Number();
        if (phoneNumber == null) phoneNumber = "";

        phoneNumberInput.setText(phoneNumber);
    }
}
