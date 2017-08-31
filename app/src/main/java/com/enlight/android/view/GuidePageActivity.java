package com.enlight.android.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.enlight.com.R;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.enlight.android.common.view.BaseActivity;
import com.enlight.android.view.fragment.GuidePageFragment;
import com.enlight.android.view.pageindicator.CirclePageIndicator;
import com.enlight.android.view.pageindicator.PageIndicator;

/**
 * Created by zyc on 2017/8/18.
 * 程序的引导页面
 */

public class GuidePageActivity extends BaseActivity {
    /**
     * 向导页是否到最后一页标志
     */
    boolean flag = false;
    ViewPager mPager;
    PageIndicator mIndicator;

    @Override
    protected int getLayoutId() {
        return R.layout.guide_page_layout;
    }

    protected void setupView() {
        FragmentPagerAdapter adapter = new CbcHotGuideAdapter(getFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(adapter);

        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrolled(int position,
                                       float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        flag = false;
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        flag = true;
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        if (mPager.getCurrentItem() == mPager.getAdapter()
                                .getCount() - 1 && !flag) {
                            // 如果到最后一页,跳转到主页面
                            Intent intent = new Intent(GuidePageActivity.this, MainIndexActivity.class);
                            startActivity(intent);
                        }
                        flag = true;
                        break;
                }
            }
        });
    }

    @Override
    protected void initialized() {
        setupView();
    }

    class CbcHotGuideAdapter extends FragmentPagerAdapter {
        private int imageResID[] = {R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};

        public CbcHotGuideAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return new GuidePageFragment(imageResID[position]);
        }

        @Override
        public int getCount() {
            return imageResID.length;
        }
    }

}
