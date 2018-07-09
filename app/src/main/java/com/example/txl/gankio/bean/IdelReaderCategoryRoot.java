package com.example.txl.gankio.bean;

import java.util.List;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/6
 * description：
 */
public class IdelReaderCategoryRoot {

    boolean error;
    List<IdelReaderCategory> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<IdelReaderCategory> getCategories() {
        return results;
    }

    public void setCategories(List<IdelReaderCategory> categories) {
        this.results = categories;
    }

    public class IdelReaderCategory {
        String _id;
        String en_name;
        String name;
        int rank;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getEn_name() {
            return en_name;
        }

        public void setEn_name(String en_name) {
            this.en_name = en_name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }
    }
}
