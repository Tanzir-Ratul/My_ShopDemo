package com.example.shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.shop.Adapters.ViewPagerAdapter;
import com.example.shop.fragments.CartFragment;
import com.example.shop.fragments.HomeFragment;
import com.example.shop.fragments.ProfileFragment;
import com.example.shop.fragments.WishlistFragment;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    MenuItem prevMenuItem;
    ProfileFragment profileFragment;
    WishlistFragment wishlistFragment;
    HomeFragment homeFragment;
    CartFragment cartFragment;

    private Toolbar toolbar;
    //ViewPager and BottomNavigationMenuView
    private BottomNavigationView mBottomNavigation;
    private ViewPager viewPager;
    private ViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbarID);
        setSupportActionBar(toolbar);


        drawerLayout = findViewById(R.id.drawableId);
        navigationView = findViewById(R.id.navigationID);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;
                switch (item.getItemId()) {

                    case R.id.nav_homeId:
                        fragment = homeFragment;
                        break;
                    case R.id.nav_profileId:
                        fragment = profileFragment;
                        break;
                    case R.id.nav_wishlistId:
                        fragment = wishlistFragment;
                        break;

                }


                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                if (fragment != null) {

                    fragmentTransaction.replace(R.id.containerID, fragment).commit();
                }
                return true;
            }
        });

//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.containerID, homeFragment);
//        transaction.commit();

         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            Toast.makeText(this, "Exception " + e, Toast.LENGTH_SHORT).show();
        }

        // ViewPager and BottomNavigationMenuView

        mBottomNavigation = findViewById(R.id.buttom_navigation);
        mBottomNavigation.setOnNavigationItemSelectedListener(this);

        viewPager = findViewById(R.id.view_pager);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    mBottomNavigation.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: " + position);
                mBottomNavigation.getMenu().getItem(position).setChecked(true);
                prevMenuItem = mBottomNavigation.getMenu().getItem(position);


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setupViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        homeFragment = new HomeFragment();
        wishlistFragment = new WishlistFragment();
        cartFragment = new CartFragment();
        profileFragment = new ProfileFragment();
        mViewPagerAdapter.addFragment(homeFragment);
        mViewPagerAdapter.addFragment(wishlistFragment);
        mViewPagerAdapter.addFragment(cartFragment);
        mViewPagerAdapter.addFragment(profileFragment);
        viewPager.setAdapter(mViewPagerAdapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        switch (item.getItemId()) {
            case R.id.action_home:
                viewPager.setCurrentItem(0);
                return true;
            case R.id.action_favorites:
                viewPager.setCurrentItem(1);
                return true;
            case R.id.action_cart:
                viewPager.setCurrentItem(2);
                return true;
            case R.id.action_account:
                viewPager.setCurrentItem(3);
                return true;
            default:
                return false;
        }
    }
}