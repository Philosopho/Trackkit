package com.krinotech.trackkit.dagger.module;

import com.krinotech.data.contract.Repository;
import com.krinotech.trackkit.viewmodel.CommentsViewModelFactory;
import com.krinotech.trackkit.viewmodel.PostsViewModelFactory;
import com.krinotech.trackkit.viewmodel.SubredditViewModelFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewModelFactoryModule {

    @Provides
    static PostsViewModelFactory providesPostViewModelFactory(Repository repository) {
        return new PostsViewModelFactory(repository);
    }

    @Provides
    static SubredditViewModelFactory providesSubredditViewModelFactory(Repository repository) {
        return new SubredditViewModelFactory(repository);
    }

    @Provides
    static CommentsViewModelFactory providesCommentsViewModelFactory(Repository repository) {
        return new CommentsViewModelFactory(repository);
    }
}
