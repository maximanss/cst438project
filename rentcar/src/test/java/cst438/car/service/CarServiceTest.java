package cst438.car.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;

import cst438.car.domain.*;


/**
 * This is used to test all the functions in the CarService without connecting to
 *  the database repositories
 * @author Max
 *
 */
@SpringBootTest
public class CarServiceTest {
    
    // declare all the repository class as @MockBean 
    // so the repository objects will be stubbed by mock objects in the test

    @MockBean
    private UserRepository mockUserRepository;

    @MockBean
    private CarRepository mockCarRepository;
    
    @MockBean
    private ReservationRepository mockReservationRepository;
    
    @MockBean
    private PartnerRepository mockPartnerRepository;

    // declare the CarService as @MockBean for unit testing
    @MockBean
    private CarService cs;
    
     // This method is executed before each Test.
     @BeforeEach
     public void setUpEach() {
         MockitoAnnotations.initMocks(this);

     }

        
    // Test the function CarService.getAvailableCars
    // - test dates not overlapping with any reservations
    @Test
    public void testGetAvailableCars_1() throws Exception {

        // create some car inventories for testing
        CarType type1 = new CarType(1, "Type1");
        CarType type2 = new CarType(2, "Type2");
        CarType type3 = new CarType(3, "Type3");
        Car car1 = new Car(1L, "Description 1", 4, "Location10", "image1", 1, 10, type1);
        Car car2 = new Car(2L, "Description 2", 5, "Location10", "image2", 2, 20, type2);
        Car car3 = new Car(3L, "Description 3", 6, "Location10", "image3", 3, 30, type3);
        List<Car> cars = new ArrayList<Car>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        
        // create some reservation records for testing
        Reservation r1 = new Reservation(11L, 1L, Date.valueOf("2021-07-01"), Date.valueOf("2021-07-10"), 2L, 0L, 200, "Location10");
        Reservation r2 = new Reservation(22L, 1L, Date.valueOf("2021-07-11"), Date.valueOf("2021-07-12"), 3L, 0L, 100, "Location10");
        Reservation r3 = new Reservation(33L, 1L, Date.valueOf("2021-08-30"), Date.valueOf("2021-09-10"), 0L, 1L, 300, "Location10");
        List<Reservation> rs = new ArrayList<Reservation>();
        rs.add(r1);
        rs.add(r2);
        rs.add(r3);
        
        // Initiate a CarService object wit mock repositories
        cs = new CarService(mockCarRepository, mockUserRepository, mockReservationRepository);

        
        // this is the stub for CarRepository
        // When given location = "Location10", it will return a list of cars at location 10
        given(mockCarRepository.findByLocationIgnoreCase("Location10")).willReturn(cars);

        // this is the stub for the ReservationRepository. 
        // When given input carid=1, it will return a list of reservations for carid=1.
        // When given input carid=2 or 3, it will return an empty list
        given(mockReservationRepository.findByCarid(1L)).willReturn(rs);
        given(mockReservationRepository.findByCarid(2L)).willReturn(new ArrayList<Reservation>());
        given(mockReservationRepository.findByCarid(3L)).willReturn(new ArrayList<Reservation>());
        
        // Create a reservation with only the location, start date and end date specified
        // no overlapping dates
        Reservation rTest = new Reservation();
        rTest.setLocation("Location10");
        rTest.setStartdate(Date.valueOf("2021-10-05"));
        rTest.setEnddate(Date.valueOf("2021-10-12"));
        
        // Expected Results
        List<Car> expectedResult = new ArrayList<Car>();
        expectedResult.add(car1);
        expectedResult.add(car2);
        expectedResult.add(car3);

        // Get the Actual Results from the function with mock stubs
        List<Car> availableCars = cs.getAvailableCars(rTest);

        // for debugging purpose
        for (Car c : availableCars) {
            System.out.println(c);
        }
        
        // compare actual return data with expected data
        // MUST implement .equals( ) method for CityInfo class.
        assertThat(availableCars).isEqualTo(expectedResult);
    }
    
    // Test the function CarService.getAvailableCars
    // - test the overlapping dates: start date overlap with reservations
    @Test
    public void testGetAvailableCars_2() throws Exception {

        // create some car inventories for testing
        CarType type1 = new CarType(1, "Type1");
        CarType type2 = new CarType(2, "Type2");
        CarType type3 = new CarType(3, "Type3");
        Car car1 = new Car(1L, "Description 1", 4, "Location10", "image1", 1, 10, type1);
        Car car2 = new Car(2L, "Description 2", 5, "Location10", "image2", 2, 20, type2);
        Car car3 = new Car(3L, "Description 3", 6, "Location10", "image3", 3, 30, type3);
        List<Car> cars = new ArrayList<Car>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        
        // create some reservation records for testing
        Reservation r1 = new Reservation(11L, 1L, Date.valueOf("2021-07-01"), Date.valueOf("2021-07-10"), 2L, 0L, 200, "Location10");
        Reservation r2 = new Reservation(22L, 1L, Date.valueOf("2021-07-11"), Date.valueOf("2021-07-12"), 3L, 0L, 100, "Location10");
        Reservation r3 = new Reservation(33L, 1L, Date.valueOf("2021-08-30"), Date.valueOf("2021-09-10"), 0L, 1L, 300, "Location10");
        List<Reservation> rs = new ArrayList<Reservation>();
        rs.add(r1);
        rs.add(r2);
        rs.add(r3);
        
        // Initiate a CarService object wit mock repositories
        cs = new CarService(mockCarRepository, mockUserRepository, mockReservationRepository);

        
        // this is the stub for CarRepository
        // When given location = "Location10", it will return a list of cars at location 10
        given(mockCarRepository.findByLocationIgnoreCase("Location10")).willReturn(cars);

        // this is the stub for the ReservationRepository. 
        // When given input carid=1, it will return a list of reservations for carid=1.
        // When given input carid=2 or 3, it will return an empty list
        given(mockReservationRepository.findByCarid(1L)).willReturn(rs);
        given(mockReservationRepository.findByCarid(2L)).willReturn(new ArrayList<Reservation>());
        given(mockReservationRepository.findByCarid(3L)).willReturn(new ArrayList<Reservation>());
        
        // Create a reservation with only the location, start date and end date specified
        // overlapping dates on start date
        Reservation rTest = new Reservation();
        rTest.setLocation("Location10");
        rTest.setStartdate(Date.valueOf("2021-07-05"));
        rTest.setEnddate(Date.valueOf("2021-07-12"));
        
        // Expected Results
        List<Car> expectedResult = new ArrayList<Car>();
        expectedResult.add(car2);
        expectedResult.add(car3);

        // Get the Actual Results from the function with mock stubs
        List<Car> availableCars = cs.getAvailableCars(rTest);

        // for debugging purpose
        for (Car c : availableCars) {
            System.out.println(c);
        }
        
        // compare actual return data with expected data
        // MUST implement .equals( ) method for CityInfo class.
        assertThat(availableCars).isEqualTo(expectedResult);
    }
    
