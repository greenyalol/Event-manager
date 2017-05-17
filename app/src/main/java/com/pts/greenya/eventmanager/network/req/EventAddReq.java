package com.pts.greenya.eventmanager.network.req;

import com.pts.greenya.eventmanager.network.res.EventCategory;
import com.pts.greenya.eventmanager.network.res.Person;

/**
 * Created by Greenya on 15.05.2017.
 */

public class EventAddReq {

    private String eventName;
    private EventCategory eventCategory;
    private Person person;
    private String startTime;
    private String endTime;
    private String description;
    private String city;
    private String address;

    public EventAddReq(String eventName, EventCategory eventCategory, Person person, String startTime, String endTime, String description, String city, String address) {
        this.eventName = eventName;
        this.eventCategory = eventCategory;
        this.person = person;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.city = city;
        this.address = address;
    }
}
