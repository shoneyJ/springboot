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

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public String add(Hotel hotel) throws Exception{
       Hotel entity= this.hotelRepository.findById(1).get();
        entity.setHotelSize(hotel.getHotelSize());
       this.hotelRepository.save(entity);
       return "ok";

    }

}