    // Test the function CarService.getAvailableCars
    // - test the overlapping dates that end date overlap with the reservation
    @Test
    public void testGetAvailableCars_3() throws Exception {

        // create some car inventories for testing
        CarType type1 = new CarType(1, "Type1");
        CarType type2 = new CarType(2, "Type2");
        CarType type3 = new CarType(3, "Type3");
        Car car1 = new Car(1L, "Description 1", 4, "Location10", "image1", 1, 10, type1);
        Car car2 = new Car(2L, "Description 2", 5, "Location10", "image2", 2, 20, type2);
        Car car3 = new Car(3L, "Description 3", 6, "Location10", "image3", 3, 30, type3);
        List<Car> cars = new ArrayList<Car>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        
        // create some reservation records for testing
        Reservation r1 = new Reservation(11L, 1L, Date.valueOf("2021-07-01"), Date.valueOf("2021-07-10"), 2L, 0L, 200, "Location10");
        Reservation r2 = new Reservation(22L, 1L, Date.valueOf("2021-07-11"), Date.valueOf("2021-07-12"), 3L, 0L, 100, "Location10");
        Reservation r3 = new Reservation(33L, 1L, Date.valueOf("2021-08-30"), Date.valueOf("2021-09-10"), 0L, 1L, 300, "Location10");
        List<Reservation> rs = new ArrayList<Reservation>();
        rs.add(r1);
        rs.add(r2);
        rs.add(r3);
        
        // Initiate a CarService object wit mock repositories
        cs = new CarService(mockCarRepository, mockUserRepository, mockReservationRepository);

        
        // this is the stub for CarRepository
        // When given location = "Location10", it will return a list of cars at location 10
        given(mockCarRepository.findByLocationIgnoreCase("Location10")).willReturn(cars);

        // this is the stub for the ReservationRepository. 
        // When given input carid=1, it will return a list of reservations for carid=1.
        // When given input carid=2 or 3, it will return an empty list
        given(mockReservationRepository.findByCarid(1L)).willReturn(rs);
        given(mockReservationRepository.findByCarid(2L)).willReturn(new ArrayList<Reservation>());
        given(mockReservationRepository.findByCarid(3L)).willReturn(new ArrayList<Reservation>());
        
        // Create a reservation with only the location, start date and end date specified
        // overlapping dates on start date
        Reservation rTest = new Reservation();
        rTest.setLocation("Location10");
        rTest.setStartdate(Date.valueOf("2021-07-13"));
        rTest.setEnddate(Date.valueOf("2021-09-01"));
        
        // Expected Results
        List<Car> expectedResult = new ArrayList<Car>();
        expectedResult.add(car2);
        expectedResult.add(car3);

        // Get the Actual Results from the function with mock stubs
        List<Car> availableCars = cs.getAvailableCars(rTest);

        // for debugging purpose
        for (Car c : availableCars) {
            System.out.println(c);
        }
        
        // compare actual return data with expected data
        // MUST implement .equals( ) method for CityInfo class.
        assertThat(availableCars).isEqualTo(expectedResult);
    }
    
