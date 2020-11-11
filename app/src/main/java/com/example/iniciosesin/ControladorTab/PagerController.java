package com.example.iniciosesin.ControladorTab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerController extends FragmentPagerAdapter {
    int numberOfTabs;
    public PagerController(@NonNull FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }



    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return  new CulturaSorda();
            case 1:
                return new LSC();
            case 2:
                return new UserInfo();
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
