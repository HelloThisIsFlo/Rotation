package com.shockn745.wireinterview;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.shockn745.wireinterview.fragments.GyroFragment;
import com.shockn745.wireinterview.fragments.MainFragment;


/**
 * Main activity of the application
 *
 * @author Florian Kempenich
 */
public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final long DRAWER_CLOSE_DELAY_MS = 250;
    private static final String NAV_ITEM_ID = "navItemId";

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private int mNavItemId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Fragment added later in "updateFragment" method

        // Add & set Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        // Find Drawer by id
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Load saved navigation state if present
        if (null == savedInstanceState) {
            mNavItemId = R.id.drawer_main_demo;
        } else {
            mNavItemId = savedInstanceState.getInt(NAV_ITEM_ID);
        }

        // Listen for navigation events
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        // Select the correct nav menu item
        navigationView.getMenu().findItem(mNavItemId).setChecked(true);

        // Set up the hamburger icon to open and close the drawer
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // Update main fragment
        updateFragment(mNavItemId);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    /**
     * Update the fragment on main activity
     * @param itemId Id of the clicked navigation item
     */
    private void updateFragment(final int itemId) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (itemId) {
            case R.id.drawer_main_demo:
                fragmentTransaction.replace(R.id.content_frame, new MainFragment());
                fragmentTransaction.commit();
                break;

            case R.id.drawer_gyro_demo:
                if (Build.VERSION.SDK_INT >= 18) {
                    fragmentTransaction.replace(R.id.content_frame, new GyroFragment());
                    fragmentTransaction.commit();
                } else {
                    Toast.makeText(this, R.string.api_18, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.drawer_material_demo:
                Toast.makeText(this, "Implement material demo", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Method to handle click on NavigationItem
     * @param menuItem Clicked navigationItem
     * @return true
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        // Gravity start is used to determine which drawer to select
        // Here : start (left side for "regular" device configuration : left -> right)
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }

        menuItem.setChecked(true);

        // Update fragment after drawer closed
        final int itemId = menuItem.getItemId();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                updateFragment(itemId);
            }
        }, DRAWER_CLOSE_DELAY_MS*2);
        return true;
    }


    /**
     * Close the drawer when back is pressed && drawer opened
     */
    @Override
    public void onBackPressed() {
        // Gravity start is used to determine which drawer to select
        // Here : start (left side for "regular" device configuration : left -> right)
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Save the drawer state in the Bundle
     * @param outState Bundle to save the drawer state
     */
    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NAV_ITEM_ID, mNavItemId);
    }
}
