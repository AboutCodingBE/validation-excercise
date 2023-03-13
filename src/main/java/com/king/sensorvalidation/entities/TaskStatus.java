package com.king.sensorvalidation.entities;


public enum TaskStatus {

     PENDING(1, "The task is waiting to start"),
     COMPLETED(2, "The task has successfully completed"),
     ERROR(3, "The task was either interrupted by an error or it completed causing an error"),

     ONGOING(4, "The task is ongoing");

     private final int taskId;
     private final String description;

     boolean isCompleted(){
         return this == TaskStatus.COMPLETED;
     }

     TaskStatus(int i, String description) {
          this.taskId = i;
          this.description = description;
     }
}