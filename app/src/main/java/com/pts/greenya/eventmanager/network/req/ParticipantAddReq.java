package com.pts.greenya.eventmanager.network.req;

/**
 * Created by Greenya on 03.05.2017.
 */

public class ParticipantAddReq {

    private String firstName;
    private String lastName;
    private int vkRef;

    public ParticipantAddReq(String firstName, String lastName, int vkRef) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.vkRef = vkRef;
    }
}
