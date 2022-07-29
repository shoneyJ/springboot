package com.myhotel.reservations.business;

import com.myhotel.reservations.data.Hotel;
import com.myhotel.reservations.data.HotelRepository;
import com.myhotel.reservations.data.Reservation;
import com.myhotel.reservations.data.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HotelService {


    private final HotelRepository hotelRepository;
    private final ReservationRepository reservationRepository;

    public HotelService(HotelRepository hotelRepository, ReservationRepository reservationRepository) {
        this.hotelRepository = hotelRepository;
        this.reservationRepository = reservationRepository;
    }

    public String add(Hotel hotel) throws Exception{

       hotel.setHotelId(1);
       this.hotelRepository.save(hotel);

       return "ok";

    }

}
