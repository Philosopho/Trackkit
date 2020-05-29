package com.krinotech.data;

import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class SubredditTest {
    private final String EXPECTED_SUBSCRIBERS = " subscribers";

    @Test
    public void constructor_shouldHaveAppropriateGetters() {
        String id = 0;
        String title = "title of the main page";
        String url = "The relative URL of the subreddit. Ex: '/r/pics'";
        String subredditType = "e.g. public, private, restricted, gold-restricted";
        String displayName = "human name of the subreddit";
        long subscribers = 1500;
        boolean userIsSubscriber = false;
        boolean isTracking = false;
        String headerImage = "full URL to the header image, or null";
        String description = "sidebar text";

        Subreddit subreddit = new Subreddit(id,
                title, url, subredditType,
                displayName, subscribers, userIsSubscriber,
                headerImage, isTracking, description
        );

        assertEquals(id, subreddit.getId());
        assertEquals(title, subreddit.getTitle());
        assertEquals(url, subreddit.getUrl());
        assertEquals(subredditType, subreddit.getSubredditType());
        assertEquals(displayName, subreddit.getDisplayName());
        assertEquals("1,500" + EXPECTED_SUBSCRIBERS, subreddit.getSubscribersFormatted());
        assertEquals(userIsSubscriber, subreddit.getUserIsSubscriber());
        assertEquals(headerImage, subreddit.getHeaderImage());
        assertEquals(description, subreddit.getDescription());
        assertEquals(isTracking, subreddit.isTracking());
    }

    @Test
    public void constructor_emptyProperties_shouldHaveAppropriateGetters() {
        String id = "1";
        String title = "";
        String url = "";
        String subredditType = "";
        String displayName = "";
        long subscribers = 0;
        boolean userIsSubscriber = true;
        String headerImage = "";
        String description = "";
        boolean isTracking = true;

        Subreddit subreddit = new Subreddit(
                id, title, url, subredditType,
                displayName, subscribers, userIsSubscriber,
                headerImage, isTracking, description
        );

        assertEquals(id, subreddit.getId());
        assertEquals(title, subreddit.getTitle());
        assertEquals(url, subreddit.getUrl());
        assertEquals(subredditType, subreddit.getSubredditType());
        assertEquals(displayName, subreddit.getDisplayName());
        assertEquals(subscribers + EXPECTED_SUBSCRIBERS, subreddit.getSubscribersFormatted());
        assertEquals(userIsSubscriber, subreddit.getUserIsSubscriber());
        assertEquals(headerImage, subreddit.getHeaderImage());
        assertEquals(description, subreddit.getDescription());
        assertEquals(isTracking, subreddit.isTracking());
    }

    @Test
    public void setters_gettersShouldReturnSetterValues() {
        String id = 1;
        String title = "title of the main page";
        String url = "The relative URL of the subreddit. Ex: '/r/pics'";
        String subredditType = "e.g. public, private, restricted, gold-restricted";
        String displayName = "human name of the subreddit";
        long subscribers = 1500;
        boolean userIsSubscriber = false;
        String headerImage = "full URL to the header image, or null";
        String description = "sidebar text";

        Subreddit subreddit = new Subreddit();

        subreddit.setId(id);
        subreddit.setTitle(title);
        subreddit.setUrl(url);
        subreddit.setSubredditType(subredditType);
        subreddit.setDisplayName(displayName);
        subreddit.setSubscribers(subscribers);
        subreddit.setUserIsSubscriber(userIsSubscriber);
        subreddit.setHeaderImage(headerImage);
        subreddit.setDescription(description);

        assertEquals(id, subreddit.getId());
        assertEquals(title, subreddit.getTitle());
        assertEquals(url, subreddit.getUrl());
        assertEquals(subredditType, subreddit.getSubredditType());
        assertEquals(displayName, subreddit.getDisplayName());
        assertEquals("1,500" + EXPECTED_SUBSCRIBERS, subreddit.getSubscribersFormatted());
        assertEquals(userIsSubscriber, subreddit.getUserIsSubscriber());
        assertEquals(headerImage, subreddit.getHeaderImage());
        assertEquals(description, subreddit.getDescription());
    }

    @Test
    public void setters_empty_gettersShouldReturnEmptySetterValues() {
        long id = 0;
        String title = "";
        String url = "";
        String subredditType = "";
        String displayName = "";
        long subscribers = 0;
        boolean userIsSubscriber = true;
        String headerImage = "";
        String description = "";

        Subreddit subreddit = new Subreddit();

        subreddit.setTitle(title);
        subreddit.setUrl(url);
        subreddit.setSubredditType(subredditType);
        subreddit.setDisplayName(displayName);
        subreddit.setSubscribers(subscribers);
        subreddit.setUserIsSubscriber(userIsSubscriber);
        subreddit.setHeaderImage(headerImage);
        subreddit.setDescription(description);

        assertEquals(id, subreddit.getId());
        assertEquals(title, subreddit.getTitle());
        assertEquals(url, subreddit.getUrl());
        assertEquals(subredditType, subreddit.getSubredditType());
        assertEquals(displayName, subreddit.getDisplayName());
        assertEquals(subscribers + EXPECTED_SUBSCRIBERS, subreddit.getSubscribersFormatted());
        assertEquals(userIsSubscriber, subreddit.getUserIsSubscriber());
        assertEquals(headerImage, subreddit.getHeaderImage());
        assertEquals(description, subreddit.getDescription());
    }

}