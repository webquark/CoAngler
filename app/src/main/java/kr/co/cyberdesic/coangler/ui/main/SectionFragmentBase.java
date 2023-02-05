package kr.co.cyberdesic.coangler.ui.main;

import androidx.fragment.app.Fragment;

import kr.co.cyberdesic.coangler.MainActivity;
import kr.co.cyberdesic.coangler.ui.fragment.FragmentBase;

public class SectionFragmentBase extends FragmentBase {

    /**
     * 맵 섹션 페이지
     * @return
     */
    public MapFragment getMapPage() {
        MainActivity mainActivity = (MainActivity)getActivity();
        return (MapFragment)mainActivity.getPagerAdapter().getItem(SectionsPagerAdapter.SECTION_MAP);
    }

    public MapFragment focusMapPage() {
        MainActivity mainActivity = (MainActivity)getActivity();

        mainActivity.getViewPager().setCurrentItem(SectionsPagerAdapter.SECTION_MAP);

        return (MapFragment)mainActivity.getPagerAdapter().getItem(SectionsPagerAdapter.SECTION_MAP);
    }

}
