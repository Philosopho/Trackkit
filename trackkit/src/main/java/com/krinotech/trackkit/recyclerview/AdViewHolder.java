package com.krinotech.trackkit.recyclerview;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.krinotech.trackkit.databinding.AdLayoutBinding;

public class AdViewHolder extends RecyclerView.ViewHolder {

    private final AdLayoutBinding adLayoutBinding;

    public AdViewHolder(@NonNull AdLayoutBinding adLayoutBinding) {
        super(adLayoutBinding.getRoot());

        this.adLayoutBinding = adLayoutBinding;

        Context context = adLayoutBinding.adView.getContext();

        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        adLayoutBinding.adView.loadAd(adRequest);
    }
}
