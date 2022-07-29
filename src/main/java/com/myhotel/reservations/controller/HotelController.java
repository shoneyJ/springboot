package com.myhotel.reservations.controller;

import com.myhotel.reservations.business.HotelService;
import com.myhotel.reservations.business.ReservationService;
import com.myhotel.reservations.data.Hotel;
import com.myhotel.reservations.data.Reservation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HotelController {

    private  final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/hotel")
    public String reservationForm(Model model) {
        model.addAttribute("hotel", new Hotel());
        return "hotel";
    }

    @PostMapping("/hotel")
    public String reservationSubmit(@ModelAttribute Hotel hotel, Model model) throws Exception {
        model.addAttribute("hotel", hotel);
        System.out.println(this.hotelService.add(hotel));

        return "reservationlist";
    }

}
