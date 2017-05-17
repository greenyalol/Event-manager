package com.pts.greenya.eventmanager.managers;

import com.pts.greenya.eventmanager.network.RestService;
import com.pts.greenya.eventmanager.network.ServiceGenerator;
import com.pts.greenya.eventmanager.network.req.EventAddReq;
import com.pts.greenya.eventmanager.network.req.ParticipantAddReq;
import com.pts.greenya.eventmanager.network.res.Event;
import com.pts.greenya.eventmanager.network.res.EventCategory;
import com.pts.greenya.eventmanager.network.res.Person;

import java.util.List;

import retrofit2.Call;

/**
 * Created by Greenya on 03.05.2017.
 */

public class DataManager {

    private RestService restService;

    private static DataManager INSTANCE = null;

    public static DataManager getINSTANCE() {
        if(INSTANCE == null) {
            INSTANCE = new DataManager();
        }
        return INSTANCE;
    }

    public DataManager() {
        this.restService = ServiceGenerator.createService(RestService.class);
    }

    public Call<List<Event>> getEvents() {
        return restService.getEvents();
    }

    public Call<List<EventCategory>> getCategories() {
        return restService.getCategories();
    }
    public Call<List<Person>> getParticipants(int eventID){ return restService.getParticipants(eventID); }
    public Call<Void> addParticipant(ParticipantAddReq participantAddReq, int eventID) {

        return restService.addParticipant(participantAddReq, eventID);

    }
    public Call<Void> addEvent(EventAddReq eventAddReq) {
        return restService.addEvent(eventAddReq);
    }
}
