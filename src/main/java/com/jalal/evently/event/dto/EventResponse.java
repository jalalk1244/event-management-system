package com.jalal.evently.event.dto;

import java.time.LocalDateTime;

public class EventResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime dateTime;
    private String location;
    private Integer capacity;

    public EventResponse(Long id, String title, String description, LocalDateTime dateTime, String location, Integer capacity) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.location = location;
        this.capacity = capacity;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDateTime getDateTime() { return dateTime; }
    public String getLocation() { return location; }
    public Integer getCapacity() { return capacity; }
}
