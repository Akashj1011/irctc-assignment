package com.example.irctcbooking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class TrainInfoDao {

    @Autowired
    private Util util;

    @PersistenceContext
    private EntityManager em;
    private static final Logger logger = LoggerFactory.getLogger(TrainInfoDao.class);


    private final String trainListQuery = "select t.trainId, t.trainName, t.startStation, t.endStation, s.departureTime, s.arrivalTime, s.arrivalDayOfWeek from schedule s inner join train t on t.trainId = s.trainId where s.sourceStation='%s' and s.destinationStation='%s' and s.departureDayOfWeek='%s' and s.departureTime >= '%s' and s.departureTime <= '%s'";
    private final String trainListQueryForArrivalDay = "select t.trainId, t.trainName, t.startStation, t.endStation, s.departureTime, s.arrivalTime, s.arrivalDayOfWeek, s.departureDayOfWeek from schedule s inner join train t on t.trainId = s.trainId where s.sourceStation='%s' and s.destinationStation='%s' and s.arrivalDayOfWeek='%s'";
    private final String stationListQuery = "select distinct(stationName) from station";

    // Get trains available on given departure day and between particular time range for specific source and destinatopn station.
    public List<TrainScheduleInfo> getTrains(String sourceStation, String destStation, int departureDayOfWeek, String timeRange1, String timeRange2, String departureDay) {
        String query = String.format(trainListQuery, sourceStation, destStation, departureDayOfWeek, timeRange1, timeRange2);
        List<Object[]> resultList = em.createNativeQuery(query).getResultList();

        List<TrainScheduleInfo> trainScheduleInfoList = new ArrayList<>();

        for (Object[] obj : resultList) {
            TrainScheduleInfo trainScheduleInfo = new TrainScheduleInfo();
            setTrainScheduleInfo(obj, trainScheduleInfo, sourceStation, destStation);

            trainScheduleInfo.setDepartureDay(departureDay);

            int arrivalDayOfWeek = (Integer) obj[6];
            int diffInDays = util.getDiffOfDaysBetnDepartureAndArrival(arrivalDayOfWeek, departureDayOfWeek);

            if (diffInDays > 0) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar c = Calendar.getInstance();
                    Date date = sdf.parse(departureDay);
                    c.setTime(date);
                    c.add(Calendar.DATE, diffInDays);
                    String arrivalDate = sdf.format(c.getTime());
                    trainScheduleInfo.setArrivalDay(arrivalDate);
                } catch (Exception ex) {
                    logger.error("Exception while setting Arrival Date.... {}", ex);
                }
            } else {
                trainScheduleInfo.setArrivalDay(departureDay);
            }
            trainScheduleInfoList.add(trainScheduleInfo);
        }
        return trainScheduleInfoList;
    }

    // Get all trains available to reach on desired arrival day for specified source and destination station.
    public List<TrainScheduleInfo> getTrainsOnArrivalDay(String sourceStation, String destStation, int arrivalDayOfWeek, String arrivalDay) {
        String query = String.format(trainListQueryForArrivalDay, sourceStation, destStation, arrivalDayOfWeek);
        List<Object[]> resultList = em.createNativeQuery(query).getResultList();

        List<TrainScheduleInfo> trainScheduleInfoList = new ArrayList<>();
        for (Object[] obj : resultList) {
            TrainScheduleInfo trainScheduleInfo = new TrainScheduleInfo();
            setTrainScheduleInfo(obj, trainScheduleInfo, sourceStation, destStation);

            trainScheduleInfo.setArrivalDay(arrivalDay);

            int departureDayOfWeek = (Integer) obj[7];
            int diffInDays = util.getDiffOfDaysBetnDepartureAndArrival(arrivalDayOfWeek, departureDayOfWeek);

            if (diffInDays > 0) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar c = Calendar.getInstance();
                    Date date = sdf.parse(arrivalDay);
                    c.setTime(date);
                    c.add(Calendar.DATE, (diffInDays * -1));
                    String arrivalDate = sdf.format(c.getTime());
                    trainScheduleInfo.setDepartureDay(arrivalDate);
                } catch (Exception ex) {
                    logger.error("Exception while setting Departure Date.... {}", ex);
                }
            } else {
                trainScheduleInfo.setDepartureDay(arrivalDay);
            }
            trainScheduleInfoList.add(trainScheduleInfo);
        }
        return trainScheduleInfoList;

    }

    // Get the list of all the stations
    public List<String> getStations() {
        List<String> resultList = em.createNativeQuery(stationListQuery).getResultList();
        List<String> stationList = new ArrayList<>();
        for (String station : resultList) {
            stationList.add(station);
        }
        return stationList;
    }

    public void setTrainScheduleInfo(Object[] obj, TrainScheduleInfo trainScheduleInfo, String sourceStation, String destStation) {
        trainScheduleInfo.setTrainId((String) obj[0]);
        trainScheduleInfo.setTrainName((String) obj[1]);
        trainScheduleInfo.setStartStation((String) obj[2]);
        trainScheduleInfo.setEndStation((String) obj[3]);
        trainScheduleInfo.setDepartureTime(((Time) obj[4]).toString());
        trainScheduleInfo.setArrivalTime(((Time) obj[5]).toString());
        trainScheduleInfo.setDepartureStation(sourceStation);
        trainScheduleInfo.setArrivalStation(destStation);
    }


}
