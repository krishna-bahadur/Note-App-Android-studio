package com.example.todoapp.model;

import java.sql.Timestamp;

public class ItemModel {
    protected String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    protected String description;
    protected String time;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    protected int Id;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    protected int flag;
}
