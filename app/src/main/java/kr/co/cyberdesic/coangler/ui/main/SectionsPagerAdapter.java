package kr.co.cyberdesic.coangler.ui.main;

import android.content.Context;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import kr.co.cyberdesic.coangler.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;

    public static int SECTION_RECOMMEND = 0;
    public static int SECTION_MAP = 1;
    public static int SECTION_ADMIN = 2;

    private MapFragment mMapFragment;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return RecommendFragment.newInstance();

        } else if (position == 1) {
            if (mMapFragment == null) {
                mMapFragment = MapFragment.newInstance();
            }

            return mMapFragment;

        } else if (position == 2) {
            return AdminFragment.newInstance();
        }

        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getStringArray(R.array.tab_text)[position];
    }

    @Override
    public int getCount() {
        // Total pages count.
        return 3;
    }
}