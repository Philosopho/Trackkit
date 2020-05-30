package com.krinotech.trackkit.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.tabs.TabLayoutMediator;
import com.krinotech.data.AuthResponse;
import com.krinotech.data.Subreddit;
import com.krinotech.data.contract.ApiContract;
import com.krinotech.data.contract.RedditAuthService;
import com.krinotech.data.contract.TrackkitPreferences;
import com.krinotech.trackkit.R;
import com.krinotech.trackkit.databinding.ActivityMainBinding;
import com.krinotech.trackkit.view.fragment.TrackkitFragmentStateAdapter;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import retrofit2.Call;
import retrofit2.Callback;
import timber.log.Timber;

import static com.krinotech.data.contract.ApiContract.CLIENT_ID;
import static com.krinotech.data.contract.ApiContract.DEVICE_ID;
import static com.krinotech.data.contract.ApiContract.DEVICE_ID_PARAM;
import static com.krinotech.data.contract.ApiContract.GRANT_TYPE;
import static com.krinotech.data.contract.ApiContract.GRANT_TYPE_PARAM;
import static com.krinotech.trackkit.NetworkConnectionHelper.isConnected;

public class MainActivity extends AppCompatActivity {
    private List<Subreddit> subreddits;
    private ActivityMainBinding binding;

    @Inject
    TrackkitPreferences trackkitPreferences;

    @Inject
    RedditAuthService redditAuthService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        if(!isConnected(this)) {
            binding.errorText.setText(getString(R.string.network_error));
            showError();
        }
        else {
            showProgressBar();

            if(trackkitPreferences.tokenNeedsRefresh()) {
                refreshToken();
            }
            else {
                hideProgressBar();
            }

            TrackkitFragmentStateAdapter trackkitFragmentStateAdapter =
                    new TrackkitFragmentStateAdapter(this);

            binding.pager.setAdapter(trackkitFragmentStateAdapter);

            String[] tabNames = {
                    getString(R.string.subreddits_fragment_name),
                    getString(R.string.tracking_fragment_name)
            };

            TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(binding.tabLayout, binding.pager,
                    (tab, position) -> tab.setText(tabNames[position])
            );

            Intent widgetIntent = getIntent();

            int tabStart = 0;
            if(widgetIntent != null) {
                tabStart = widgetIntent.getIntExtra(getString(R.string.WIDGET_TAB_TRACKING_EXTRA), 0);
            }
            binding.pager.setCurrentItem(tabStart);

            tabLayoutMediator.attach();
        }
    }

    private void showError() {
        binding.errorText.setVisibility(View.VISIBLE);
        binding.pbMain.setVisibility(View.GONE);
    }

    private void showProgressBar() {
        binding.pbMain.setVisibility(View.VISIBLE);
        binding.pager.setVisibility(View.GONE);
        binding.tabLayout.setVisibility(View.GONE);
        binding.errorText.setVisibility(View.GONE);
    }

    private void hideProgressBar() {
        binding.pbMain.setVisibility(View.GONE);
        binding.pager.setVisibility(View.VISIBLE);
        binding.tabLayout.setVisibility(View.VISIBLE);
    }

    private void refreshToken() {
        String body = GRANT_TYPE_PARAM + "=" + GRANT_TYPE + "&"
                + DEVICE_ID_PARAM + "=" + DEVICE_ID;

        String credentials = CLIENT_ID + ":";
        String base64 = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        String credentialsEncoded = "Basic " + base64;
        String contentType = ApiContract.CONTENT_TYPE;

        redditAuthService.redditAuthenthicate(credentialsEncoded, contentType, body).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, retrofit2.Response<AuthResponse> stringResponse) {
                if(stringResponse.isSuccessful()) {
                    if(stringResponse.body() != null) {
                        String accessToken = "Bearer " + stringResponse.body().getAccessToken();
                        trackkitPreferences.saveAccessTokenAndExpiration(accessToken, stringResponse.body().getExpiresIn());
                    }
                    else {
                        if(stringResponse.errorBody() != null) {
                            Timber.d(stringResponse.errorBody().toString());
                        }
                    }
                }
                else {
                    if(stringResponse.errorBody() != null) {
                        Timber.d(stringResponse.errorBody().toString());
                    }
                }
                hideProgressBar();
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Timber.d(t.getCause());
                hideProgressBar();
            }
        });
    }

    public List<Subreddit> getSubreddits() {
        return subreddits;
    }

    public void setSubreddits(List<Subreddit> subreddits) {
        this.subreddits = subreddits;
    }
}
