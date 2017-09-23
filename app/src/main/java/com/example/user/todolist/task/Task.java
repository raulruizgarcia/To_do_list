package com.example.user.todolist.task;

import com.example.user.todolist.category.Category;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

//import java.util.*;

public class Task {

    private int id;
    private String title;
    private String description;
    private String date;
    private Category category;

    public Task(){

    }

    public Task(String title, String description, String date, Category category) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.category = category;
    }

    public Task(String title, String description, String date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public Task(int id, String title, String description, String date, Category category){
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
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

    public void setDate(String date) {
        this.date = date;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public long daysLeft(){
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        long diff = 0;
        try {
            Date taskDate = myFormat.parse(date);
            diff = (long) currentDate.getTime() - taskDate.getTime();
            System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

    }
}
