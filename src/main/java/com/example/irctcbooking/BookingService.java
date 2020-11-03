package com.example.irctcbooking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private DAO bookingDao;

    public List<TrainScheduleInfo> getTrains(String sourceStation, String destStation, String departureDay, String timeRange1, String timeRange2) {
        validateDate(departureDay);
        validateTime(timeRange1, timeRange2);
        int weekOfDay = getWeekOfDay(departureDay);
        return bookingDao.getTrains(sourceStation, destStation, weekOfDay, timeRange1, timeRange2, departureDay);

    }

    public List<TrainScheduleInfo> getTrainsOnArrivalDay(String sourceStation, String destStation, String arrivalDay) {
        validateDate(arrivalDay);
        int weekOfDay = getWeekOfDay(arrivalDay);
        return bookingDao.getTrainsOnArrivalDay(sourceStation, destStation, weekOfDay, arrivalDay);
    }

    public boolean validateDate(String departureDay) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            simpleDateFormat.parse(departureDay);

        } catch (Exception ex) {
            return false;
        }

        return true;
    }

    public boolean validateTime(String timeRange1, String timeRange2) {
        try {

            String strDateFormat = "HH:mm";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strDateFormat);
            simpleDateFormat.parse(timeRange1);
            simpleDateFormat.parse(timeRange2);

        } catch (Exception ex) {
            return false;
        }

        return true;

    }

    public int getWeekOfDay(String day) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = simpleDateFormat.parse(day);
            Calendar cal  = Calendar.getInstance();
            cal.setTime(date);
            return cal.get(Calendar.DAY_OF_WEEK);
        }
        catch(Exception ex) {

        }
        return -1;
    }
 }
