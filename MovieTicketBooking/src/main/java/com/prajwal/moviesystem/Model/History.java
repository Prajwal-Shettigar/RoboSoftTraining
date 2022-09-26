package com.prajwal.moviesystem.Model;
/*
{
"userId":1,
"showId":1,
"seatsBooked":2
}
 */
public class History {

    private int userId;
    private int showId;
    private int seatsBooked;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public int getSeatsBooked() {
        return seatsBooked;
    }

    public void setSeatsBooked(int seatsBooked) {
        this.seatsBooked = seatsBooked;
    }
}
