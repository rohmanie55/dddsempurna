package com.mr.rohmani.diadiasempurna.model;

/**
 * Created by USER on 31/07/2017.
 */

public class storyModel {
    private int part;
    private String title;
    private String body;

    public storyModel(){

    }

    public storyModel(int part, String title, String body) {
        this.part = part;
        this.title = title;
        this.body = body;
    }

    public int getPart() {
        return part;
    }

    public void setParty(int part) {
        this.part = part;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
