package com.github.thushear.cache;

import java.util.Set;

/**
 * Created by kongming on 2016/3/17.
 */
public abstract class AbstractSortedSetCache extends BaseCacheDomain {


    private Set<String> resultSet ;

    private String member;

    private double score;

    private long start;

    private long end;

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }


    public Set<String> getResultSet() {
        return resultSet;
    }

    public void setResultSet(Set<String> resultSet) {
        this.resultSet = resultSet;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }
}
