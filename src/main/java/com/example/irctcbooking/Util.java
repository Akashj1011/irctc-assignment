package com.example.irctcbooking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.slf4j.LoggerFactory;

@Component
public class Util {
    private static final Logger logger = LoggerFactory.getLogger(Util.class);

    // Check if date is in proper format
    public boolean validateDate(String departureDay) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            simpleDateFormat.parse(departureDay);

        } catch (Exception ex) {
            return false;
        }

        return true;
    }

    // Check if time is in proper format
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

    // Get the day of week from the Date. Ranges from [1..7] Starting fromm Sunday to Saturday
    public int getDayOfWeek(String day) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = simpleDateFormat.parse(day);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal.get(Calendar.DAY_OF_WEEK);
        } catch (Exception ex) {

        }
        return -1;
    }

    // Get the diff in no of days between departure and Arrival Date.
    public int getDiffOfDaysBetnDepartureAndArrival(int arrivalDayOfWeek, int departureDayOfWeek) {
        int diffInDays = 0;
        if (arrivalDayOfWeek != departureDayOfWeek) {
            if (arrivalDayOfWeek < departureDayOfWeek) {
                diffInDays = 7 - departureDayOfWeek + arrivalDayOfWeek;
            } else {
                diffInDays = arrivalDayOfWeek - departureDayOfWeek;
            }
        }
        return diffInDays;
    }
}
