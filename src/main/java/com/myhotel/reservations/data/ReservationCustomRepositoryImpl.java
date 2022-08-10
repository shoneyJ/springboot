package com.myhotel.reservations.data;

import com.myhotel.reservations.business.RoomReservation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReservationCustomRepositoryImpl implements ReservationCustomRepository {

    @PersistenceContext
    private EntityManager em;
    @Override
    public int getAvailableRoom(int startDay, int endDay) {

        Hotel hotel =em.find(Hotel.class,1);

        List<Integer> notAvailableRoomNumbers = new ArrayList<>();

        Map<Integer, RoomReservation> roomReservationMap = new HashMap<>();

        Query query = em.createQuery("SELECT r FROM Reservation r",Reservation.class);

        Iterable<Reservation> reservations =query.getResultList();

        reservations.forEach(res -> {
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomNumber(res.getRoomNumber());
            List<Integer> range = IntStream.rangeClosed(res.getStartDay(), res.getEndDay())
                    .boxed().collect(Collectors.toList());
            roomReservation.setBookDays(range);

            roomReservationMap.put(res.getRoomNumber(), roomReservation);
        });

        /* Check if new reservation is within the range of existing reservations */

        List<Integer> newRange = IntStream.rangeClosed(startDay,endDay)
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

        int hotelSize =hotel.getHotelSize();
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
}
