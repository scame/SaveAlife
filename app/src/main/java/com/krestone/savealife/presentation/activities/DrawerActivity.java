package com.krestone.savealife.presentation.activities;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.krestone.savealife.R;
import com.krestone.savealife.SaveAlifeApplication;
import com.krestone.savealife.data.messages.NotificationsHandler;
import com.krestone.savealife.presentation.di.components.AddToEmergencyListComponent;
import com.krestone.savealife.presentation.di.components.ContactProfileComponent;
import com.krestone.savealife.presentation.di.components.DashboardComponent;
import com.krestone.savealife.presentation.di.components.EmergencyComponent;
import com.krestone.savealife.presentation.di.components.MapComponent;
import com.krestone.savealife.presentation.di.components.MyProfileComponent;
import com.krestone.savealife.presentation.di.components.SosWindowComponent;
import com.krestone.savealife.presentation.di.modules.AddToEmergencyListModule;
import com.krestone.savealife.presentation.di.modules.ContactProfileModule;
import com.krestone.savealife.presentation.di.modules.DashboardModule;
import com.krestone.savealife.presentation.di.modules.DrawerModule;
import com.krestone.savealife.presentation.di.modules.EmergencyModule;
import com.krestone.savealife.presentation.di.modules.MapModule;
import com.krestone.savealife.presentation.di.modules.MyProfileModule;
import com.krestone.savealife.presentation.di.modules.SosWindowModule;
import com.krestone.savealife.presentation.fragments.ChatsFragment;
import com.krestone.savealife.presentation.fragments.DashboardFragment;
import com.krestone.savealife.presentation.fragments.MapFragment;
import com.krestone.savealife.presentation.fragments.MyProfileFragment;
import com.krestone.savealife.presentation.fragments.SettingsFragment;
import com.krestone.savealife.presentation.fragments.SosWindowFragment;
import com.krestone.savealife.presentation.fragments.contacts.AddToEmergencyListFragment;
import com.krestone.savealife.presentation.fragments.contacts.ContactProfileFragment;
import com.krestone.savealife.presentation.fragments.contacts.EmergencyContactsFragment;
import com.krestone.savealife.presentation.presenters.DrawerActivityPresenter;
import com.krestone.savealife.util.InvitationUtil;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DrawerActivity extends AppCompatActivity implements
        EmergencyContactsFragment.EmergencyListener, DashboardFragment.DashboardListener,
        DrawerActivityPresenter.DrawerView, MyProfileFragment.MyProfileListener,
        SosWindowFragment.SosWindowListener {

    private static final String DASHBOARD_FRAG_TAG = "dashboardFragment";
    private static final String MAP_FRAG_TAG = "mapFragment";
    private static final String CHATS_FRAG_TAG = "chatsFragment";
    private static final String ADD_TO_CONTACTS_FRAG_TAG = "contactsFragment";
    private static final String EMERGENCY_CONTACTS_TAG = "emergencyContacts";
    private static final String MY_PROFILE_TAG = "myProfile";
    private static final String CONTACT_PROFILE_TAG = "contactProfile";

    private static final String SETTINGS_FRAG_TAG = "settingsFragment";

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.main_navigation_view)
    NavigationView navigationView;

    @Nullable
    @BindView(R.id.profile_image)
    ImageView profileImage;

    @Nullable
    @BindView(R.id.username_tv)
    TextView usernameTv;

    @Nullable
    @BindView(R.id.header_button)
    ImageButton headerButton;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    DrawerActivityPresenter<DrawerActivityPresenter.DrawerView> presenter;

    private ActionBarDrawerToggle drawerToggle;

    private MapComponent mapComponent;

    private AddToEmergencyListComponent addToEmergencyListComponent;

    private EmergencyComponent emergencyComponent;

    private MyProfileComponent myProfileComponent;

    private ContactProfileComponent contactProfileComponent;

    private SosWindowComponent sosWindowComponent;

    private DashboardComponent dashboardComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_activity);

        inject();
        checkIfNotificationIntent();
        presenter.setView(this);
        presenter.checkLoginStatus();

        ButterKnife.bind(this);
        navigationView.setNavigationItemSelectedListener(this::selectDrawerItem);
        setupDrawerToggle();

        bindHeaderViews();
        configureHeaderView();
    }

    private void checkIfNotificationIntent() {
        Bundle extras = getIntent().getExtras();
        int messageType = extras.getInt(NotificationsHandler.MESSAGE_EXTRA, -1);

        if (messageType == NotificationsHandler.SOS_MESSAGE_ID) {
            String targetNumber = extras.getString(NotificationsHandler.SOS_NUMBER_EXTRA, "");
            replaceFragment(MAP_FRAG_TAG, MapFragment.fromNotification(targetNumber));
        } else if (messageType == NotificationsHandler.HELP_INTENT_MESSAGE_ID) {
            replaceFragment(CHATS_FRAG_TAG, new ChatsFragment());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        configureToolbar();
    }

    private void inject() {
        SaveAlifeApplication.getAppComponent().provideDrawerComponent(new DrawerModule()).inject(this);
    }

    @Override
    public void startEntryProcess() {
        Intent entryIntent = new Intent(this, RegistrationActivity.class);
        startActivity(entryIntent);
        finish();
    }

    @Override
    public void goNormal() {
        setupDefaultFragment();
    }

    private void bindHeaderViews() {
        usernameTv = ButterKnife.findById(navigationView.getHeaderView(0), R.id.username_tv);
        headerButton = ButterKnife.findById(navigationView.getHeaderView(0), R.id.header_button);
    }

    private void configureHeaderView() {
        usernameTv.setText("Slava Petrochenko");
        headerButton.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.drawer_activity_fl, new MyProfileFragment(), MY_PROFILE_TAG)
                    .commit();
            drawerLayout.closeDrawers();
        });
    }

    private void setupDefaultFragment() {
        if (getSupportFragmentManager().getFragments() == null) {
            replaceFragment(DASHBOARD_FRAG_TAG, new DashboardFragment());
        }
    }

    private void configureToolbar() {
        setSupportActionBar(toolbar);

        notEmptyStackCheck();

        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            notEmptyStackCheck();
            emptyStackCheck();
        });
    }

    private void notEmptyStackCheck() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            drawerToggle.setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
        }
    }

    private void emptyStackCheck() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            drawerToggle.setDrawerIndicatorEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            drawerToggle.syncState();
            toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
        }
    }

    private void setupDrawerToggle() {
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
    }

    private boolean selectDrawerItem(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dashboard_drawer_item:
                replaceFragment(DASHBOARD_FRAG_TAG, new DashboardFragment());
                break;
            case R.id.map_drawer_item:
                replaceFragment(MAP_FRAG_TAG, new MapFragment());
                break;
            case R.id.chats_drawer_item:
                replaceFragment(CHATS_FRAG_TAG, new ChatsFragment());
                break;
            case R.id.contacts_drawer_item:
                replaceFragment(EMERGENCY_CONTACTS_TAG, new EmergencyContactsFragment());
                break;
            case R.id.settings_drawer_item:
                replaceFragment(SETTINGS_FRAG_TAG, new SettingsFragment());
                break;
            case R.id.invite_drawer_item:
                InvitationUtil.showInviteWindow(getApplicationContext(), "");
                break;
            default:
                replaceFragment(DASHBOARD_FRAG_TAG, new DashboardFragment());
        }

        item.setChecked(true);
        setTitle(item.getTitle());
        drawerLayout.closeDrawers();

        return true;
    }

    @Override
    public void onHelpRouteBuilt(PolylineOptions helpRoute, String targetPhoneNumber) {
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentByTag(MAP_FRAG_TAG);
        if (mapFragment != null) {
            mapFragment.onHelpRouteBuilt(helpRoute, targetPhoneNumber);
        }
    }

    @Override
    public void onContactProfileClick(String phoneNumber) {
        replaceFragmentWithBackstack(ContactProfileFragment.newInstance(phoneNumber), CONTACT_PROFILE_TAG);
    }

    @Override
    public void onAddToEmergencyListClick() {
        replaceFragmentWithBackstack(new AddToEmergencyListFragment(), ADD_TO_CONTACTS_FRAG_TAG);
    }

    private void replaceFragmentWithBackstack(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.drawer_activity_fl, fragment, tag)
                .commit();
    }

    @Override
    public void onOpenMapClick() {
        replaceFragment(MAP_FRAG_TAG, new MapFragment());
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    private void replaceFragment(String fragmentTag, Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.drawer_activity_fl, fragment, fragmentTag)
                .commit();
    }

    public DashboardComponent provideDashboardComponent() {
        if (dashboardComponent == null) {
            dashboardComponent = SaveAlifeApplication.getAppComponent()
                    .provideDashboardComponent(new DashboardModule());
        }
        return dashboardComponent;
    }

    public ContactProfileComponent provideContactProfileComponent() {
        if (contactProfileComponent == null) {
            contactProfileComponent = SaveAlifeApplication.getAppComponent()
                    .provideContactProfileComponent(new ContactProfileModule());
        }
        return contactProfileComponent;
    }

    public MapComponent provideMapComponent() {
        if (mapComponent == null) {
            mapComponent = SaveAlifeApplication.getAppComponent().provideMapComponent(new MapModule());
        }
        return mapComponent;
    }

    public AddToEmergencyListComponent provideContactsComponent() {
        if (addToEmergencyListComponent == null) {
            addToEmergencyListComponent = SaveAlifeApplication.getAppComponent()
                    .provideAddToEmergencyListComponent(new AddToEmergencyListModule());
        }
        return addToEmergencyListComponent;
    }

    public EmergencyComponent provideEmergencyComponent() {
        if (emergencyComponent == null) {
            emergencyComponent = SaveAlifeApplication.getAppComponent().provideEmergencyComponent(new EmergencyModule());
        }
        return emergencyComponent;
    }

    public MyProfileComponent provideMyProfileComponent() {
        if (myProfileComponent == null) {
            myProfileComponent = SaveAlifeApplication.getAppComponent().provideMyProfileComponent(new MyProfileModule());
        }
        return myProfileComponent;
    }

    public SosWindowComponent provideSosWindowComponent() {
        if (sosWindowComponent == null) {
            sosWindowComponent = SaveAlifeApplication.getAppComponent().provideSosWindowComponent(new SosWindowModule());
        }
        return sosWindowComponent;
    }

    @Override
    public void onSignOutClick() {
        startEntryProcess();
    }
}
