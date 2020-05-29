package com.krinotech.trackkit.viewmodel;

import androidx.lifecycle.LiveData;

import com.krinotech.data.TrackkitRepository;
import com.krinotech.data.Subreddit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SubredditsViewModelTest {
    @Mock
    TrackkitRepository repository;

    @Mock
    LiveData<List<Subreddit>> subredditsMock;

    private SubredditsViewModel testSubject;

    @Before
    public void setUp() {
        when(repository.getSubreddits()).thenReturn(subredditsMock);

        testSubject = new SubredditsViewModel(repository);
    }

    @Test
    public void getNewSubreddits_shouldReturnExpectedNewSubreddits() {
        assertEquals(subredditsMock, testSubject.getSubreddits());
    }
}