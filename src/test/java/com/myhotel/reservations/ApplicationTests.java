package com.myhotel.reservations;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void shouldReturnDefaultMessage() throws Exception {

		this.mockMvc.perform(get("/reservation")).andDo(print()).andExpect(status().isOk()).andExpect(content()
				.string(containsString("Room Reservation")));
	}

	@Test
	void shouldReturn() throws Exception {
		this.mockMvc.perform(post("/reservation")
				.param("startDay","0")
						.param("endDay","89")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
		).andDo(print()).andExpect(status().isOk());
	}

	@Test
	void shouldReturnOutOfRange() throws Exception {
		try {
			this.mockMvc.perform(post("/reservation")
							.param("startDay", "-6")
							.param("endDay", "0")
							.contentType(MediaType.APPLICATION_FORM_URLENCODED))
					.andExpect(status().isInternalServerError())
					.andExpect(content().string("Value out of Range"));
		}catch (Exception e){
			//sink it
		}
	}

}
