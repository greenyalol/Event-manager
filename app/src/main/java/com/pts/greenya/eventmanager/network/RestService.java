package com.pts.greenya.eventmanager.network;


import com.pts.greenya.eventmanager.network.req.EventAddReq;
import com.pts.greenya.eventmanager.network.req.ParticipantAddReq;
import com.pts.greenya.eventmanager.network.res.Event;
import com.pts.greenya.eventmanager.network.res.EventCategory;
import com.pts.greenya.eventmanager.network.res.Person;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestService {

    @GET("events")
    Call<List<Event>> getEvents();

    @GET("categories")
    Call<List<EventCategory>> getCategories();

    @GET("participants")
    Call<List<Person>> getParticipants(@Query("eventID") int eventID);

    @POST("participant/add/{eventID}")
    Call<Void> addParticipant(@Body ParticipantAddReq participantAddReq, @Path("eventID") int eventID);

    @POST("event/add")
    Call<Void> addEvent(@Body EventAddReq eventAddReq);

}
