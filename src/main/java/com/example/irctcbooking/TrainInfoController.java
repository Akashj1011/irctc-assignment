package com.example.irctcbooking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TrainInfoController {

    @Autowired
    private TrainInfoService trainInfoService;

    // RESTful API method for getting all trains available on given departure day and between particular time range.
    @GetMapping("/trainsOnDepartureDay")
    public ResponseEntity<?> getTrainsForTimeRange(@RequestParam String sourceStation,
                                 @RequestParam String destinationStation,
                                 @RequestParam String departureDay,
                                 @RequestParam String timeRange1,
                                 @RequestParam String timeRange2) {

        List<TrainScheduleInfo> result = trainInfoService.getTrains(sourceStation, destinationStation, departureDay, timeRange1, timeRange2);
        if(result == null) {
            String responseString = "Invalid Parameters passed";
            return new ResponseEntity<>(responseString, HttpStatus.BAD_REQUEST);
        }
        if(result.size() == 0) {
            String responseString = "No trains available";
            return new ResponseEntity<>(responseString, HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // RESTful API method for getting all trains available to reach on desired arrival day.
    @GetMapping("/trainsOnArrivalDay")
    public ResponseEntity<?> getTrainsForDestDay(@RequestParam String sourceStation,
                                 @RequestParam String destinationStation,
                                 @RequestParam String arrivalDay
                                 ) {
        List<TrainScheduleInfo> result = trainInfoService.getTrainsOnArrivalDay(sourceStation, destinationStation, arrivalDay);
        if(result == null) {
            String responseString = "Invalid Parameters passed";
            return new ResponseEntity<>(responseString, HttpStatus.BAD_REQUEST);
        }
        if(result.size() == 0) {
            String responseString = "No trains available";
            return new ResponseEntity<>(responseString, HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // RESTful API method for getting all the Station Names
    @GetMapping("/stations")
    public ResponseEntity<?> getStations() {
        List<String> result = trainInfoService.getStations();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
