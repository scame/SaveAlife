package com.krestone.savealife.presentation.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.krestone.savealife.SaveAlifeApplication;
import com.krestone.savealife.data.websockets.SocketTest;

public class ChatsFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SocketTest socketTest = ((SaveAlifeApplication) getActivity().getApplication()).getSocketTest();
        socketTest.connect();
    }
}
