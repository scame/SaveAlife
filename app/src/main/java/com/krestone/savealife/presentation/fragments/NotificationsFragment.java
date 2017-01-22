package com.krestone.savealife.presentation.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.krestone.savealife.R;
import com.krestone.savealife.data.sqlite.models.AbstractMessage;

import java.util.ArrayList;

import butterknife.BindView;
import icepick.State;

public class NotificationsFragment extends AbstractFragment {

    @BindView(R.id.notifications_rv)
    RecyclerView notificationsRv;

    @State
    ArrayList<AbstractMessage> messages;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        return fragmentView;
    }

    @Override
    protected void inject() {

    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.notifications_layout;
    }
}
