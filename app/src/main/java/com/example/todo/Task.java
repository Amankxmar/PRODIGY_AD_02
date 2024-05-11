package com.example.todo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Task {
    private String description;
    private Calendar dateTime;

    public Task(String description, Calendar dateTime) {
        this.description = description;
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public String getFormattedDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy - h:mm a", Locale.getDefault());
        return dateFormat.format(dateTime.getTime());
    }

    public Calendar getDateTime() {
        return dateTime;
    }
}
