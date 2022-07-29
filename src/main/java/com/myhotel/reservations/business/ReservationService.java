package com.myhotel.reservations.business;

import com.myhotel.reservations.data.Hotel;
import com.myhotel.reservations.data.HotelRepository;
import com.myhotel.reservations.data.Reservation;
import com.myhotel.reservations.data.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;


@Service
public class ReservationService {
    private final HotelRepository hotelRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(HotelRepository hotelRepository, ReservationRepository reservationRepository) {
        this.hotelRepository = hotelRepository;
        this.reservationRepository = reservationRepository;
    }

    public String add(Reservation reservation) throws Exception{
            boolean isOutsidePlanningPeriod = isOutsidePlanningPeriod(reservation);
            if(isOutsidePlanningPeriod)
                throw new Exception("Value out of Range");

        get();

        reservation.setRoomNumber(1);
            this.reservationRepository.save(reservation);
          return this.hotelRepository.findById(1).get().getHotelName();
    }

    public List<Reservation> get() throws Exception {
        Iterable<Reservation> reservationIterable=  this.reservationRepository.findAll();
        List<Reservation> reservations = new ArrayList<>();

        reservationIterable.forEach(r->{

            Reservation reservation = new Reservation();
            reservation.setReservationId(r.getReservationId());
            reservation.setRoomNumber(r.getRoomNumber());
            reservation.setStartDay(r.getStartDay());
            reservation.setEndDay(r.getEndDay());

            reservations.add(reservation);

        });

        return reservations;


    }


    private List<Integer> getAvailableRooms(Reservation reservation)
    {
        Iterator<Reservation> reservations= (Iterator<Reservation>) this.reservationRepository.findAll();
        int[] arr = { 1, 2, 3, 4, 5 };

        List<Integer> list = Arrays.stream(arr)        // IntStream
                .boxed()          // Stream<Integer>
                .collect(Collectors.toList());


        return list;
    }

    private boolean isOutsidePlanningPeriod (Reservation reservation)
    {
        if (reservation.getEndDay()<0 || reservation.getEndDay()>365)
            return true;
        else if (reservation.getStartDay()<0 || reservation.getStartDay()>365)
            return true;
        else
            return false;

    }


}