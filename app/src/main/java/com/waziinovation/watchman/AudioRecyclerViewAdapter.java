package com.waziinovation.watchman;

import android.content.Intent;
/*http://www.xvideos.com/profiles/bon99dotcom#_tabVideos,videos-best*/
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.waziinovation.watchman.AudioFragment.OnListFragmentInteractionListener;
import com.waziinovation.watchman.audio.AudioContent;
import com.waziinovation.watchman.audio.AudioItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link AudioItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AudioRecyclerViewAdapter extends RecyclerView.Adapter<AudioRecyclerViewAdapter.ViewHolder> {
    private final ColorGenerator generator = ColorGenerator.MATERIAL;
    private final List<AudioItem> mValues;
    private final OnListFragmentInteractionListener mListener;


    public AudioRecyclerViewAdapter(List<AudioItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_audio, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        if(mValues.isEmpty()){

        }
        else {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).getTitle());
            holder.mContentView.setText(mValues.get(position).getContent());
            String letter =  String.valueOf(mValues.get(position).getTitle().charAt(0));
            TextDrawable drawable = TextDrawable.builder() .buildRound(letter, generator.getRandomColor());
            holder.mImageView.setImageDrawable(drawable);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onListFragmentInteraction(holder.mItem);
                    }

                    // holder.getAdapterPosition();
                    AudioItem item = mValues.get(holder.getAdapterPosition());
                    Intent intent = new Intent(v.getContext(), AudioActivity.class);
                    intent.putExtra("audioItem",new String[]{item.getTitle(), item.getContent(), item.getDetails(),item.getUrl()});
                    v.getContext().startActivity(intent);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final ImageView mImageView;
        public AudioItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mImageView = (ImageView)view.findViewById(R.id.watchman_audio_letter);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
