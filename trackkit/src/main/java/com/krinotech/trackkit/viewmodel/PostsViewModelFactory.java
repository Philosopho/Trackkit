package com.krinotech.trackkit.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.krinotech.data.contract.Repository;

import javax.inject.Inject;

public class PostsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Repository repository;

    @Inject
    public PostsViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PostsViewModel(repository);
    }
}
