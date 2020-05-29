package com.krinotech.trackkit.dagger;

import android.app.Application;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.krinotech.data.contract.RedditApi;
import com.krinotech.data.contract.RedditAuthService;
import com.krinotech.data.contract.TrackkitDao;
import com.krinotech.domain.DateHelper;
import com.krinotech.trackkit.BaseApplication;
import com.krinotech.trackkit.dagger.module.ActivityModule;
import com.krinotech.trackkit.module.RepositoryModule;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Component(modules = {
        AndroidInjectionModule.class,
        ActivityModule.class,
        RepositoryModule.class
})
@Singleton
public interface ApplicationComponent extends AndroidInjector<BaseApplication> {
    TrackkitDao trackkitDao();
    RedditApi redditApi();

    void inject(BaseApplication application);

    @Component.Factory
    interface Factory {

        ApplicationComponent create(@BindsInstance Application context,
                                    @BindsInstance Executor executor,
                                    @BindsInstance RedditApi redditApi,
                                    @BindsInstance TrackkitDao trackkitDao,
                                    @BindsInstance DateHelper dateHelper,
                                    @BindsInstance RedditAuthService redditAuthService,
                                    @BindsInstance FirebaseAnalytics firebaseAnalytics);
    }
}
