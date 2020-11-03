package com.example.irctcbooking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainInfoService {

    @Autowired
    private TrainInfoDao bookingTrainInfoDao;
    @Autowired
    private Util util;
    @Autowired
    private StationRespository stationRespository;

    private static final Logger logger = LoggerFactory.getLogger(TrainInfoService.class);


    public List<TrainScheduleInfo> getTrains(String sourceStation, String destStation, String departureDay, String timeRange1, String timeRange2) {
        if (!util.validateDate(departureDay)) {
            return null;
        }
        if (!util.validateTime(timeRange1, timeRange2)) {
            return null;
        }
        int dayOfWeek = util.getDayOfWeek(departureDay);
        return bookingTrainInfoDao.getTrains(sourceStation, destStation, dayOfWeek, timeRange1, timeRange2, departureDay);

    }

    public List<TrainScheduleInfo> getTrainsOnArrivalDay(String sourceStation, String destStation, String arrivalDay) {
        if(!util.validateDate(arrivalDay)) {
            return null;
        }
        int dayOfWeek = util.getDayOfWeek(arrivalDay);
        return bookingTrainInfoDao.getTrainsOnArrivalDay(sourceStation, destStation, dayOfWeek, arrivalDay);
    }

    public List<String> getStations() {
        return bookingTrainInfoDao.getStations();
    }


 }
