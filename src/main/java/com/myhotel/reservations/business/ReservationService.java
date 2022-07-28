package com.myhotel.reservations.business;

import com.myhotel.reservations.data.Hotel;
import com.myhotel.reservations.data.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class ReservationService {
    private final HotelRepository hotelRepository;

    public ReservationService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public String add(RoomReservation reservation){
          return this.hotelRepository.findById(1).get().getHotelName();


    }
}