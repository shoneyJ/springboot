package com.myhotel.reservations.data;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MYHOTEL")
public class Hotel {

    @Id
    @Column(name="HOTEL_ID")
    private int hotelId;
    @Column(name = "HOTEL_NAME")
    private String  hotelName;

    @Column(name="HOTEL_SIZE")
    private int hotelSize;

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public int getHotelSize() {
        return hotelSize;
    }

    public void setHotelSize(int hotelSize) {
        this.hotelSize = hotelSize;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }
}
