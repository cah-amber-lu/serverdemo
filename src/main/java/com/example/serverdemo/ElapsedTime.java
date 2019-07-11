package com.example.serverdemo;

import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ElapsedTime {

    private long startTime;

    private long endTime;

    private long totalTime;

    private List<Long> increments;

    public ElapsedTime() {
        increments = new ArrayList<>();
    }

    public void addIncrement(long increment) {
        increments.add(increment);
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public List<Long> getIncrements() {
        return increments;
    }

    public void setIncrements(List<Long> increments) {
        this.increments = increments;
    }

    public long getLastIncrement() {
        if (increments.size() > 1) {
            return increments.get(increments.size() - 1) - increments.get(increments.size() - 2);
        }
        else {
            return increments.get(0) - startTime;
        }
    }
}
