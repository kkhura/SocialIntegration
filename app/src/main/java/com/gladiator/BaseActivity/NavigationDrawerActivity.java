package com.gladiator.BaseActivity;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;

import com.gladiator.Adapter.NavDrawerItem;
import com.gladiator.Adapter.NavigationDrawerAdapter;
import com.gladiator.CustomView.CustomFontTextView;
import com.gladiator.HomeScreen.Activity.HomeActivity;
import com.gladiator.HomeScreen.Fragment.ListingFragment;
import com.gladiator.Prefrence.SharedPref;
import com.gladiator.R;
import com.gladiator.Utility.Utils;
import com.gladiator.icomoon.IconTextView;

import java.util.ArrayList;

/**
 * Created by Kailash on 26-01-2017.
 */
public abstract class NavigationDrawerActivity extends AppCompatActivity {

    public static final int HOME = 0;
    public static final int MYORDER = 1;
    public static final int SETTINGS = 2;

    public String[] leftSliderData;
    public String[] navMenuIcons;
    public ArrayList<NavDrawerItem> navDrawerItems;

    public NavigationDrawerAdapter navigationDrawerAdapter;
    private String toolBarTitle;
    private android.support.v4.widget.DrawerLayout drawerLayout;
    private Toolbar mToolbar;
    private ImageButton btnBack;
    private IconTextView bt_toolbar_right;
    private CustomFontTextView toolbar_title;
    private IconTextView bt_toolbar_icon;
    private android.support.v4.app.FragmentManager supportFragmentManager;
    private ActionBarDrawerToggle drawerToggle;

    public NavigationDrawerActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        supportFragmentManager = getSupportFragmentManager();
    }


    public void setNavDrawerItems() {
        InitializeNavigationDrawerPatient();

    }

    public abstract void setNavigationDrawerAdapter();

    private void InitializeNavigationDrawerPatient() {
        navDrawerItems = new ArrayList<NavDrawerItem>();
        leftSliderData = getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons = getResources().getStringArray(R.array.nav_drawer_icons);

        navDrawerItems.add(new NavDrawerItem(leftSliderData[0], navMenuIcons[0], HOME));
        navDrawerItems.add(new NavDrawerItem(leftSliderData[1], navMenuIcons[1], MYORDER));
        navDrawerItems.add(new NavDrawerItem(leftSliderData[4], navMenuIcons[4], SETTINGS));
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mToolbar.setBackgroundColor(getResources().getColor(R.color.action_bar_bg));
        btnBack = (ImageButton) mToolbar.findViewById(R.id.bt_toolbar_cross);
        btnBack.setVisibility(View.GONE);
        toolbar_title = (CustomFontTextView) mToolbar.findViewById(R.id.toolbar_title);
        bt_toolbar_right = (IconTextView) mToolbar.findViewById(R.id.bt_toolbar_right);
        bt_toolbar_right.setText(getResources().getString(R.string.fa_viewall_orders));
        bt_toolbar_right.setTextSize(44);
        bt_toolbar_right.setVisibility(View.GONE);
        bt_toolbar_icon = (IconTextView) mToolbar.findViewById(R.id.bt_toolbar_icon);
        bt_toolbar_icon.setVisibility(View.GONE);

        setSupportActionBar(mToolbar);
    }

    public void onItemClickPatient(AdapterView<?> parent, View view, int position, long id, DrawerLayout drawerLayout, Fragment fragment, int currentFragment, Context context) {
        Utils.hideKeyboard(this);

        if (currentFragment != -1 && leftSliderData[currentFragment].equals(navDrawerItems.get(position).getTitle())) {
            drawerLayout.closeDrawers();
            return;
        }
        if (!(this instanceof HomeActivity)) {
            Utils.UpdateSelectItemFromNavigationDrawer(true, position);
            this.finish();
            return;
        }
        switch (position) {
            case HOME:
                fragment = new ListingFragment();
                setNavigationBackIcon();
                toolBarTitle = "Lisiting";
                break;
            case MYORDER:
                break;
            case SETTINGS:
                break;
        }
        navigationDrawerAdapter.setSelectedPosition(position);
        navigationDrawerAdapter.notifyDataSetChanged();
        openFragment(fragment);

    }

    public void setToolbarTitle(String title) {
        initToolbar();

        toolbar_title.setText(title);
        toolbar_title.setVisibility(View.VISIBLE);
        bt_toolbar_right.setOnClickListener(null);
        bt_toolbar_right.setVisibility(View.GONE);
        bt_toolbar_icon.setOnClickListener(null);
        bt_toolbar_icon.setVisibility(View.GONE);

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAppIcon();
            }
        });

    }

    private void onClickAppIcon() {
        Utils.hideKeyboard(NavigationDrawerActivity.this);
        int drawerLockMode = drawerLayout.getDrawerLockMode(GravityCompat.START);
        if (drawerLayout.isDrawerVisible(GravityCompat.START)
                && (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_OPEN)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_CLOSED) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    private void openFragment(Fragment fragment) {
        Utils.UpdateSelectItemFromNavigationDrawer(false, -1);
        initToolbar();
        setToolbarTitle(toolBarTitle);
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_container, fragment);
        fragmentTransaction.addToBackStack(fragment.getClass().getName());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commitAllowingStateLoss();
        drawerLayout.closeDrawers();
    }

    public void setHamburgerIcon() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.hamburger);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    private void setNavigationBackIcon() {
        initToolbar();
        mToolbar.setNavigationIcon(R.drawable.hamburger);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    public void setToolbarBack() {
        initToolbar();
        mToolbar.setNavigationIcon(R.drawable.ic_back_w_shadow);
        //drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }


    private void initDrawer() {
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.drawer_opened, R.string.drawer_closed) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                Utils.hideKeyboard(NavigationDrawerActivity.this);
                navigationDrawerAdapter.notifyDataSetChanged();
                super.onDrawerOpened(drawerView);

            }

        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    /*
this method will display side drawer open when app is installed for the first time
otherwise close the drawer if it is open
*/
    public void showDrawer(final DrawerLayout drawerLayout) {
        if (!SharedPref.isSideDrawerShown()) {
            drawerLayout.openDrawer(Gravity.START);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    drawerLayout.closeDrawers();
                    SharedPref.setSideDrawerShown(true);
                }
            }, 3 * 1000);

        } else if (drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawers();
            return;
        }

    }
}