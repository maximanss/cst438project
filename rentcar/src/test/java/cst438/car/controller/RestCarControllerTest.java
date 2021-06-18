package cst438.car.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import cst438.car.domain.*;
import cst438.car.service.CarService;

//class must be annotated as WebMvcTest,  not SpringBootTest
@WebMvcTest(RestCarController.class)
public class RestCarControllerTest {
    
    // declare CarService as @MockBean
    @MockBean
    private CarService carService;

    // This class is used for make simulated HTTP requests to the REST Controller
    // being tested.
    @Autowired
    private MockMvc mvc;

    // These objects will be magically initialized by the initFields method below.
    private JacksonTester<ReserveInfo> jsonReserveAttempt;

     // This method is executed before each Test.
     @BeforeEach
     public void setUpEach() {
         MockitoAnnotations.initMocks(this);
         JacksonTester.initFields(this, new ObjectMapper());
     }

     /**
      * This test will check for acceptable partner company request
      */
     @Test
     public void testBookCar_1() throws Exception {

     // prepare the stub for the CarService. 
     // the passing parameter for the stub
     Reservation reservation = new Reservation();
     reservation.setCompanyid(999);
     reservation.setStartdate(Date.valueOf("2030-01-01"));
     reservation.setEnddate(Date.valueOf("2030-01-10"));
     reservation.setLocation("testcity");
     String cartype = "suv";
     
     // prepare the expected result for the stub
     ReserveInfo reserveInfo = new ReserveInfo(100L, "SUV", "Car Description", (float)900.0);
     
     // the return value from the stub using the above parameters and expected result
     given(carService.bookPartnerReservation(reservation, cartype))
             .willReturn(new ResponseEntity<ReserveInfo>(reserveInfo, HttpStatus.OK));
     
     // perform the test by making simulated HTTP get using URL of
     // "/api/book" to the RestCarController
     MockHttpServletResponse response = mvc.perform(
             get("/api/book?companyid=999&startdate=2030-01-01&enddate=2030-01-10&location=testcity&cartype=suv"))
             .andReturn()
             .getResponse();

     // first verify the http status response code is as expected
     assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

     System.out.println("Mock Response:" + response.getContentAsString());

     // convert returned data from JSON string format to ReserveInfo object
     ReserveInfo testResult = jsonReserveAttempt.parseObject(response.getContentAsString());

     ReserveInfo expectedResult = reserveInfo;

     // compare actual return data with expected data
     // MUST implement .equals( ) method for ReserveInfo class.
     assertThat(testResult).isEqualTo(expectedResult);
     }

 }