    // Test the function CarService.getAvailableCars
    // - test the date boundary: start date land on the end date of reserved car
    @Test
    public void testGetAvailableCars_4() throws Exception {

        // create some car inventories for testing
        CarType type1 = new CarType(1, "Type1");
        CarType type2 = new CarType(2, "Type2");
        CarType type3 = new CarType(3, "Type3");
        Car car1 = new Car(1L, "Description 1", 4, "Location10", "image1", 1, 10, type1);
        Car car2 = new Car(2L, "Description 2", 5, "Location10", "image2", 2, 20, type2);
        Car car3 = new Car(3L, "Description 3", 6, "Location10", "image3", 3, 30, type3);
        List<Car> cars = new ArrayList<Car>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        
        // create some reservation records for testing, this time the carid set to 2
        Reservation r1 = new Reservation(11L, 2L, Date.valueOf("2021-07-01"), Date.valueOf("2021-07-10"), 2L, 0L, 200, "Location10");
        Reservation r2 = new Reservation(22L, 2L, Date.valueOf("2021-07-11"), Date.valueOf("2021-07-12"), 3L, 0L, 100, "Location10");
        Reservation r3 = new Reservation(33L, 2L, Date.valueOf("2021-08-30"), Date.valueOf("2021-09-10"), 0L, 1L, 300, "Location10");
        List<Reservation> rs = new ArrayList<Reservation>();
        rs.add(r1);
        rs.add(r2);
        rs.add(r3);
        
        // Initiate a CarService object wit mock repositories
        cs = new CarService(mockCarRepository, mockUserRepository, mockReservationRepository);

        
        // this is the stub for CarRepository
        // When given location = "Location10", it will return a list of cars at location 10
        given(mockCarRepository.findByLocationIgnoreCase("Location10")).willReturn(cars);

        // this is the stub for the ReservationRepository. 
        // When given input carid=2, it will return a list of reservations for carid=1.
        // When given input carid=1 or 3, it will return an empty list
        given(mockReservationRepository.findByCarid(1L)).willReturn(new ArrayList<Reservation>());
        given(mockReservationRepository.findByCarid(2L)).willReturn(rs);
        given(mockReservationRepository.findByCarid(3L)).willReturn(new ArrayList<Reservation>());
        
        // Create a reservation with only the location, start date and end date specified
        Reservation rTest = new Reservation();
        rTest.setLocation("Location10");
        rTest.setStartdate(Date.valueOf("2021-07-12"));
        rTest.setEnddate(Date.valueOf("2021-08-01"));
        
        // Expected Results
        List<Car> expectedResult = new ArrayList<Car>();
        expectedResult.add(car1);
        expectedResult.add(car3);

        // Get the Actual Results from the function with mock stubs
        List<Car> availableCars = cs.getAvailableCars(rTest);

        // for debugging purpose
        for (Car c : availableCars) {
            System.out.println(c);
        }
        
        // compare actual return data with expected data
        // MUST implement .equals( ) method for CityInfo class.
        assertThat(availableCars).isEqualTo(expectedResult);
    }
    
    // Test the function CarService.getAvailableCars
    // - test the date boundary: end date land on the start date of reserved car
    @Test
    public void testGetAvailableCars_5() throws Exception {

        // create some car inventories for testing
        CarType type1 = new CarType(1, "Type1");
        CarType type2 = new CarType(2, "Type2");
        CarType type3 = new CarType(3, "Type3");
        Car car1 = new Car(1L, "Description 1", 4, "Location10", "image1", 1, 10, type1);
        Car car2 = new Car(2L, "Description 2", 5, "Location10", "image2", 2, 20, type2);
        Car car3 = new Car(3L, "Description 3", 6, "Location10", "image3", 3, 30, type3);
        List<Car> cars = new ArrayList<Car>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        
        // create some reservation records for testing, this time the carid set to 2
        Reservation r1 = new Reservation(11L, 2L, Date.valueOf("2021-07-01"), Date.valueOf("2021-07-10"), 2L, 0L, 200, "Location10");
        Reservation r2 = new Reservation(22L, 2L, Date.valueOf("2021-07-11"), Date.valueOf("2021-07-12"), 3L, 0L, 100, "Location10");
        Reservation r3 = new Reservation(33L, 2L, Date.valueOf("2021-08-30"), Date.valueOf("2021-09-10"), 0L, 1L, 300, "Location10");
        List<Reservation> rs = new ArrayList<Reservation>();
        rs.add(r1);
        rs.add(r2);
        rs.add(r3);
        
        // Initiate a CarService object wit mock repositories
        cs = new CarService(mockCarRepository, mockUserRepository, mockReservationRepository);

        
        // this is the stub for CarRepository
        // When given location = "Location10", it will return a list of cars at location 10
        given(mockCarRepository.findByLocationIgnoreCase("Location10")).willReturn(cars);

        // this is the stub for the ReservationRepository. 
        // When given input carid=2, it will return a list of reservations for carid=1.
        // When given input carid=1 or 3, it will return an empty list
        given(mockReservationRepository.findByCarid(1L)).willReturn(new ArrayList<Reservation>());
        given(mockReservationRepository.findByCarid(2L)).willReturn(rs);
        given(mockReservationRepository.findByCarid(3L)).willReturn(new ArrayList<Reservation>());
        
        // Create a reservation with only the location, start date and end date specified
        Reservation rTest = new Reservation();
        rTest.setLocation("Location10");
        rTest.setStartdate(Date.valueOf("2021-07-19"));
        rTest.setEnddate(Date.valueOf("2021-08-30"));
        
        // Expected Results
        List<Car> expectedResult = new ArrayList<Car>();
        expectedResult.add(car1);
        expectedResult.add(car3);

        // Get the Actual Results from the function with mock stubs
        List<Car> availableCars = cs.getAvailableCars(rTest);

        // for debugging purpose
        for (Car c : availableCars) {
            System.out.println(c);
        }
        
        // compare actual return data with expected data
        // MUST implement .equals( ) method for CityInfo class.
        assertThat(availableCars).isEqualTo(expectedResult);
    }
    
    
    // Test the function CarService.getAvailableCars
    // - test no cars in the inventory at that location
    @Test
    public void testGetAvailableCars_6() throws Exception {

        // no cars at that location
        List<Car> cars = new ArrayList<Car>();
                
        // create some reservation records for testing, this time the carid set to 2
        Reservation r1 = new Reservation(11L, 2L, Date.valueOf("2021-07-01"), Date.valueOf("2021-07-10"), 2L, 0L, 200, "Location10");
        Reservation r2 = new Reservation(22L, 2L, Date.valueOf("2021-07-11"), Date.valueOf("2021-07-12"), 3L, 0L, 100, "Location10");
        Reservation r3 = new Reservation(33L, 2L, Date.valueOf("2021-08-30"), Date.valueOf("2021-09-10"), 0L, 1L, 300, "Location10");
        List<Reservation> rs = new ArrayList<Reservation>();
        rs.add(r1);
        rs.add(r2);
        rs.add(r3);
        
        // Initiate a CarService object wit mock repositories
        cs = new CarService(mockCarRepository, mockUserRepository, mockReservationRepository);

        
        // this is the stub for CarRepository
        // When given location = "Location10", it will return an empty list of cars at Location10
        given(mockCarRepository.findByLocationIgnoreCase("Location10")).willReturn(cars);

        // this is the stub for the ReservationRepository. 
        // When given input carid=2, it will return a list of reservations for carid=1.
        // When given input carid=1 or 3, it will return an empty list
        given(mockReservationRepository.findByCarid(1L)).willReturn(new ArrayList<Reservation>());
        given(mockReservationRepository.findByCarid(2L)).willReturn(rs);
        given(mockReservationRepository.findByCarid(3L)).willReturn(new ArrayList<Reservation>());
        
        // Create a reservation with only the location, start date and end date specified
        Reservation rTest = new Reservation();
        rTest.setLocation("Location10");
        rTest.setStartdate(Date.valueOf("2021-09-19"));
        rTest.setEnddate(Date.valueOf("2021-10-30"));
        
        // Expected Results should be empty list
        List<Car> expectedResult = new ArrayList<Car>();
        
        // Get the Actual Results from the function with mock stubs
        List<Car> availableCars = cs.getAvailableCars(rTest);

        // for debugging purpose
        for (Car c : availableCars) {
            System.out.println(c);
        }
        
        // compare actual return data with expected data
        // MUST implement .equals( ) method for CityInfo class.
        assertThat(availableCars).isEqualTo(expectedResult);
    }
    
