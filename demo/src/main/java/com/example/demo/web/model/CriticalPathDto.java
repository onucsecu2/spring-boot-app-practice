package com.example.demo.web.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CriticalPathDto {
    @Id
    String id;
    int earlyStart;
    int earlyFinish;
    int lateStart;
    int lateFinish;
    int floatStart;
    int floatFinish;


    int duration;

    public CriticalPathDto(String id, int earlyStart, int earlyFinish, int lateStart, int lateFinish, int floatStart, int floatFinish, int duration) {
        this.id = id;
        this.earlyStart = earlyStart;
        this.earlyFinish = earlyFinish;
        this.lateStart = lateStart;
        this.lateFinish = lateFinish;
        this.floatStart = floatStart;
        this.floatFinish = floatFinish;
        this.duration = duration;
    }

    public CriticalPathDto(String id, int earlyStart, int earlyFinish, int duration) {
        this.id = id;
        this.earlyStart = earlyStart;
        this.earlyFinish = earlyFinish;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getEarlyStart() {
        return earlyStart;
    }

    public void setEarlyStart(int earlyStart) {
        this.earlyStart = earlyStart;
    }

    public int getEarlyFinish() {
        return earlyFinish;
    }

    public void setEarlyFinish(int earlyFinish) {
        this.earlyFinish = earlyFinish;
    }

    public int getLateStart() {
        return lateStart;
    }

    public void setLateStart(int lateStart) {
        this.lateStart = lateStart;
    }

    public int getLateFinish() {
        return lateFinish;
    }

    public void setLateFinish(int lateFinish) {
        this.lateFinish = lateFinish;
    }

    public int getFloatStart() {
        return floatStart;
    }

    public void setFloatStart(int floatStart) {
        this.floatStart = floatStart;
    }

    public int getFloatFinish() {
        return floatFinish;
    }

    public void setFloatFinish(int floatFinish) {
        this.floatFinish = floatFinish;
    }
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

}
