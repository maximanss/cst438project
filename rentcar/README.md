# Car Rental Documentation 
### Version 06.15.2021

This application was created to allow a user to enter a date and location to book a car rental after logging into the application. A user is able to register by navigating to "register" on the nav bar. After their information is entered, a user is then able to login and select a date and location to rent a car. A car selection can be made, and finally a summary will be displayed of their reservation. 


# REST API
```
https://maha20-car-rental.herokuapp.com/
```

### GET /book
This allows to book a reservation with the company id, start and end date of the rental, and the location of where it will be used. Cartype is defaulted to a two seated vehicle minimum (standard). Please use cartype=${cartype} for specific models. The available ones are luxury, suv, truck, and van.
```
https://maha20-car-rental.herokuapp.com/api/book?companyid=${id}&startdate=yyyy-mm-dd&enddate=yyyy-mm-dd&location=${location}
```

```

{
    "reservation_id": 35,
    "car_type": "Standard",
    "description": "Volkswagen Jetta, Toyota Corolla or similar. Features: Cruise Control, AM/FM Stereo Radio, Automatic, \nAir Conditioning, and Bluetooth.",
    "total_price": 561.75
}
```

### DELETE /cancel
This allows the deletion of a reservation with the company id and reservation id. 

```
https://maha20-car-rental.herokuapp.com/api/cancel?companyid=1&reservationid=35
```

# Database Design

![Database Model](https://github.com/maximanss/cst438project/blob/master/rentcar/src/main/resources/static/images/db_model.png?raw=true)

# Class Diagrams 
## Domain, Service, Controller

![Domain Class Diagram](https://github.com/maximanss/cst438project/blob/master/rentcar/src/main/resources/static/images/domain.png?raw=true)

![Service Class Diagram](https://github.com/maximanss/cst438project/blob/master/rentcar/src/main/resources/static/images/service.png?raw=true)

![Controller Class Diagram](https://github.com/maximanss/cst438project/blob/master/rentcar/src/main/resources/static/images/controller.png?raw=true)
