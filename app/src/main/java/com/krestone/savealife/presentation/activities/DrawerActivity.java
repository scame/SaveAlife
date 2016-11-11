package com.krestone.savealife.presentation.activities;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.krestone.savealife.R;
import com.krestone.savealife.presentation.fragments.ChatsFragment;
import com.krestone.savealife.presentation.fragments.ContactsFragment;
import com.krestone.savealife.presentation.fragments.DashboardFragment;
import com.krestone.savealife.presentation.fragments.MapFragment;
import com.krestone.savealife.presentation.fragments.SettingsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DrawerActivity extends AppCompatActivity {

    private static final String DASHBOARD_FRAG_TAG = "dashboardFragment";
    private static final String MAP_FRAG_TAG = "mapFragment";
    private static final String CHATS_FRAG_TAG = "chatsFragment";
    private static final String CONTACTS_FRAG_TAG = "contactsFragment";

    private static final String SETTINGS_FRAG_TAG = "settingsFragment";

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.main_navigation_view)
    NavigationView navigationView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_activity);

        ButterKnife.bind(this);
        setupToolbar();
        navigationView.setNavigationItemSelectedListener(this::selectDrawerItem);
        setupDrawerToggle();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupDrawerToggle() {
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
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
                replaceFragment(CONTACTS_FRAG_TAG, new ContactsFragment());
                break;
            case R.id.settings_drawer_item:
                replaceFragment(SETTINGS_FRAG_TAG, new SettingsFragment());
            default:
                replaceFragment(DASHBOARD_FRAG_TAG, new DashboardFragment());
        }

        item.setChecked(true);
        setTitle(item.getTitle());
        drawerLayout.closeDrawers();

        return true;
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

    private void replaceFragment(String fragmentTag, android.app.Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(R.id.settings_drawer_item, fragment, fragmentTag)
                .commit();
    }
}
