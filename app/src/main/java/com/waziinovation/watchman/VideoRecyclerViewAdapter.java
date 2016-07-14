package com.waziinovation.watchman;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.waziinovation.watchman.VideoFragment.OnListFragmentInteractionListener;
import com.waziinovation.watchman.video.VideoContent.VideoItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link VideoItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoRecyclerViewAdapter.ViewHolder> {

    private final List<VideoItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final Context mContext;

    public VideoRecyclerViewAdapter(List<VideoItem> items, OnListFragmentInteractionListener listener , Context context) {
        mValues = items;
        mListener = listener;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(mValues.isEmpty()){

        }else {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).getTitle());
            Picasso.with(holder.imageView.getContext()).load(mValues.get(position).getThumbnail()).into(holder.imageView);
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onListFragmentInteraction(holder.mItem);
                    }

                    VideoItem item = mValues.get(holder.getAdapterPosition());
                    Intent videoIntent = new Intent(v.getContext() , VideoActivity.class);
                    videoIntent.putExtra("videoItem",new String[]{item.getYoutubeID(),item.getTitle(), item.getDescription(), item.getThumbnail()});
                    v.getContext().startActivity(videoIntent);
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
        public final ImageView imageView;
        public final LinearLayout cardHolder;
        public VideoItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            imageView = (ImageView)view.findViewById(R.id.videoImage);
            cardHolder= (LinearLayout)view.findViewById(R.id.cardHolder);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }


}
