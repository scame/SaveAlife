package com.krestone.savealife.presentation.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.krestone.savealife.R;
import com.krestone.savealife.data.messages.NotificationsHandler;
import com.krestone.savealife.data.sqlite.models.AbstractMessage;
import com.krestone.savealife.presentation.activities.DrawerActivity;
import com.krestone.savealife.presentation.adapters.DividerItemDecoration;
import com.krestone.savealife.presentation.adapters.notifications.NotificationsAdapter;
import com.krestone.savealife.presentation.presenters.NotificationsPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import icepick.State;

public class NotificationsFragment extends AbstractFragment implements NotificationsPresenter.NotificationsView {

    @BindView(R.id.refresh_notifications_view)
    SwipeRefreshLayout refreshView;

    @BindView(R.id.notifications_rv)
    RecyclerView notificationsRv;

    @State
    ArrayList<AbstractMessage> messages;

    @Inject
    NotificationsHandler notificationsHandler;

    @Inject
    NotificationsPresenter<NotificationsPresenter.NotificationsView> notificationsPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        notificationsPresenter.setView(this);
        refreshView.setOnRefreshListener(() -> notificationsPresenter.requestMessages());
        getActivity().setTitle("Notifications");
        instantiateFragment();

        return fragmentView;
    }

    private void instantiateFragment() {
        if (messages == null || messages.isEmpty()) {
            notificationsPresenter.requestMessages();
        } else {
            displayMessages(messages);
        }
    }

    @Override
    protected void inject() {
        if (getActivity() instanceof DrawerActivity) {
            ((DrawerActivity) getActivity()).provideNotificationsComponent().inject(this);
        }
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.notifications_layout;
    }

    @Override
    public void displayMessages(List<AbstractMessage> messages) {
        this.messages = new ArrayList<>(messages);

        NotificationsAdapter adapter = new NotificationsAdapter(messages, getContext(), notificationsHandler);

        notificationsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        notificationsRv.addItemDecoration(new DividerItemDecoration(getContext()));
        notificationsRv.setHasFixedSize(true);
        notificationsRv.setAdapter(adapter);

        if (refreshView.isRefreshing()) refreshView.setRefreshing(false);
    }

    @Override
    public void onError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }
}
