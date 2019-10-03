package com.nalovma.bakingapp.page.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.nalovma.bakingapp.R;
import com.nalovma.bakingapp.model.Step;
import com.nalovma.bakingapp.page.common.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailViewFragment extends BaseFragment {

    public static final String STEP = "step";

    @BindView(R.id.player_view)
    PlayerView mPlayerView;

    @BindView(R.id.stepInstructions)
    TextView mStepInstructions;

    SimpleExoPlayer player;

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            mStepInstructions.setVisibility(View.GONE);
        }
        else {
            mStepInstructions.setVisibility(View.VISIBLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_detail_view, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            Step step = (Step) bundle.getParcelable(STEP);
            if (step != null) {
                initStepData(step);
            }
        }
    }

    private void initStepData(Step step) {
        Context context = requireContext();

        if (!step.getDescription().isEmpty()){
            mStepInstructions.setText(step.getDescription());
        }
        if (!step.getVideoURL().isEmpty()) {
            player = ExoPlayerFactory.newSimpleInstance(context);
            mPlayerView.setPlayer(player);

            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                    Util.getUserAgent(context, getString(R.string.app_name)));

            MediaSource videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(step.getVideoURL()));

            player.setPlayWhenReady(true);
            player.prepare(videoSource);
        } else {
            mPlayerView.setVisibility(View.GONE);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (player != null) {
            player.stop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
        }

    }
}
