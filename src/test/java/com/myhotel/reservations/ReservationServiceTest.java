package com.myhotel.reservations;

import com.myhotel.reservations.util.NoRoomAvailableException;
import com.myhotel.reservations.util.OutOfRangeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ReservationServiceTest {

        @Autowired
        private MockMvc mockMvc;

        /*
         * @Autowired
         * public ReservationController reservationController;
         * 
         * @BeforeEach
         * public void setUp() {
         * mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();
         * }
         */

        @Test
        void shouldReturnDefaultMessage() throws Exception {

                this.mockMvc.perform(get("/reservation")).andDo(print()).andExpect(status().isOk()).andExpect(content()
                                .string(containsString("Room Reservation")));
        }

        @Test
        void shouldReturn() throws Exception {
                this.mockMvc.perform(post("/reservation")
                                .param("startDay", "0")
                                .param("endDay", "89")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)).andDo(print())
                                .andExpect(status().isOk());
        }

        @Test
        void shouldReturnOutOfRangeNegetive() throws Exception {

                        this.mockMvc.perform(post("/reservation")
                                        .param("startDay", "-6")
                                        .param("endDay", "2")
                                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                .andExpect(status().isBadRequest())
                                .andExpect(result->assertTrue(result.getResolvedException() instanceof OutOfRangeException))
                                .andExpect(result -> assertEquals("Value out of Range",result.getResolvedException().getMessage()));

        }

        @Test
        void shouldReturnOutOfRangePositive() throws Exception {

                        this.mockMvc.perform(post("/reservation")
                                        .param("startDay", "200")
                                        .param("endDay", "400")
                                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                .andExpect(status().isBadRequest())
                                .andExpect(result->assertTrue(result.getResolvedException() instanceof OutOfRangeException))
                                .andExpect(result -> assertEquals("Value out of Range",result.getResolvedException().getMessage()));

        }

        @Test
        void shouldAcceptRequest() throws Exception {

                this.mockMvc.perform(post("/hotel")
                                .param("hotelSize", "3")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                .andExpect(status().isOk());

                this.mockMvc.perform(post("/reservation")
                                .param("startDay", "0")
                                .param("endDay", "5")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                .andExpect(status().isOk());

                this.mockMvc.perform(post("/reservation")
                                .param("startDay", "7")
                                .param("endDay", "13")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                .andExpect(status().isOk());

                this.mockMvc.perform(post("/reservation")
                                .param("startDay", "3")
                                .param("endDay", "9")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                .andExpect(status().isOk());

                this.mockMvc.perform(post("/reservation")
                                .param("startDay", "5")
                                .param("endDay", "7")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                .andExpect(status().isOk());

                this.mockMvc.perform(post("/reservation")
                                .param("startDay", "6")
                                .param("endDay", "6")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                .andExpect(status().isOk());

                this.mockMvc.perform(post("/reservation")
                                .param("startDay", "0")
                                .param("endDay", "4")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                .andExpect(status().isOk());
        }

        @Test
        void shouldDeclineRequest() throws Exception {
                 /* set hotel size to 3 */

                        this.mockMvc.perform(post("/hotel")
                                        .param("hotelSize", "3")
                                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                        .andExpect(status().isOk());

                        this.mockMvc.perform(post("/reservation")
                                        .param("startDay", "1")
                                        .param("endDay", "3")
                                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                        .andExpect(status().isOk());

                        this.mockMvc.perform(post("/reservation")
                                        .param("startDay", "2")
                                        .param("endDay", "5")
                                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                        .andExpect(status().isOk());

                        this.mockMvc.perform(post("/reservation")
                                        .param("startDay", "1")
                                        .param("endDay", "9")
                                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                        .andExpect(status().isOk());

                        this.mockMvc.perform(post("/reservation")
                                        .param("startDay", "0")
                                        .param("endDay", "15")
                                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                        .andExpect(status().isConflict())
                                .andExpect(result->assertTrue(result.getResolvedException() instanceof NoRoomAvailableException))
                                .andExpect(result -> assertEquals("No rooms available",result.getResolvedException().getMessage()));


        }

        @Test
        void shouldAcceptAfterDeclineRequest() throws Exception {

                        /* set hotel size to 3 */

                        this.mockMvc.perform(post("/hotel")
                                        .param("hotelSize", "3")
                                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                        .andExpect(status().isOk());

                        this.mockMvc.perform(post("/reservation")
                                        .param("startDay", "1")
                                        .param("endDay", "3")
                                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                        .andExpect(status().isOk());

                        this.mockMvc.perform(post("/reservation")
                                        .param("startDay", "0")
                                        .param("endDay", "15")
                                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                        .andExpect(status().isOk());

                        this.mockMvc.perform(post("/reservation")
                                        .param("startDay", "1")
                                        .param("endDay", "9")
                                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                        .andExpect(status().isOk());

                        this.mockMvc.perform(post("/reservation")
                                        .param("startDay", "2")
                                        .param("endDay", "5")
                                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                .andExpect(status().isConflict())
                                .andExpect(result->assertTrue(result.getResolvedException() instanceof NoRoomAvailableException))
                                .andExpect(result -> assertEquals("No rooms available",result.getResolvedException().getMessage()));


                this.mockMvc.perform(post("/reservation")
                                        .param("startDay", "4")
                                        .param("endDay", "9")
                                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                        .andExpect(status().isOk());

        }

        @Test
        void shouldProcessComplexRequest() throws Exception {

                        /* set hotel size to 2 */

                        this.mockMvc.perform(post("/hotel")
                                        .param("hotelSize", "2")
                                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                        .andExpect(status().isOk());

                        this.mockMvc.perform(post("/reservation")
                                        .param("startDay", "1")
                                        .param("endDay", "3")
                                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                        .andExpect(status().isOk());

                        this.mockMvc.perform(post("/reservation")
                                        .param("startDay", "0")
                                        .param("endDay", "4")
                                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                        .andExpect(status().isOk());

                        this.mockMvc.perform(post("/reservation")
                                        .param("startDay", "2")
                                        .param("endDay", "3")
                                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                .andExpect(status().isConflict())
                                .andExpect(result->assertTrue(result.getResolvedException() instanceof NoRoomAvailableException))
                                .andExpect(result -> assertEquals("No rooms available",result.getResolvedException().getMessage()));


                this.mockMvc.perform(post("/reservation")
                                        .param("startDay", "5")
                                        .param("endDay", "5")
                                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                        .andExpect(status().isOk());

                        this.mockMvc.perform(post("/reservation")
                                        .param("startDay", "4")
                                        .param("endDay", "10")
                                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                .andExpect(status().isConflict())
                                .andExpect(result->assertTrue(result.getResolvedException() instanceof NoRoomAvailableException))
                                .andExpect(result -> assertEquals("No rooms available",result.getResolvedException().getMessage()));


                this.mockMvc.perform(post("/reservation")
                                        .param("startDay", "6")
                                        .param("endDay", "7")
                                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                        .andExpect(status().isOk());

                        this.mockMvc.perform(post("/reservation")
                                        .param("startDay", "8")
                                        .param("endDay", "10")
                                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                        .andExpect(status().isOk());

                        this.mockMvc.perform(post("/reservation")
                                        .param("startDay", "8")
                                        .param("endDay", "9")
                                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                        .andExpect(status().isOk());

        }
}
