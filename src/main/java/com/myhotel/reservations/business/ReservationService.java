package com.myhotel.reservations.business;

import com.myhotel.reservations.data.HotelRepository;
import com.myhotel.reservations.data.Reservation;
import com.myhotel.reservations.data.ReservationRepository;
import com.myhotel.reservations.util.NoRoomAvailableException;
import com.myhotel.reservations.util.OutOfRangeException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ReservationService {
    private final HotelRepository hotelRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(HotelRepository hotelRepository, ReservationRepository reservationRepository) {
        this.hotelRepository = hotelRepository;
        this.reservationRepository = reservationRepository;
    }

    public void add(Reservation reservation) throws Exception {
        boolean isOutsidePlanningPeriod = isOutsidePlanningPeriod(reservation);
        if (isOutsidePlanningPeriod)
            throw new OutOfRangeException("Value out of Range");

        int roomNumber = getAvailableRoomNumber(reservation);
        if (roomNumber == 0)
            throw new NoRoomAvailableException("No rooms available");
        else {
            reservation.setRoomNumber(roomNumber);
            this.reservationRepository.save(reservation);
        }

    }

    public List<Reservation> get() throws Exception {
        Iterable<Reservation> reservationIterable = this.reservationRepository.findAll();
        List<Reservation> reservations = new ArrayList<>();

        reservationIterable.forEach(r -> {

            Reservation reservation = new Reservation();
            reservation.setReservationId(r.getReservationId());
            reservation.setRoomNumber(r.getRoomNumber());
            reservation.setStartDay(r.getStartDay());
            reservation.setEndDay(r.getEndDay());

            reservations.add(reservation);

        });

        return reservations;

    }

    private Integer getAvailableRoomNumber(Reservation reservation) throws Exception {

        List<Integer> notAvailableRoomNumbers = new ArrayList<>();

        Map<Integer, RoomReservation> roomReservationMap = new HashMap<>();

        Iterable<Reservation> reservations = this.reservationRepository.findAll();

        reservations.forEach(res -> {
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomNumber(res.getRoomNumber());
            List<Integer> range = IntStream.rangeClosed(res.getStartDay(), res.getEndDay())
                    .boxed().collect(Collectors.toList());
            roomReservation.setBookDays(range);

            roomReservationMap.put(res.getRoomNumber(), roomReservation);
        });

        /* Check if new reservation is within the range of existing reservations */

        List<Integer> newRange = IntStream.rangeClosed(reservation.getStartDay(), reservation.getEndDay())
                .boxed().collect(Collectors.toList());
        ;

        for (Integer id : roomReservationMap.keySet()) {

            Set<Integer> similar = new HashSet<Integer>(newRange);
            similar.retainAll(roomReservationMap.get(id).getBookDays());

            /* similar has values then Id aka roomNumber is not available */

            if (!similar.isEmpty())
                /* store the not available room number in a variable */
                notAvailableRoomNumbers.add(id);
        }

        /* get the size of hotel and check available rooms */

        int hotelSize = this.hotelRepository.findById(1).get().getHotelSize();
        List<Integer> hotelRooms = IntStream.rangeClosed(1, hotelSize)
                .boxed().collect(Collectors.toList());

        Set<Integer> similar = new HashSet<Integer>(hotelRooms);
        Set<Integer> availableRooms = new HashSet<Integer>();
        availableRooms.addAll(hotelRooms);
        availableRooms.addAll(notAvailableRoomNumbers);

        similar.retainAll(notAvailableRoomNumbers);

        availableRooms.removeAll(similar);
        /* different contains list of available rooms */

        if (!availableRooms.isEmpty())
            /* find the smallest room number */
            return Collections.min(availableRooms);
        else
            return 0;

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