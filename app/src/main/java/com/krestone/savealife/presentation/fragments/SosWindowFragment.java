package com.krestone.savealife.presentation.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.krestone.savealife.R;
import com.krestone.savealife.data.entities.responses.map.MapObject;
import com.krestone.savealife.presentation.activities.DrawerActivity;
import com.krestone.savealife.presentation.presenters.map.SosWindowPresenter;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import icepick.State;

public class SosWindowFragment extends AbstractFragment implements SosWindowPresenter.SosWindowView {

    @BindView(R.id.sos_window_root)
    LinearLayout sosWindowRoot;

    @BindView(R.id.address_tv)
    TextView addressTv;

    @BindView(R.id.accident_time)
    TextView accidentTimeTv;

    @BindView(R.id.dismiss_window_btn)
    ImageButton dismissBtn;

    @BindView(R.id.help_btn)
    Button helpButton;

    @Inject
    SosWindowPresenter<SosWindowPresenter.SosWindowView> presenter;

    @State
    MapObject mapObject;

    private SosWindowListener sosWindowListener;

    private ProgressDialog progressDialog;

    public interface SosWindowListener {

        void onHelpRouteBuilt(PolylineOptions polyline, MapObject mappedObj);
    }

    public static SosWindowFragment newInstance(MapObject mapObject) {
        SosWindowFragment sosWindowFragment = new SosWindowFragment();
        Bundle args = new Bundle();

        args.putParcelable(SosWindowFragment.class.getCanonicalName(), mapObject);
        sosWindowFragment.setArguments(args);

        return sosWindowFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof SosWindowListener) {
            sosWindowListener = (SosWindowListener) getActivity();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        parseArgs();
        presenter.setView(this);

        return fragmentView;
    }

    private void parseArgs() {
        this.mapObject = getArguments().getParcelable(SosWindowFragment.class.getCanonicalName());
    }

    @Override
    protected void inject() {
        if (getActivity() instanceof DrawerActivity) {
            ((DrawerActivity) getActivity()).provideSosWindowComponent().inject(this);
        }
    }

    @OnClick(R.id.dismiss_window_btn)
    public void onDismissClick(View v) {
        getFragmentManager().popBackStack();
    }


    @OnClick(R.id.help_btn)
    public void onHelpBtnClick(View v) {
        showProgressDialog();
        sosWindowRoot.setVisibility(View.GONE);
        LatLng targetLatLng = new LatLng(mapObject.getLatitude(), mapObject.getLongitude());
        presenter.promoteHelpIntent(targetLatLng, mapObject.getPhoneNumber(), true);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.sos_window;
    }

    @Override
    public void onHelpPressed(PolylineOptions polyline) {
        progressDialog.dismiss();
        sosWindowListener.onHelpRouteBuilt(polyline, mapObject);
    }

    @Override
    public void onError(String error) {
        progressDialog.dismiss();
        sosWindowRoot.setVisibility(View.VISIBLE);
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setOnCancelListener(dialog -> presenter.progressDialogCancel());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Building the route...");
        progressDialog.show();
    }

    @Override
    public void onDestroyView() {
        presenter.destroy();
        super.onDestroyView();
    }
}
