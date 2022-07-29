package com.myhotel.reservations.business;

import java.util.List;

public class RoomReservation {

    private int roomNumber;
    private List<Integer> bookDays;

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public List<Integer> getBookDays() {
        return bookDays;
    }

    public void setBookDays(List<Integer> bookDays) {
        this.bookDays = bookDays;
    }
}
