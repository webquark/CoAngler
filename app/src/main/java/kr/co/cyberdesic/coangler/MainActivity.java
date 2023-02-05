package kr.co.cyberdesic.coangler;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import kr.co.cyberdesic.coangler.ui.activity.ActivityBase;
import kr.co.cyberdesic.coangler.ui.main.SectionsPagerAdapter;
import kr.co.cyberdesic.coangler.databinding.ActivityMainBinding;

public class MainActivity extends ActivityBase {

    private ActivityMainBinding binding;

    private SectionsPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        mViewPager = binding.viewPager;
        mViewPager.setAdapter(mPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(mViewPager);
    }

    public SectionsPagerAdapter getPagerAdapter() {
        return mPagerAdapter;
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }
}