    // Test the function CarService.getAvailableCars
    // - test no reservations with the given carid
    @Test
    public void testGetAvailableCars_7() throws Exception {

        // create some car inventories for testing
        CarType type1 = new CarType(1, "Type1");
        CarType type2 = new CarType(2, "Type2");
        CarType type3 = new CarType(3, "Type3");
        Car car1 = new Car(1L, "Description 1", 4, "Location10", "image1", 1, 10, type1);
        Car car2 = new Car(2L, "Description 2", 5, "Location10", "image2", 2, 20, type2);
        Car car3 = new Car(3L, "Description 3", 6, "Location10", "image3", 3, 30, type3);
        List<Car> cars = new ArrayList<Car>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        
        // create some reservation records for testing, no reservation for the carid
        List<Reservation> rs = new ArrayList<Reservation>();
        
        
        // Initiate a CarService object wit mock repositories
        cs = new CarService(mockCarRepository, mockUserRepository, mockReservationRepository);

        
        // this is the stub for CarRepository
        // When given location = "Location10", it will return a list of cars at location 10
        given(mockCarRepository.findByLocationIgnoreCase("Location10")).willReturn(cars);

        // this is the stub for the ReservationRepository. 
        // When given input carid=2, it will return a list of reservations for carid=1.
        // When given input carid=1 or 3, it will return an empty list
        given(mockReservationRepository.findByCarid(1L)).willReturn(new ArrayList<Reservation>());
        given(mockReservationRepository.findByCarid(2L)).willReturn(rs);
        given(mockReservationRepository.findByCarid(3L)).willReturn(new ArrayList<Reservation>());
        
        // Create a reservation with only the location, start date and end date specified
        Reservation rTest = new Reservation();
        rTest.setLocation("Location10");
        rTest.setStartdate(Date.valueOf("2021-07-19"));
        rTest.setEnddate(Date.valueOf("2021-08-30"));
        
        // Expected Results - all the cars at Location10 are available
        List<Car> expectedResult = new ArrayList<Car>();
        expectedResult.add(car1);
        expectedResult.add(car2);
        expectedResult.add(car3);
        
        // Get the Actual Results from the function with mock stubs
        List<Car> availableCars = cs.getAvailableCars(rTest);

        // for debugging purpose
        for (Car c : availableCars) {
            System.out.println(c);
        }
        
        // compare actual return data with expected data
        // MUST implement .equals( ) method for CityInfo class.
        assertThat(availableCars).isEqualTo(expectedResult);
    }
    
