package com.waziinovation.watchman.audio;

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
public class AudioContent{

    /**
     * An array of Audio items.
     */
    public  final List<AudioItem> ITEMS = new ArrayList<AudioItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public  final Map<String, AudioItem> ITEM_MAP = new HashMap<String, AudioItem>();

    public void addItem(AudioItem item) {
        this.ITEMS.add(item);
        ITEM_MAP.put(item.getTitle(), item);
    }

    public  AudioItem createAudioItem(String title, String content, String details,String url) {
        return new AudioItem(title,content,details,url);
    }


}
