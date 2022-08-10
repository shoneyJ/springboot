package com.myhotel.reservations.business;

import com.myhotel.reservations.data.Hotel;
import com.myhotel.reservations.data.HotelRepository;
import org.springframework.stereotype.Service;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public String set(Hotel hotel){
        Hotel entity = this.hotelRepository.findById(1).get();
        entity.setHotelSize(hotel.getHotelSize());
        this.hotelRepository.save(entity);
        return "ok";

    }

}