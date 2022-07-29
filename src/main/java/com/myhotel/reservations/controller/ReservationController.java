package com.myhotel.reservations.controller;

import com.myhotel.reservations.business.ReservationService;
import com.myhotel.reservations.data.Reservation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReservationController {

    private  final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservation")
    public String reservationForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        return "reservation";
    }

    @PostMapping("/reservation")
    public String reservationSubmit(@ModelAttribute Reservation reservation, Model model) {
        model.addAttribute("reservation", reservation);
         System.out.println(this.reservationService.add(reservation));

        return "reservationlist";
    }

}
