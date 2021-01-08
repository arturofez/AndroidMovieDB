package com.example.androidmoviedb;

import java.util.List;

public class SearchResult {

    private int page;
    private List<Movie> results;
    private int total_pages;
    private int total_results;

    public SearchResult() {}

    public SearchResult(int page, List<Movie> results, int total_pages, int total_results) {
        this.page = page;
        this.results = results;
        this.total_pages = total_pages;
        this.total_results = total_results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getResult() {
        return results;
    }

    public void setResult(List<Movie> result) {
        this.results = result;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_result() {
        return total_results;
    }

    public void setTotal_result(int total_result) {
        this.total_results = total_result;
    }
}
