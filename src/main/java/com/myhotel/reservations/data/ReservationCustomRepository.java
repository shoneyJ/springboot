package com.myhotel.reservations.data;

public interface ReservationCustomRepository {
    int getAvailableRoom(int startDay,int endDay);
}
