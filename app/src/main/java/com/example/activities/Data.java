package com.example.activities;

public class Data {
    private String type;
    private String id;
    private String url;
    private String slug;
    private String bitly_gif_url;
    private String bitly_url;
    private String embed_url;
    private String username;
    private String source;
    private String title;
    private String rating;

    public Data(String type, String id, String url, String slug, String bitly_gif_url, String bitly_url, String embed_url, String username, String source, String title, String rating) {
        this.type = type;
        this.id = id;
        this.url = url;
        this.slug = slug;
        this.bitly_gif_url = bitly_gif_url;
        this.bitly_url = bitly_url;
        this.embed_url = embed_url;
        this.username = username;
        this.source = source;
        this.title = title;
        this.rating = rating;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getSlug() {
        return slug;
    }

    public String getBitly_gif_url() {
        return bitly_gif_url;
    }

    public String getBitly_url() {
        return bitly_url;
    }

    public String getEmbed_url() {
        return embed_url;
    }

    public String getUsername() {
        return username;
    }

    public String getSource() {
        return source;
    }

    public String getTitle() {
        return title;
    }

    public String getRating() {
        return rating;
    }
}
