package com.example.user.todolist.task;

import com.example.user.todolist.category.Category;
import com.example.user.todolist.sqlRunner.SqlRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by user on 22/09/2017.
 */

public class Runner {

    public static void main (String[] args){

        Task task = new Task("Title", "Description", "20-09-2017", Category.ADULT_STUFF);
        System.out.println(task.daysLeft());

    }

}
