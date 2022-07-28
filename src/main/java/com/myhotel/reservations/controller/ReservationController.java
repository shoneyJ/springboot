package com.myhotel.reservations.controller;

import com.myhotel.reservations.business.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.myhotel.reservations.business.RoomReservation;

import java.time.Clock;

@Controller
public class ReservationController {

    private  final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservation")
    public String reservationForm(Model model) {
        model.addAttribute("reservation", new RoomReservation());
        return "reservation";
    }

    @PostMapping("/reservation")
    public String reservationSubmit(@ModelAttribute RoomReservation reservation, Model model) {
        model.addAttribute("reservation", reservation);
         System.out.println(this.reservationService.add(reservation));

        return "reservationlist";
    }

}
