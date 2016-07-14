package com.waziinovation.watchman.video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class VideoContent {

    /**
     * An array of sample (dummy) items.
     */
    public final List<VideoItem> ITEMS = new ArrayList<VideoItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public final Map<String, VideoItem> ITEM_MAP = new HashMap<String, VideoItem>();

    private static final int COUNT = 25;


    public  void addItem(VideoItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.title, item);
    }

    public  VideoItem createVideoItem(String id, String title, String description, String thumbnail) {
        return new VideoItem(id,title,description,thumbnail);
    }

    public  String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public  class VideoItem {

        private final String youtubeID;
        private final String title;
        private final String description;
        private final String thumbnail;

        public VideoItem(String id , String title, String description, String thumbnail) {
            this.title = title;
            this.description = description;
            this.thumbnail = thumbnail;
            this.youtubeID = id;
        }

        @Override
        public String toString() {
            return description;
        }

        public String getDescription() {
            return description;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public String getTitle() {
            return title;
        }

        public String getYoutubeID() {
            return youtubeID;
        }
    }
}
