package ru.extremefitness.fitness_trainer.ui;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import ru.extremefitness.fitness_trainer.R;
import ru.extremefitness.fitness_trainer.loader.LoaderService;

/**
 * Created by user on 28.09.2015.
 */
public class MainViewModel extends BaseViewModel {

    private View rootView;
    private MyDashBoardModelView myDashBoardModelView;
    private SectionPagerAdapter adapter;
    private TabLayout tabLayout;

    protected MainViewModel(BaseActivity activity) {
        super(activity);
    }

    @Override
    protected void initViews() {

        myDashBoardModelView = new MyDashBoardModelView(activity);
        rootView = View.inflate(activity, R.layout.screen_tab, null);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        adapter = new SectionPagerAdapter(new View[]{myDashBoardModelView.getView(), new View(activity)});
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                final int position = tab.getPosition();
                if (position == 1) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tabLayout.getTabAt(0).select();
                            ExercisesActivity.start(activity);
                        }
                    });
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.select();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public void loaderOnReceive(Intent intent) {
        myDashBoardModelView.loaderOnReceive(intent);
    }

    public void loadUserData() {
        myDashBoardModelView.loadUserData();
    }

    public MyDashBoardModelView getMyDashboard() {
        return myDashBoardModelView;
    }

    public void setLoaderService(LoaderService loaderService) {
        this.myDashBoardModelView.setLoaderService(loaderService);
    }


    public class SectionPagerAdapter extends PagerAdapter {

        final View[] views;

        SectionPagerAdapter(final View[] views) {
            this.views = views;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views[position]);
            return views[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return true;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Личный кабинет";
                case 1:
                default:
                    return "Видео";
            }
        }
    }


    @Override
    public View getView() {
        return rootView;
    }

    public void saveChanges() {
        myDashBoardModelView.saveChanges();
    }
}
