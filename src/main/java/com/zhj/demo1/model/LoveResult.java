package com.zhj.demo1.model;

import java.util.List;

/**
 * @author hongjay
 * @description
 * @since 2019/12/13
 */
public class LoveResult {

    private List<LoveStages> results;
    private int count;

    public List<LoveStages> getResults() {
        return results;
    }

    public void setResults(List<LoveStages> results) {
        this.results = results;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
