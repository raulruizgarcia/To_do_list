package com.example.user.todolist.task;

import com.example.user.todolist.category.Category;

import java.util.Date;

//import java.util.*;

public class Task {

    private int id;
    private String title;
    private String description;
    private Date date;
    private Category category;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public Category getCategory() {
        return category;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
