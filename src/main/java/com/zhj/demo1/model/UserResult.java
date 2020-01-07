package com.zhj.demo1.model;

import java.util.List;

/**
 * @author hongjay
 * @description
 * @since 2019/12/13
 */
public class UserResult {

    private List<MyUser> results;
    private int count;

    public List<MyUser> getResults() {
        return results;
    }

    public void setResults(List<MyUser> results) {
        this.results = results;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
