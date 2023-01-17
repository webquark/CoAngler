package kr.co.cyberdesic.coangler;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;

import kr.co.cyberdesic.coangler.ui.activity.ActivityBase;
import kr.co.cyberdesic.coangler.ui.main.SectionsPagerAdapter;
import kr.co.cyberdesic.coangler.databinding.ActivityMainBinding;

public class MainActivity extends ActivityBase {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

    }
}