package com.myhotel.reservations.business;

import com.myhotel.reservations.data.Reservation;
import com.myhotel.reservations.data.ReservationRepository;
import com.myhotel.reservations.util.NoRoomAvailableException;
import com.myhotel.reservations.util.OutOfRangeException;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }


    public void add(Reservation reservation) throws Exception {
       System.out.println( );

        if (isOutsidePlanningPeriod(reservation))
            throw new OutOfRangeException("Value out of Range");

        int roomNumber = this.reservationRepository.getAvailableRoom(reservation.getStartDay(),reservation.getEndDay());

        if (roomNumber == 0)
            throw new NoRoomAvailableException("No rooms available");
        else {
            reservation.setRoomNumber(roomNumber);
            this.reservationRepository.save(reservation);
        }

    }

    private boolean isOutsidePlanningPeriod(Reservation reservation) {
        if (reservation.getEndDay() < 0 || reservation.getEndDay() > 365)
            return true;
        else if (reservation.getStartDay() < 0 || reservation.getStartDay() > 365)
            return true;
        else
            return false;

    }

}