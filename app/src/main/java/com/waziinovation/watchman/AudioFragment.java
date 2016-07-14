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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.waziinovation.watchman.audio.AudioContent;
import com.waziinovation.watchman.audio.AudioItem;
import com.waziinovation.watchman.helper.DividerItemDecoration;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class AudioFragment extends Fragment {
    //URL to get JSON Array
    private static String audioJSONUrl = "http://brandug.com/json.php";
    //JSON Node Names
    private static final String TAG_TITLE = "title";
    private static final String TAG_CONTENT = "content";
    private static final String TAG_DETAILS = "details";
    private static final String TAG_URL = "url";
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    public static Context audioContext = null;

    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AudioFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AudioFragment newInstance(int columnCount,Context context) {
        AudioFragment fragment = new AudioFragment();
        Bundle args = new Bundle();
        audioContext = context;
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
        View view = inflater.inflate(R.layout.fragment_audio_list, container, false);

        // Set the adapter
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        final ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.watchman_audio_progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        if (recyclerView instanceof RecyclerView) {
            Context context = view.getContext();
            final AudioContent audioContent = new AudioContent();

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));

            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
           final AudioRecyclerViewAdapter audioRecyclerViewAdapter = new AudioRecyclerViewAdapter(audioContent.ITEMS,null);
            recyclerView.setAdapter(audioRecyclerViewAdapter);
            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(context, LinearLayoutManager.VERTICAL);
            recyclerView.addItemDecoration(itemDecoration);
            RequestQueue requestQueue = Volley.newRequestQueue(audioContext);
            // Creating the JsonArrayRequest class called arrayreq, passing the required parameters
            //JsonURL is the URL to be fetched from
            JsonArrayRequest arrayreq = new JsonArrayRequest(audioJSONUrl,
                    // The second parameter Listener overrides the method onResponse() and passes
                    //JSONArray as a parameter
                    new Response.Listener<JSONArray>() {

                        // Takes the response from the JSON request
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                // Retrieves first JSON object in outer array
                                JSONObject audioObj = response.getJSONObject(0);
                                // Retrieves "colorArray" from the JSON object
                                JSONArray audioArray = audioObj.getJSONArray("audio");
                                // Iterates through the JSON Array getting objects and adding them
                                //to the list view until there are no more objects in colorArray


                                for (int i = 0; i < audioArray.length(); i++) {
                                    //gets each JSON object within the JSON array
                                    JSONObject jsonObject = audioArray.getJSONObject(i);
                                    String title = audioArray.getJSONObject(i).getString(TAG_TITLE);
                                    String content = audioArray.getJSONObject(i).getString(TAG_CONTENT);
                                    String detail = audioArray.getJSONObject(i).getString(TAG_DETAILS);
                                    String url = audioArray.getJSONObject(i).getString(TAG_URL);

                                    audioContent.addItem(audioContent.createAudioItem(title, content, detail,url));

                                }
                                progressBar.setVisibility(View.GONE);
                                audioRecyclerViewAdapter.notifyDataSetChanged();

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
                            Log.e("Volley", "Error");
                        }
                    }
            );
            // Adds the JSON array request "arrayreq" to the request queue
            requestQueue.add(arrayreq);

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
        void onListFragmentInteraction(AudioItem item);
    }

}
