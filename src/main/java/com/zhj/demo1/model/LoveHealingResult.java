package com.zhj.demo1.model;

import java.util.List;

/**
 * @author hongjay
 * @description
 * @since 2019/12/13
 */
public class LoveHealingResult {

    private List<LoveHealing> results;
    private int count;

    public List<LoveHealing> getResults() {
        return results;
    }

    public void setResults(List<LoveHealing> results) {
        this.results = results;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
