package com.mr.rohmani.diadiasempurna.model;

/**
 * Created by USER on 03/08/2017.
 */

public class modelBookmark {
    public int part;
    public String title;

    public modelBookmark(int part, String title) {
        this.part = part;
        this.title = title;
    }

    public int getPart() {
        return part;
    }

    public void setPart(int part) {
        this.part = part;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
