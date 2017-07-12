package com.ketaki.creative.movieapp.Model;

import java.util.List;


public class MovieVideoResults {

    private Integer id;
    private List<VideoInfo> results = null;

    public List<VideoInfo> getResults() {
        return results;
    }

    public void setResults(List<VideoInfo> results) {
        this.results = results;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}