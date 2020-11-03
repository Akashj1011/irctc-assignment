package com.example.irctcbooking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // RESTful API method for
    @GetMapping("/getTrains")
    public ResponseEntity<?> getTrainsForTimeRange(@RequestParam String sourceStation,
                                 @RequestParam String destinationStation,
                                 @RequestParam String departureDay,
                                 @RequestParam String timeRange1,
                                 @RequestParam String timeRange2) {

        List<TrainScheduleInfo> result = bookingService.getTrains(sourceStation, destinationStation, departureDay, timeRange1, timeRange2);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // RESTful API method for
    @GetMapping("/getTrainsForDest")
    public ResponseEntity<?> getTrainsForDestDay(@RequestParam String sourceStation,
                                 @RequestParam String destinationStation,
                                 @RequestParam String arrivalDay
                                 ) {
        List<TrainScheduleInfo> result = bookingService.getTrainsOnArrivalDay(sourceStation, destinationStation, arrivalDay);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
