package com.myhotel.reservations.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends CrudRepository<Hotel,Integer> {
}
