package com.skymanlab.weighttraining.management.project.fragment.Training.list.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.skymanlab.weighttraining.management.project.fragment.Training.list.ArmListFragment;
import com.skymanlab.weighttraining.management.project.fragment.Training.list.ChestListFragment;
import com.skymanlab.weighttraining.management.project.fragment.Training.list.EtcListFragment;
import com.skymanlab.weighttraining.management.project.fragment.Training.list.LatListFragment;
import com.skymanlab.weighttraining.management.project.fragment.Training.list.ShoulderListFragment;
import com.skymanlab.weighttraining.management.project.fragment.Training.list.UpperBodyListFragment;

public class EventListPagerAdapter extends FragmentStateAdapter {

    // constant
    public static final int FRAGMENT_COUNT = 6;

    // constructor
    public EventListPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        ChestListFragment chestList = ChestListFragment.newInstance();
        ShoulderListFragment shoulderList = ShoulderListFragment.newInstance();
        LatListFragment latList = LatListFragment.newInstance();
        UpperBodyListFragment upperBodyList = UpperBodyListFragment.newInstance();
        ArmListFragment armList = ArmListFragment.newInstance();
        EtcListFragment etcList = EtcListFragment.newInstance();

        // [check 1] : 몇 번째 화면인가요?
        switch (position) {
            case 0:
                return chestList;
            case 1:
                return  shoulderList;
            case 2:
                return latList;
            case 3:
                return upperBodyList;
            case 4:
                return armList;
            case 5:
                return etcList;
            default:
                return null;
        }

    }

    @Override
    public int getItemCount() {
        return FRAGMENT_COUNT;
    }

    public static class ZoomOutPageTransformer implements ViewPager2.PageTransformer {

        // constant
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        @Override
        public void transformPage(@NonNull View page, float position) {

            // page 의 width 와 height 가져오기
            int pageWidth = page.getWidth();
            int pageHeight = page.getHeight();

            // [check 1] : position 값 으로 화면 전환 설정하기
            if (position < -1) {
                // [ -Infinity, -1)
                // this page is way off-screen to the left.
                page.setAlpha(0f);

            } else if(position <=1) {
                // [-1, 1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1-Math.abs(position));
                float vertMargin = pageHeight * (1-scaleFactor) / 2;
                float horzMargin = pageWidth * (1-scaleFactor) / 2;

                // [check 2] :
                if (position < 0) {
                    page.setTranslationX(horzMargin - vertMargin /2);
                } else {
                    page.setTranslationX(-horzMargin + vertMargin /2) ;
                } // [check 2]

                // Scale the page down (between MIN_SCALE and 1)
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                page.setAlpha(MIN_ALPHA + (scaleFactor-MIN_SCALE) / (1-MIN_SCALE) * (1-MIN_ALPHA));

            } else {
                // (1, +Infinity]
                // This page is way off-screen to the right.
            } // [check 1]
        }
    }
}
