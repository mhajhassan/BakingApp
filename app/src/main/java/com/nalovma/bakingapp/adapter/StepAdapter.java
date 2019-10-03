package com.nalovma.bakingapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.nalovma.bakingapp.R;
import com.nalovma.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepHolder> {

    private List<Step> mItems = new ArrayList<>();
    private StepOnItemClickListener stepOnItemClickListener;

    public StepAdapter() {
    }

    @NonNull
    @Override
    public StepHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new StepHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.step_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StepHolder stepHolder, int i) {
        Step step = mItems.get(i);
        stepHolder.setStep(step, i);
    }

    @Override
    public int getItemCount() {
        if (mItems == null) {
            return 0;
        }
        return mItems.size();

    }

    public Step getStepByPosition(int pos) {
        if (!mItems.isEmpty()) {
            return mItems.get(pos);
        }
        return null;
    }

    public void setStep(List<Step> steps, StepOnItemClickListener stepOnItemClickListener) {
        this.mItems.clear();
        this.mItems.addAll(steps);
        notifyDataSetChanged();
        this.stepOnItemClickListener = stepOnItemClickListener;
    }

    public interface StepOnItemClickListener {

        void onStepItemClick(View view, int position);
    }

    public class StepHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.stepThumbnail)
        ImageView mStepThumbnail;

        @BindView(R.id.stepNumber)
        TextView mStepNumber;

        @BindView(R.id.shortDescription)
        TextView mShortDescription;


        public StepHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void setStep(Step step, int position) {
            mShortDescription.setText(step.getShortDescription());
            mStepNumber.setText(String.format(Locale.ENGLISH, "%02d", position));
            RequestOptions options = new RequestOptions()
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);

            Glide.with(mStepThumbnail)
                    .load(step.getThumbnailURL())
                    .apply(options)
                    .into(mStepThumbnail);
        }

        @Override
        public void onClick(View v) {
            if (stepOnItemClickListener != null) {
                stepOnItemClickListener.onStepItemClick(v, getAdapterPosition());
            }
        }
    }
}

