package com.moysa.saveup.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;

import com.moysa.saveup.R;
import com.moysa.saveup.app.SaveUpApp;
import com.moysa.saveup.data.entity.UserEntity;
import com.moysa.saveup.data.response.Response;
import com.moysa.saveup.databinding.ActivityHomeBinding;
import com.moysa.saveup.databinding.NavHeaderHomeBinding;
import com.moysa.saveup.view.users.UsersSpinnerAdapter;
import com.moysa.saveup.view.users.UsersViewModel;
import com.moysa.saveup.view.users.UsersViewModelProviderFactory;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseLifecycleActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityHomeBinding mBinding;
    private UsersSpinnerAdapter mUsersAdapter;
    private UsersViewModel mUsersViewModel;
    private Spinner usersSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        Toolbar toolbar = mBinding.appBar.toolbar;
        setSupportActionBar(toolbar);

        FloatingActionButton fab = mBinding.appBar.fab;
        fab.setOnClickListener(view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", v -> {
                            if (mUsersViewModel != null) {
                                UserEntity user = new UserEntity();
                                user.setName("Test");
                                mUsersViewModel.create(user);
                            }
                        }).show());

        DrawerLayout drawer = mBinding.drawerLayout;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = mBinding.navView;
        navigationView.setNavigationItemSelectedListener(this);

        NavHeaderHomeBinding headerHomeBinding = DataBindingUtil
                .bind(mBinding.navView.getHeaderView(0));

        mUsersAdapter = new UsersSpinnerAdapter(this, new ArrayList<>());

        usersSpinner = headerHomeBinding.usersSpinner;
        usersSpinner.setAdapter(mUsersAdapter);
        usersSpinner.setSelection(1);
    }

    @Override
    protected void onStart() {
        super.onStart();

        SaveUpApp.getInstance().isDbCreated().observe(this, initialized -> {

            boolean dbInitialized = initialized != null ? initialized : false;
            mBinding.setDbInitialized(dbInitialized);

            if (dbInitialized) {
                initializeUsersViewModel();
            }
        });
    }

    private void initializeUsersViewModel() {
        UsersViewModelProviderFactory factory = new UsersViewModelProviderFactory(
                SaveUpApp.getInstance().getDatabase().userDao());

        mUsersViewModel = ViewModelProviders
                .of(HomeActivity.this, factory)
                .get(UsersViewModel.class);

        mUsersViewModel.getUsersList()
                .observe(HomeActivity.this, response -> {
                    if (response != null && response.getStatus() == Response.Status.SUCCESS) {

                        List<UserEntity> data = response.getData();

                        if (data != null) {
                            mUsersAdapter.clear();
                            mUsersAdapter.addAll(data);
                            usersSpinner.setSelection(1);
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = mBinding.drawerLayout;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Snackbar.make(usersSpinner, item.getTitle() + " pressed", Snackbar.LENGTH_LONG).show();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = mBinding.drawerLayout;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
