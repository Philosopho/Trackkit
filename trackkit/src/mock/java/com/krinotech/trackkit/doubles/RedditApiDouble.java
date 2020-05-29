package com.krinotech.trackkit.doubles;

import com.krinotech.data.Subreddit;
import com.krinotech.data.contract.RedditApi;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.mock.Calls;

public class RedditApiDouble implements RedditApi {
    public static final String NEW_TYPE = "new";
    private final String LOREM_PICSUM_1 = "https://picsum.photos/id/1/1000";
    private String LOREM_PICSUM_2 = "https://picsum.photos/id/2/1000";
    private String LOREM_PICSUM_3 = "https://picsum.photos/id/3/1000";

    @Override
    public Call<List<Subreddit>> getNewSubreddits() {
        List<Subreddit> subreddits = getSubreddits();

        return Calls.response(subreddits);
    }

    private List<Subreddit> getSubreddits() {
        Lorem lorem = LoremIpsum.getInstance();
        List<Subreddit> subreddits = new ArrayList<>();
        Random random = new Random();
        int[] limit = {3, 5, 7, 10};
        int maxSubreddits = limit[random.nextInt(limit.length)];
        boolean[] isUserSubscriber = {true, false};
        String[] images = {null, null, null, null, null,
                LOREM_PICSUM_1,
                LOREM_PICSUM_2,
                LOREM_PICSUM_3};

        for(int index = 0; index < maxSubreddits; index++) {
            String title = lorem.getTitle(1, 4);
            String url = "r/" + title;
            String description = lorem.getParagraphs(1, 4);
            String displayName = lorem.getName();
            long subscribers = random.nextInt(2_000_000_000);
            boolean userIsSubscriber = isUserSubscriber[random.nextInt(2)];
            String headerImage = images[random.nextInt(images.length)];
            System.out.println(headerImage);
            Subreddit subreddit = new Subreddit(
                    String.valueOf(index), title, url, NEW_TYPE,
                    displayName, subscribers,
                    userIsSubscriber, headerImage,
                    false, description);

            subreddits.add(subreddit);
        }
        return subreddits;
    }
}
