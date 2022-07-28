package com.myhotel.reservations.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.myhotel.reservations.business.RoomReservation;

@Controller
public class ReservationController {

    @GetMapping("/reservation")
    public String reservationForm(Model model) {
        model.addAttribute("reservation", new RoomReservation());
        return "reservation";
    }

    @PostMapping("/reservation")
    public String reservationSubmit(@ModelAttribute RoomReservation reservation, Model model) {
        model.addAttribute("reservation", reservation);
        return "reservation";
    }

}
