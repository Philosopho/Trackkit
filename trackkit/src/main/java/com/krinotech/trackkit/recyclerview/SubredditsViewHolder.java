package com.krinotech.trackkit.recyclerview;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.krinotech.data.Subreddit;
import com.krinotech.data.contract.ApiContract;
import com.krinotech.trackkit.R;
import com.krinotech.trackkit.databinding.SubredditListItemBinding;

import org.jetbrains.annotations.NotNull;

public class SubredditsViewHolder extends RecyclerView.ViewHolder {
    private final SubredditListItemBinding subredditListItemBinding;
    private String expandText;
    private String collapseText;
    private int maxLinesExpand;
    private int maxLinesCollapse;

    public SubredditsViewHolder(@NonNull SubredditListItemBinding subredditListItemBinding) {
        super(subredditListItemBinding.getRoot());

        this.subredditListItemBinding = subredditListItemBinding;
        Resources resources = subredditListItemBinding.subredditCard.getResources();
        expandText = resources.getString(R.string.expand_subreddit_desc);
        collapseText = resources.getString(R.string.collapse_subreddit_desc);
        maxLinesExpand = resources.getInteger(R.integer.initial_description_max_lines);
        maxLinesCollapse = resources.getInteger(R.integer.expanded_description_max_lines);
    }

    public void bind(Subreddit subreddit, SubredditsAdapter.ViewOnClicks viewOnClicks) {
        final CheckBox subredditCheckbox = subredditListItemBinding.subredditCheckbox;
        final MaterialButton expand = subredditListItemBinding.subredditDescriptionExpand;
        checkDescriptionMaxLines(subreddit, expand);
        expand.setOnClickListener(expandClickListener(expand, subreddit));
        subredditCheckbox.setOnCheckedChangeListener(null);
        subredditCheckbox.setChecked(subreddit.isTracking());
        subredditCheckbox.setOnCheckedChangeListener(
                checkBoxClickListener(subreddit, viewOnClicks)
        );
        subredditListItemBinding.subredditUrl.setOnClickListener(urlClick(subreddit));
        subredditListItemBinding.subredditViewPosts.setVisibility(viewOnClicks.viewPostsVisibility());
        subredditListItemBinding.subredditViewPosts.setOnClickListener(viewOnClicks.viewPosts(subreddit));
        subredditListItemBinding.setSubreddit(subreddit);
        subredditListItemBinding.executePendingBindings();
    }

    private void checkDescriptionMaxLines(Subreddit subreddit, MaterialButton expand) {
        if (subreddit.isDescriptionLimited()) {
            expand.setText(expandText);
            subredditListItemBinding.subredditDescription.setMaxLines(maxLinesExpand);
        }
        else {
            expand.setText(collapseText);
            subredditListItemBinding.subredditDescription.setMaxLines(maxLinesCollapse);
        }
    }

    public View.OnClickListener urlClick(Subreddit subreddit) {
        return v -> {
            String url = ApiContract.BASE_REDDIT_URL + subreddit.getUrl().substring(1);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            subredditListItemBinding
                    .subredditCard
                    .getContext()
                    .startActivity(intent);
        };
    }

    @NotNull
    private CompoundButton.OnCheckedChangeListener checkBoxClickListener(Subreddit subreddit,
                                                                         SubredditsAdapter.ViewOnClicks viewOnClicks) {
        return (buttonView, isChecked) -> {
            viewOnClicks.checkboxClick(subreddit);
            subreddit.setIsTracking(isChecked);
        };
    }

    private View.OnClickListener expandClickListener(MaterialButton expandView, Subreddit subreddit) {
        return v -> {
            TextView description = subredditListItemBinding.subredditDescription;
            int animationDuration = 300;

            if(subreddit.isDescriptionLimited()) {
                expandView.setText(collapseText);
                ObjectAnimator animation = ObjectAnimator.ofInt(description, "maxLines", maxLinesCollapse);
                animation.setDuration(animationDuration).start();
                subreddit.setDescriptionLimited(false);
            }
            else {
                expandView.setText(expandText);
                ObjectAnimator animation = ObjectAnimator.ofInt(description, "maxLines", maxLinesExpand);
                animation.setDuration(animationDuration).start();
                subreddit.setDescriptionLimited(true);
            }
        };
    }
}
