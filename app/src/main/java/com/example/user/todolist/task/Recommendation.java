package com.example.user.todolist.task;

import com.example.user.todolist.category.Category;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class Recommendation implements Comparable<Recommendation> {

    private int id;
    private String title;
    private String description;
    private String date;
    private Category category;

    public Recommendation() {

    }

    public Recommendation(String title, String description, String date, Category category) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.category = category;
    }


    public Recommendation(int id, String title, String description, String date, Category category) {
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

    public long daysLeft() {
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        long diff = 0;
        try {
            Date taskDate = myFormat.parse(date);
            diff = (long) taskDate.getTime() - currentDate.getTime();
            System.out.println("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Recommendation otherRecommendation) {
        int myTime = (int) this.daysLeft();
        int otherTaskTime = (int) otherRecommendation.daysLeft();
        int difference = myTime - otherTaskTime;
        if (difference == 0) {
            return 0;
        } else if (difference > 0){
            return 1;
        } else {
            return -1;
        }
    }

    public static ArrayList<Recommendation> returnTestArray(){
        ArrayList<Recommendation> result = new ArrayList<>();
        Recommendation recommendation1 = new Recommendation("It, a novel", "", "", Category.BOOKS);
        Recommendation recommendation2 = new Recommendation("The handmaid's tale", "", "", Category.BOOKS);
        Recommendation recommendation3 = new Recommendation("Queens of Stone Age: Villains", "", "", Category.MUSIC);
        Recommendation recommendation4 = new Recommendation("Buy Tickets for Kasabian", "", "2-10-2017", Category.GIGS);
        Recommendation recommendation8 = new Recommendation("Learn Android Studio", "", "", Category.CODING);
        Recommendation recommendation5 = new Recommendation("Learn C#", "", "", Category.CODING);
        Recommendation recommendation6 = new Recommendation("Cameo Cinema", "", "", Category.MOVIES);
        Recommendation recommendation7 = new Recommendation("Ramen", "", "", Category.FOOD);
        result.add(recommendation1);
        result.add(recommendation2);
        result.add(recommendation3);
        result.add(recommendation4);
        result.add(recommendation5);
        result.add(recommendation6);
        result.add(recommendation7);
        result.add(recommendation8);
        return result;
    }

    public static ArrayList<Recommendation> returnTaskOfCategory (ArrayList<Recommendation> arrayList, Category category){
        ArrayList<Recommendation> resultArray = new ArrayList<>();
        for (Recommendation recommendation : arrayList){
            if (recommendation.getCategory().equals(category)){
                resultArray.add(recommendation);
            }
        }
        return resultArray;
    }

}