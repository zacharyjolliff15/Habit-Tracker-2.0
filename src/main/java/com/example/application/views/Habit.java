package com.example.application.views;

public class Habit {
    private String name;
    private String description;
    private int completionCount;
    private double successPercentage;

    public Habit(String name, String description) {
        this.name = name;
        this.description = description;
        this.completionCount = 0;
        this.successPercentage = 0;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCompletionCount() {
        return completionCount;
    }

    public double getSuccessPercentage() {
        return successPercentage;
    }

    public void setSuccess(boolean success) {
        if (success) {
            completionCount++;
            successPercentage = (double) completionCount / (double) getTotalCount() * 100.0;
        } else {
            successPercentage = (double) completionCount / (double) getTotalCount() * 100.0;
        }
    }

    private int getTotalCount() {
        return completionCount == 0 ? 1 : completionCount;
    }
}
