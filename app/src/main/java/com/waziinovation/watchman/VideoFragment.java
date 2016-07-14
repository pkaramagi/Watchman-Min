package com.waziinovation.watchman;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.waziinovation.watchman.helper.GridSpacingItemDecoration;
import com.waziinovation.watchman.video.VideoContent;
import com.waziinovation.watchman.video.VideoContent.VideoItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class VideoFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    //JSON Node Names
    private static final String TAG_TITLE = "title";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_ID = "videoId";
    private static final String TAG_CONTENTDETAILS ="contentDetails";
    private static final String TAG_SNIPPET = "snippet";
    private static final String TAG_THUMB = "thumbnails";
    private static final String TAG_THUMB_MEDIUM = "high";
    private static final String TAG_THUMB_MEDIUM_URL = "url";



    private static String videoJSONUrl = "http://brandug.com/watchman/";

    // TODO: Customize parameters
    private int mColumnCount = 2;
    private OnListFragmentInteractionListener mListener;
    public static Context videoContext;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public VideoFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static VideoFragment newInstance(int columnCount , Context context) {
        VideoFragment fragment = new VideoFragment();
        videoContext = context;
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_list, container, false);

        // Set the adapter
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.watchman_video_list);
        final ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.watchman_video_progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        if (recyclerView instanceof RecyclerView) {

            Context context = view.getContext();

            final VideoContent videoContent = new VideoContent();

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            final VideoRecyclerViewAdapter videoRecyclerViewAdapter = new VideoRecyclerViewAdapter(videoContent.ITEMS, null, videoContext);
            recyclerView.setAdapter(videoRecyclerViewAdapter);
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(2,12,true));

            RequestQueue requestQueue = Volley.newRequestQueue(videoContext);
            // Creating the JsonArrayRequest class called arrayreq, passing the required parameters
            //JsonURL is the URL to be fetched from
            JsonObjectRequest objRequest = new JsonObjectRequest(videoJSONUrl,
                    // The second parameter Listener overrides the method onResponse() and passes
                    //JSONArray as a parameter
                    new Response.Listener<JSONObject>() {

                        // Takes the response from the JSON request
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject videoJson =  response;
                                JSONArray videoArray = videoJson.getJSONArray("items");
                                for (int i = 0; i < videoArray.length(); i++) {
                                    JSONObject videoObject = videoArray.getJSONObject(i);
                                    JSONObject snippet = videoObject.getJSONObject(TAG_SNIPPET);
                                    JSONObject contentDetails = videoObject.getJSONObject(TAG_CONTENTDETAILS);
                                    JSONObject thumbnails = snippet.getJSONObject(TAG_THUMB).getJSONObject(TAG_THUMB_MEDIUM);

                                    String title = snippet.getString(TAG_TITLE);
                                    String description = snippet.getString(TAG_DESCRIPTION);
                                    String id = contentDetails.getString(TAG_ID);
                                    String thumbnailUrl = thumbnails.getString(TAG_THUMB_MEDIUM_URL);

                                    videoContent.addItem(videoContent.createVideoItem(id,title,description,thumbnailUrl));
                                }

                                progressBar.setVisibility(View.GONE);
                                videoRecyclerViewAdapter.notifyDataSetChanged();


                            }
                            // Try and catch are included to handle any errors due to JSON
                            catch (JSONException e) {
                                // If an error occurs, this prints the error to the log
                                e.printStackTrace();
                            }
                        }
                    },
                    // The final parameter overrides the method onErrorResponse() and passes VolleyError
                    //as a parameter
                    new Response.ErrorListener() {
                        @Override
                        // Handles errors that occur due to Volley
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Volley", error.getMessage().toString());
                        }
                    }
            );
            requestQueue.add(objRequest);

        }
        return view;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(VideoItem item);
    }
}