    // Test the function CarService.cancelUserReservation
    // - test a record will be deleted if the reservation is found 
    //   with given reservation number and matching email address
    @Test
    public void testCancelUserReservation_1() throws Exception {
        
        // create a user for testing
        User u1 = new User(100L, "email100", "user100", "pwd100", "9100", "address100");
        List<User> users = new ArrayList<User>();
        users.add(u1);
                
        // create some reservation records for testing, this time the carid set to 2
        Reservation r1 = new Reservation(11L, 2L, Date.valueOf("2021-07-01"), Date.valueOf("2021-07-10"), 100L, 0L, 200, "Location10");
        Reservation r2 = new Reservation(22L, 2L, Date.valueOf("2021-07-11"), Date.valueOf("2021-07-12"), 100L, 0L, 100, "Location10");
        Reservation r3 = new Reservation(33L, 2L, Date.valueOf("2021-08-30"), Date.valueOf("2021-09-10"), 100L, 0L, 300, "Location10");
        List<Reservation> rs = new ArrayList<Reservation>();
        rs.add(r2);
        
        // Initiate a CarService object wit mock repositories
        cs = new CarService(mockCarRepository, mockUserRepository, mockReservationRepository);
        
        // stub for UserRepository with given email="email100"
        given(mockUserRepository.findByEmailaddress("email100")).willReturn(users);
        
        // stub for ReservationRepository with given userid = 100
        given(mockReservationRepository.findByReserveidAndUserid(22L, 100L)).willReturn(rs);
        
        // stub for ReservationRepository when try to delete the reservation
        doNothing().when(mockReservationRepository).deleteById(22L);
        
        
        
        // Expected Result
        boolean expectedResult = true;
        
        boolean actualResult = cs.cancelUserReservation("email100", 22L);
        
        // verify the deletById is called once only
        verify(mockReservationRepository, times(1)).deleteById(22L);
        
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    // Test the function CarService.cancelUserReservation
    // - test no records will be deleted if user email is not found in the record
    @Test
    public void testCancelUserReservation_2() throws Exception {
        
        // create a user for testing - this case user not found
        List<User> users = new ArrayList<User>();
        
                
        // create some reservation records for testing, this time the carid set to 2
        List<Reservation> rs = new ArrayList<Reservation>();
        
        // Initiate a CarService object wit mock repositories
        cs = new CarService(mockCarRepository, mockUserRepository, mockReservationRepository);
        
        // stub for UserRepository with given email="email100"
        given(mockUserRepository.findByEmailaddress("email100")).willReturn(users);
        
        // Expected Result is false since user does not exist
        boolean expectedResult = false;
        
        boolean actualResult = cs.cancelUserReservation("email100", 22L);
        
        // verify the deletById is never called
        verify(mockReservationRepository, times(0)).deleteById(22L);
        
        assertThat(actualResult).isEqualTo(expectedResult);
    }
    
    // Test the function CarService.cancelUserReservation
    // - test no record will be deleted if user does not have the reservation with that number.
    @Test
    public void testCancelUserReservation_3() throws Exception {
        
        // create a user for testing
        User u1 = new User(100L, "email100", "user100", "pwd100", "9100", "address100");
        List<User> users = new ArrayList<User>();
        users.add(u1);
                
        // create some reservation records for testing, this time the carid set to 2
        List<Reservation> rs = new ArrayList<Reservation>();
        
        // Initiate a CarService object wit mock repositories
        cs = new CarService(mockCarRepository, mockUserRepository, mockReservationRepository);
        
        // stub for UserRepository with given email="email100"
        given(mockUserRepository.findByEmailaddress("email100")).willReturn(users);
        
        // stub for ReservationRepository with given userid = 100
        given(mockReservationRepository.findByReserveidAndUserid(22L, 100L)).willReturn(rs);
        
       
        // Expected Result
        boolean expectedResult = false;
        
        boolean actualResult = cs.cancelUserReservation("email100", 22L);
        
              
        assertThat(actualResult).isEqualTo(expectedResult);
    }
    
    // Test the function CarService.setTotalPrice
    // - test the total price is correct for rental less than 7 days
    // 
    @Test
    public void testSetTotalPrice_1() throws Exception {
        
        
        // Initiate a CarService object wit mock repositories even is not used in this function
        // to ensure the repository interface is not called
        cs = new CarService(mockCarRepository, mockUserRepository, mockReservationRepository);
        
        // create a car record for testing
        Car cTest = new Car();
        cTest.setDailyprice(1);
        cTest.setWeeklyprice(10);
        
        // create a reservation for testing
        Reservation rTest = new Reservation();
        rTest.setStartdate(Date.valueOf("2021-07-11"));
        rTest.setEnddate(Date.valueOf("2021-07-17"));
        rTest.setTotalprice(0);
        
        // Expected Result for reservation
        Reservation expectedResult = new Reservation();
        expectedResult.setStartdate(Date.valueOf("2021-07-11"));
        expectedResult.setEnddate(Date.valueOf("2021-07-17"));
        expectedResult.setTotalprice(6);
        
        cs.setTotalPrice(rTest, cTest);
        
        assertThat(rTest).isEqualTo(expectedResult);
        
    }
    
    // Test the function CarService.setTotalPrice
    // - test the total price is zero when the enddate is same as startdate
    // 
    @Test
    public void testSetTotalPrice_2() throws Exception {
        
        
        // Initiate a CarService object wit mock repositories even is not used in this function
        // to ensure the repository interface is not called
        cs = new CarService(mockCarRepository, mockUserRepository, mockReservationRepository);
        
        // create a car record for testing
        Car cTest = new Car();
        cTest.setDailyprice(1);
        cTest.setWeeklyprice(10);
        
        // create a reservation for testing
        Reservation rTest = new Reservation();
        rTest.setStartdate(Date.valueOf("2021-07-11"));
        rTest.setEnddate(Date.valueOf("2021-07-11"));
        rTest.setTotalprice(0);
        
        // Expected Result for reservation
        Reservation expectedResult = new Reservation();
        expectedResult.setStartdate(Date.valueOf("2021-07-11"));
        expectedResult.setEnddate(Date.valueOf("2021-07-11"));
        expectedResult.setTotalprice(0);
        
        cs.setTotalPrice(rTest, cTest);
        
        assertThat(rTest).isEqualTo(expectedResult);
        
    }
    
    // Test the function CarService.setTotalPrice
    // - test the total price is zero when the enddate is before startdate
    // 
    @Test
    public void testSetTotalPrice_3() throws Exception {
        
        
        // Initiate a CarService object wit mock repositories even is not used in this function
        // to ensure the repository interface is not called
        cs = new CarService(mockCarRepository, mockUserRepository, mockReservationRepository);
        
        // create a car record for testing
        Car cTest = new Car();
        cTest.setDailyprice(1);
        cTest.setWeeklyprice(10);
        
        // create a reservation for testing
        Reservation rTest = new Reservation();
        rTest.setStartdate(Date.valueOf("2021-07-11"));
        rTest.setEnddate(Date.valueOf("2021-07-10"));
        rTest.setTotalprice(0);
        
        // Expected Result for reservation
        Reservation expectedResult = new Reservation();
        expectedResult.setStartdate(Date.valueOf("2021-07-11"));
        expectedResult.setEnddate(Date.valueOf("2021-07-10"));
        expectedResult.setTotalprice(0);
        
        cs.setTotalPrice(rTest, cTest);
        
        assertThat(rTest).isEqualTo(expectedResult);
        
    }
    
    // Test the function CarService.setTotalPrice
    // - test the total price will take care of leap year and number of rental days are longer than 7 days
    // 
    @Test
    public void testSetTotalPrice_4() throws Exception {
        
        
        // Initiate a CarService object wit mock repositories even is not used in this function
        // to ensure the repository interface is not called
        cs = new CarService(mockCarRepository, mockUserRepository, mockReservationRepository);
        
        // create a car record for testing
        Car cTest = new Car();
        cTest.setDailyprice(1);
        cTest.setWeeklyprice(10);
        
        // create a reservation for testing
        Reservation rTest = new Reservation();
        rTest.setStartdate(Date.valueOf("2024-02-28"));
        rTest.setEnddate(Date.valueOf("2024-03-10"));
        rTest.setTotalprice(0);
        
        // Expected Result for reservation
        Reservation expectedResult = new Reservation();
        expectedResult.setStartdate(Date.valueOf("2024-02-28"));
        expectedResult.setEnddate(Date.valueOf("2024-03-10"));
        expectedResult.setTotalprice(11/7 * 10 + 11 % 7);
        
        cs.setTotalPrice(rTest, cTest);
        
        assertThat(rTest).isEqualTo(expectedResult);
        
    }
    
    // Test the function CarService.bookPartnerReservation(Reservation, Cartype)
    // - with the companyid is in the database, location and reservation dates are good but with Cartype="any"
    @Test
    public void testBookPartnerReservation_1() throws Exception {
        
        // create the company that match with the companyid in the reservation
        Partner company = new Partner(99L, "TestComapany", (float)0.99);
        List<Partner> partners = new ArrayList<Partner>();
        partners.add(company);
       
        // create some car inventories for testing
        CarType type1 = new CarType(1, "Standard");
        CarType type2 = new CarType(2, "SUV");
        CarType type3 = new CarType(3, "Truck");
        CarType type4 = new CarType(4, "Van");
        CarType type5 = new CarType(5, "Luxury");
        
        // Car(long carid, String description, int maxpassengers, String location, String image,
        //    float dailyprice, float weeklyprice, CarType cartype)
        Car car1 = new Car(1L, "Description 1", 4, "Location10", "image1", 1, 10, type1);
        Car car2 = new Car(2L, "Description 2", 5, "Location10", "image2", 2, 20, type2);
        Car car3 = new Car(3L, "Description 3", 6, "Location10", "image3", 3, 30, type3);
        Car car4 = new Car(4L, "Description 4", 7, "Location10", "image4", 4, 40, type4);
        Car car5 = new Car(5L, "Description 5", 8, "Location10", "image5", 5, 50, type5);
        List<Car> cars = new ArrayList<Car>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        cars.add(car4);
        cars.add(car5);
        
        // create empty list of reservation records for testing
        List<Reservation> rlist = new ArrayList<Reservation>();
        
        
        
        // Initiate a CarService object wit mock repositories
        cs = new CarService(mockCarRepository, mockUserRepository, mockReservationRepository, mockPartnerRepository);

        // this is the stub for PartnerRepository
        given(mockPartnerRepository.findByCompanyid(99L)).willReturn(partners);
        
        // this is the stub for CarRepository
        // When given location = "Location10", it will return a list of cars at location 10
        given(mockCarRepository.findByLocationIgnoreCase("Location10")).willReturn(cars);

        // this is the stub for the ReservationRepository. 
        // When given input carid=1 to 5 , it will return an empty list
        // because we are testing the overlapping dates
        given(mockReservationRepository.findByCarid(1L)).willReturn(rlist);
        given(mockReservationRepository.findByCarid(2L)).willReturn(rlist);
        given(mockReservationRepository.findByCarid(3L)).willReturn(rlist);
        given(mockReservationRepository.findByCarid(4L)).willReturn(rlist);
        given(mockReservationRepository.findByCarid(5L)).willReturn(rlist);
      
        // Create a reservation with only the location, start date and end date specified
        // no overlapping dates
        // create a reservation as input parameter to the bookPartnerReservation
        // Reservation(long reserveid, long carid, Date startdate, Date enddate, long userid,
        // long companyid, float totalprice, String location)
        Reservation reservation = new Reservation();
        reservation.setReserveid(0L);
        reservation.setCompanyid(99L);
        reservation.setStartdate(Date.valueOf("2021-12-01"));
        reservation.setEnddate(Date.valueOf("2021-12-6"));
        reservation.setLocation("Location10");
        
        given(mockReservationRepository.save(reservation)).willReturn(reservation);
        
        String type = "any";
        
        ReserveInfo reserveInfo = new ReserveInfo(0L, "Standard", "Description 1", (float)5.0);
        
                
        ResponseEntity<ReserveInfo> testResultResponse = cs.bookPartnerReservation(reservation, type);
        
        ReserveInfo testResult = testResultResponse.getBody();

        ReserveInfo expectedResult = reserveInfo;

        // first verify the http status response code is as expected
        assertThat(testResultResponse.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
        
        assertThat(testResult).isEqualTo(expectedResult);
        
    }
    
 // Test the function CarService.bookPartnerReservation(Reservation, Cartype)
    // - with the companyid is in the database, location and reservation dates are good but with Cartype="truck"
    @Test
    public void testBookPartnerReservation_2() throws Exception {
        
        // create the company that match with the companyid in the reservation
        Partner company = new Partner(99L, "TestComapany", (float)0.99);
        List<Partner> partners = new ArrayList<Partner>();
        partners.add(company);
       
        // create some car inventories for testing
        CarType type1 = new CarType(1, "Standard");
        CarType type2 = new CarType(2, "SUV");
        CarType type3 = new CarType(3, "Truck");
        CarType type4 = new CarType(4, "Van");
        CarType type5 = new CarType(5, "Luxury");
        
        // Car(long carid, String description, int maxpassengers, String location, String image,
        //    float dailyprice, float weeklyprice, CarType cartype)
        Car car1 = new Car(1L, "Description 1", 4, "Location10", "image1", 1, 10, type1);
        Car car2 = new Car(2L, "Description 2", 5, "Location10", "image2", 2, 20, type2);
        Car car3 = new Car(3L, "Description 3", 6, "Location10", "image3", 3, 30, type3);
        Car car4 = new Car(4L, "Description 4", 7, "Location10", "image4", 4, 40, type4);
        Car car5 = new Car(5L, "Description 5", 8, "Location10", "image5", 5, 50, type5);
        List<Car> cars = new ArrayList<Car>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        cars.add(car4);
        cars.add(car5);
        
        // create empty list of reservation records for testing
        List<Reservation> rlist = new ArrayList<Reservation>();
        
        
        
        // Initiate a CarService object wit mock repositories
        cs = new CarService(mockCarRepository, mockUserRepository, mockReservationRepository, mockPartnerRepository);

        // this is the stub for PartnerRepository
        given(mockPartnerRepository.findByCompanyid(99L)).willReturn(partners);
        
        // this is the stub for CarRepository
        // When given location = "Location10", it will return a list of cars at location 10
        given(mockCarRepository.findByLocationIgnoreCase("Location10")).willReturn(cars);

        // this is the stub for the ReservationRepository. 
        // When given input carid=1 to 5 , it will return an empty list
        // because we are testing the overlapping dates
        given(mockReservationRepository.findByCarid(1L)).willReturn(rlist);
        given(mockReservationRepository.findByCarid(2L)).willReturn(rlist);
        given(mockReservationRepository.findByCarid(3L)).willReturn(rlist);
        given(mockReservationRepository.findByCarid(4L)).willReturn(rlist);
        given(mockReservationRepository.findByCarid(5L)).willReturn(rlist);
      
        // Create a reservation with only the location, start date and end date specified
        // no overlapping dates
        // create a reservation as input parameter to the bookPartnerReservation
        // Reservation(long reserveid, long carid, Date startdate, Date enddate, long userid,
        // long companyid, float totalprice, String location)
        Reservation reservation = new Reservation();
        reservation.setReserveid(0L);
        reservation.setCompanyid(99L);
        reservation.setStartdate(Date.valueOf("2021-12-01"));
        reservation.setEnddate(Date.valueOf("2021-12-6"));
        reservation.setLocation("Location10");
        
        given(mockReservationRepository.save(reservation)).willReturn(reservation);
        
        String type = "truck";
        
        ReserveInfo reserveInfo = new ReserveInfo(0L, "Truck", "Description 3", (float)15.0);
        
                
        ResponseEntity<ReserveInfo> testResultResponse = cs.bookPartnerReservation(reservation, type);
        
        ReserveInfo testResult = testResultResponse.getBody();

        ReserveInfo expectedResult = reserveInfo;

        // first verify the http status response code is as expected
        assertThat(testResultResponse.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
        
        assertThat(testResult).isEqualTo(expectedResult);
        
    }
    
    
    // Test the function CarService.bookPartnerReservation(Reservation, Cartype)
    // - with the companyid is in the database, location and reservation dates are good but with Cartype="xxx"
    @Test
    public void testBookPartnerReservation_3() throws Exception {
        
        // create the company that match with the companyid in the reservation
        Partner company = new Partner(99L, "TestComapany", (float)0.99);
        List<Partner> partners = new ArrayList<Partner>();
        partners.add(company);
       
        // create some car inventories for testing
        CarType type1 = new CarType(1, "Standard");
        CarType type2 = new CarType(2, "SUV");
        CarType type3 = new CarType(3, "Truck");
        CarType type4 = new CarType(4, "Van");
        CarType type5 = new CarType(5, "Luxury");
        
        // Car(long carid, String description, int maxpassengers, String location, String image,
        //    float dailyprice, float weeklyprice, CarType cartype)
        Car car1 = new Car(1L, "Description 1", 4, "Location10", "image1", 1, 10, type1);
        Car car2 = new Car(2L, "Description 2", 5, "Location10", "image2", 2, 20, type2);
        Car car3 = new Car(3L, "Description 3", 6, "Location10", "image3", 3, 30, type3);
        Car car4 = new Car(4L, "Description 4", 7, "Location10", "image4", 4, 40, type4);
        Car car5 = new Car(5L, "Description 5", 8, "Location10", "image5", 5, 50, type5);
        List<Car> cars = new ArrayList<Car>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        cars.add(car4);
        cars.add(car5);
        
        // create empty list of reservation records for testing
        List<Reservation> rlist = new ArrayList<Reservation>();
        
        
        
        // Initiate a CarService object wit mock repositories
        cs = new CarService(mockCarRepository, mockUserRepository, mockReservationRepository, mockPartnerRepository);

        // this is the stub for PartnerRepository
        given(mockPartnerRepository.findByCompanyid(99L)).willReturn(partners);
        
        // this is the stub for CarRepository
        // When given location = "Location10", it will return a list of cars at location 10
        given(mockCarRepository.findByLocationIgnoreCase("Location10")).willReturn(cars);

        // this is the stub for the ReservationRepository. 
        // When given input carid=1 to 5 , it will return an empty list
        // because we are testing the overlapping dates
        given(mockReservationRepository.findByCarid(1L)).willReturn(rlist);
        given(mockReservationRepository.findByCarid(2L)).willReturn(rlist);
        given(mockReservationRepository.findByCarid(3L)).willReturn(rlist);
        given(mockReservationRepository.findByCarid(4L)).willReturn(rlist);
        given(mockReservationRepository.findByCarid(5L)).willReturn(rlist);
      
        // Create a reservation with only the location, start date and end date specified
        // no overlapping dates
        // create a reservation as input parameter to the bookPartnerReservation
        // Reservation(long reserveid, long carid, Date startdate, Date enddate, long userid,
        // long companyid, float totalprice, String location)
        Reservation reservation = new Reservation();
        reservation.setReserveid(0L);
        reservation.setCompanyid(99L);
        reservation.setStartdate(Date.valueOf("2021-12-01"));
        reservation.setEnddate(Date.valueOf("2021-12-6"));
        reservation.setLocation("Location10");
        
        given(mockReservationRepository.save(reservation)).willReturn(reservation);
        
        String type = "xxx";
        
        ReserveInfo reserveInfo = null;
        
                
        ResponseEntity<ReserveInfo> testResultResponse = cs.bookPartnerReservation(reservation, type);
        
        ReserveInfo testResult = testResultResponse.getBody();

        ReserveInfo expectedResult = reserveInfo;

        // first verify the http status response code is as expected
        assertThat(testResultResponse.getStatusCodeValue()).isEqualTo(HttpStatus.NO_CONTENT.value());
        
        assertThat(testResult).isEqualTo(expectedResult);
        
    }
    
    // Test the function CarService.bookPartnerReservation(Reservation, Cartype)
    // - with the companyid is not in the database
    @Test
    public void testBookPartnerReservation_4() throws Exception {
        
        // create the company that match with the companyid in the reservation
        Partner company = new Partner(99L, "TestComapany", (float)0.99);
        List<Partner> partners = new ArrayList<Partner>();
        partners.add(company);
       
        // create some car inventories for testing
        CarType type1 = new CarType(1, "Standard");
        CarType type2 = new CarType(2, "SUV");
        CarType type3 = new CarType(3, "Truck");
        CarType type4 = new CarType(4, "Van");
        CarType type5 = new CarType(5, "Luxury");
        
        // Car(long carid, String description, int maxpassengers, String location, String image,
        //    float dailyprice, float weeklyprice, CarType cartype)
        Car car1 = new Car(1L, "Description 1", 4, "Location10", "image1", 1, 10, type1);
        Car car2 = new Car(2L, "Description 2", 5, "Location10", "image2", 2, 20, type2);
        Car car3 = new Car(3L, "Description 3", 6, "Location10", "image3", 3, 30, type3);
        Car car4 = new Car(4L, "Description 4", 7, "Location10", "image4", 4, 40, type4);
        Car car5 = new Car(5L, "Description 5", 8, "Location10", "image5", 5, 50, type5);
        List<Car> cars = new ArrayList<Car>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        cars.add(car4);
        cars.add(car5);
        
        // create empty list of reservation records for testing
        List<Reservation> rlist = new ArrayList<Reservation>();
        
        
        
        // Initiate a CarService object wit mock repositories
        cs = new CarService(mockCarRepository, mockUserRepository, mockReservationRepository, mockPartnerRepository);

        // this is the stub for PartnerRepository
        given(mockPartnerRepository.findByCompanyid(99L)).willReturn(new ArrayList<Partner>());
        
        // this is the stub for CarRepository
        // When given location = "Location10", it will return a list of cars at location 10
        given(mockCarRepository.findByLocationIgnoreCase("Location10")).willReturn(cars);

        // this is the stub for the ReservationRepository. 
        // When given input carid=1 to 5 , it will return an empty list
        // because we are testing the overlapping dates
        given(mockReservationRepository.findByCarid(1L)).willReturn(rlist);
        given(mockReservationRepository.findByCarid(2L)).willReturn(rlist);
        given(mockReservationRepository.findByCarid(3L)).willReturn(rlist);
        given(mockReservationRepository.findByCarid(4L)).willReturn(rlist);
        given(mockReservationRepository.findByCarid(5L)).willReturn(rlist);
      
        // Create a reservation with only the location, start date and end date specified
        // no overlapping dates
        // create a reservation as input parameter to the bookPartnerReservation
        // Reservation(long reserveid, long carid, Date startdate, Date enddate, long userid,
        // long companyid, float totalprice, String location)
        Reservation reservation = new Reservation();
        reservation.setReserveid(0L);
        reservation.setCompanyid(99L);
        reservation.setStartdate(Date.valueOf("2021-12-01"));
        reservation.setEnddate(Date.valueOf("2021-12-6"));
        reservation.setLocation("Location10");
        
        given(mockReservationRepository.save(reservation)).willReturn(reservation);
        
        String type = "any";
        
        ReserveInfo reserveInfo = null;
        
                
        ResponseEntity<ReserveInfo> testResultResponse = cs.bookPartnerReservation(reservation, type);
        
        ReserveInfo testResult = testResultResponse.getBody();

        ReserveInfo expectedResult = reserveInfo;

        // first verify the http status response code is as expected
        assertThat(testResultResponse.getStatusCodeValue()).isEqualTo(HttpStatus.NOT_FOUND.value());
        
        assertThat(testResult).isEqualTo(expectedResult);
        
    }
}
