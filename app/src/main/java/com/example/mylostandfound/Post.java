package com.example.mylostandfound;

public class Post {
    private int id;
    private String post;
    private String name;
    private String phone;
    private String decs;
    private String date;
    private String loc;
    private Double lat;
    private Double lon;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDecs() {
        return decs;
    }

    public void setDecs(String decs) {
        this.decs = decs;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public Post(int id, String post, String name, String phone, String decs, String date, String loc, Double lat, Double lon) {
        this.id = id;
        this.post = post;
        this.name = name;
        this.phone = phone;
        this.decs = decs;
        this.date = date;
        this.loc = loc;
        this.lat = lat;
        this.lon = lon;
    }
}
