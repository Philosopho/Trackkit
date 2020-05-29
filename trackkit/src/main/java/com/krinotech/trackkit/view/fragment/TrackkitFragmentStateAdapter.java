package com.krinotech.trackkit.view.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TrackkitFragmentStateAdapter extends FragmentStateAdapter {

    public TrackkitFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new SubredditsFragment();
        }
        return new TrackingFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
