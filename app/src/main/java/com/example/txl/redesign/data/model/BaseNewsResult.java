package com.example.txl.redesign.data.model;

import java.util.List;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/25
 * description：
 */
public class BaseNewsResult {
    private String count;
    private boolean error;
    private List<NewsData> results;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<NewsData> getResults() {
        return results;
    }

    public void setResults(List<NewsData> results) {
        this.results = results;
    }
}
