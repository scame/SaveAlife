package com.krestone.savealife.presentation.activities;


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
import com.krestone.savealife.presentation.di.components.ContactsComponent;
import com.krestone.savealife.presentation.di.components.EmergencyComponent;
import com.krestone.savealife.presentation.di.components.MapComponent;
import com.krestone.savealife.presentation.di.modules.ContactsModule;
import com.krestone.savealife.presentation.di.modules.EmergencyModule;
import com.krestone.savealife.presentation.di.modules.MapModule;
import com.krestone.savealife.presentation.fragments.ChatsFragment;
import com.krestone.savealife.presentation.fragments.ContactsFragment;
import com.krestone.savealife.presentation.fragments.DashboardFragment;
import com.krestone.savealife.presentation.fragments.EmergencyContactsFragment;
import com.krestone.savealife.presentation.fragments.MapFragment;
import com.krestone.savealife.presentation.fragments.MyProfileFragment;
import com.krestone.savealife.presentation.fragments.SettingsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DrawerActivity extends AppCompatActivity implements
        EmergencyContactsFragment.EmergencyListener, DashboardFragment.DashboardListener {

    private static final String DASHBOARD_FRAG_TAG = "dashboardFragment";
    private static final String MAP_FRAG_TAG = "mapFragment";
    private static final String CHATS_FRAG_TAG = "chatsFragment";
    private static final String CONTACTS_FRAG_TAG = "contactsFragment";
    private static final String EMERGENCY_CONTACTS_TAG = "emergencyContacts";
    private static final String MY_PROFILE_TAG = "myProfile";

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

    private ActionBarDrawerToggle drawerToggle;

    private MapComponent mapComponent;

    private ContactsComponent contactsComponent;

    private EmergencyComponent emergencyComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_activity);

        ButterKnife.bind(this);
        navigationView.setNavigationItemSelectedListener(this::selectDrawerItem);
        setupDrawerToggle();
        setupDefaultFragment();
        configureToolbar();

        bindHeaderViews();
        configureHeaderView();
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

        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                toolbar.setNavigationOnClickListener(v -> onBackPressed());
            } else {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                drawerToggle.syncState();
                toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
            }
        });
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
            default:
                replaceFragment(DASHBOARD_FRAG_TAG, new DashboardFragment());
        }

        item.setChecked(true);
        setTitle(item.getTitle());
        drawerLayout.closeDrawers();

        return true;
    }

    @Override
    public void onEditEmergencyListClick() {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.drawer_activity_fl, new ContactsFragment(), CONTACTS_FRAG_TAG)
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

    public MapComponent provideMapComponent() {
        if (mapComponent == null) {
            mapComponent = SaveAlifeApplication.getAppComponent().provideMapSubcomponent(new MapModule());
        }
        return mapComponent;
    }

    public ContactsComponent provideContactsComponent() {
        if (contactsComponent == null) {
            contactsComponent = SaveAlifeApplication.getAppComponent().provideContactsSubcomponent(new ContactsModule());
        }
        return contactsComponent;
    }

    public EmergencyComponent provideEmergencyComponent() {
        if (emergencyComponent == null) {
            emergencyComponent = SaveAlifeApplication.getAppComponent().provideEmergencySubcomponent(new EmergencyModule());
        }
        return emergencyComponent;
    }
}
