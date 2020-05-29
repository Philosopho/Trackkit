package com.krinotech.data;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.krinotech.data.contract.TrackkitPreferences;
import com.krinotech.domain.Filter;
import com.krinotech.domain.SubredditType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {
    private static final String DATA = "data";
    private static final String REPLIES = "replies";
    private static final String CHILDREN = "children";
    private static final String MEDIA = "media";
    private static final String SECURE_MEDIA = "secure_media";
    private static final String REDDIT_VIDEO = "reddit_video";
    private static final String SECURE_MEDIA_EMBED = "secure_media_embed";
    private static final String MEDIA_DOMAIN_URL = "media_domain_url";

    public static List<PostComment> convertChildrenCommentsJSON(JsonElement jsonElement, String subredditPostId) {
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        List<PostComment> comments = new ArrayList<>();
        Gson gson = new Gson();

        if (jsonArray != null) {
            JsonArray jsonArrayChildrenPostComments = getJsonArrayChildren(jsonArray, 1);
            List<ChildrenComments> children =
                    gson.fromJson(jsonArrayChildrenPostComments.toString(), new TypeToken<List<ChildrenComments>>() {
                    }.getType());

            for (int index2 = 0; index2 < jsonArrayChildrenPostComments.size(); index2++) {
                JsonObject childObject = jsonArrayChildrenPostComments.get(index2).getAsJsonObject();
                JsonObject dataObject = childObject.getAsJsonObject(DATA);
                if (dataObject.has(REPLIES)) {
                    JsonElement repliesObject = dataObject.get(REPLIES);
                    if (repliesObject != null && repliesObject.isJsonObject()) {
                        JsonArray replies = getJsonArrayProper(repliesObject.getAsJsonObject());
                        children.addAll(gson.fromJson(replies.toString(), new TypeToken<List<ChildrenComments>>() {
                        }.getType()));
                    }
                }
                for (ChildrenComments child : children) {
                    PostComment postComment1 = child.getComment();
                    postComment1.setPostTitleId(subredditPostId);
                    comments.add(postComment1);
                }
            }
        }
        return comments;
    }

    public static PostTitle convertJsonToPostTitle(JsonElement jsonElement, String subredditPostId) {
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        JsonArray jsonArrayChildren = getJsonArrayChildren(jsonArray, 0);
        Gson gson = new Gson();

        JsonObject postTitleJsonObject = jsonArrayChildren.get(0).getAsJsonObject();
        PostTitle postTitle = gson.fromJson(postTitleJsonObject.getAsJsonObject(DATA), PostTitle.class);
        postTitle.setSubredditPostId(subredditPostId);

        if(postTitle.isVideo()) {
            if(postTitleJsonObject.getAsJsonObject(DATA).has(SECURE_MEDIA)) {
                JsonObject jsonObject = postTitleJsonObject.getAsJsonObject(DATA)
                        .getAsJsonObject(SECURE_MEDIA)
                        .getAsJsonObject(REDDIT_VIDEO);

                PostVideo postVideo = gson.fromJson(jsonObject, PostVideo.class);

                postTitle.setPostVideo(postVideo);
            }
            else {
                JsonObject jsonObject = postTitleJsonObject
                        .getAsJsonObject(DATA)
                        .getAsJsonObject(MEDIA).getAsJsonObject(REDDIT_VIDEO);
                PostVideo postVideo = gson.fromJson(jsonObject, PostVideo.class);

                postTitle.setPostVideo(postVideo);
            }
        }
        else if(postTitleJsonObject.getAsJsonObject(DATA).has(SECURE_MEDIA_EMBED)) {
            JsonObject jsonObject = postTitleJsonObject
                    .getAsJsonObject(DATA)
                    .getAsJsonObject(SECURE_MEDIA_EMBED);
            if(jsonObject.size() != 0) {
                PostVideo postVideo = gson.fromJson(jsonObject, PostVideo.class);
                postVideo.setScrubberMediaUrl(jsonObject.getAsJsonPrimitive(MEDIA_DOMAIN_URL).getAsString());
                postTitle.setVideo(true);
                postTitle.setPostVideo(postVideo);
            }
        }
        return postTitle;
    }

    private static JsonArray getJsonArrayChildren(JsonArray jsonArray, int index) {
        JsonObject jsonObject = jsonArray.get(index).getAsJsonObject();
        return getJsonArrayProper(jsonObject);
    }

    public static List<Subreddit> convertSubredditsFromJSON(SubredditType subredditType,
                                                             JsonElement jsonElement,
                                                             TrackkitPreferences trackkitPreferences) throws IOException {
        JsonArray jsonArray = getJsonArray(jsonElement);
        List<Children> children =
                new Gson().fromJson(jsonArray.toString(), new TypeToken<List<Children>>(){}.getType());
        List<Subreddit> subreddits = new ArrayList<>();
        for(Children child: children) {
            Subreddit subreddit = child.getSubreddit();
            if(subreddit.isOver18()
                    || Filter.containsOver18Content(subreddit.getTitle())
                    || Filter.containsOver18Content(subreddit.getDescription())) {
                continue;
            }
            if(trackkitPreferences.isTracked(subreddit.getId())) {
                subreddit.setIsTracking(true);
            }
            subreddit.setType(subredditType.name());
            subreddits.add(subreddit);
        }
        return subreddits;
    }

    public static List<SubredditPost> convertSubredditPostsFromJson(String subredditId, JsonElement jsonElement) throws IOException {
        JsonArray jsonArray = getJsonArray(jsonElement);
        List<ChildrenPosts> children =
                new Gson().fromJson(jsonArray.toString(), new TypeToken<List<ChildrenPosts>>(){}.getType());
        List<SubredditPost> subredditPosts = new ArrayList<>();
        for(ChildrenPosts child: children) {
            SubredditPost subredditPost = child.getSubredditPost();
            subredditPost.setSubredditId(subredditId);
            subredditPosts.add(subredditPost);
        }
        return subredditPosts;
    }

    private static JsonArray getJsonArrayProper(JsonObject jsonObject) {
        return jsonObject
                .get(DATA)
                .getAsJsonObject()
                .get(CHILDREN)
                .getAsJsonArray();
    }

    private static JsonArray getJsonArray(JsonElement jsonElement) throws IOException {
        return jsonElement
                .getAsJsonObject()
                .get(DATA)
                .getAsJsonObject()
                .get(CHILDREN)
                .getAsJsonArray();
    }
}
