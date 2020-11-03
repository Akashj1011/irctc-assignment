package com.example.irctcbooking;

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
public class DAO {

    @PersistenceContext
    private EntityManager em;

    private final String trainListQuery = "select t.trainId, t.trainName, t.startStation, t.endStation, s.departureTime, s.arrivalTime, s.arrivalDayOfWeek from schedule s inner join train t on t.trainId = s.trainId where s.sourceStation='%s' and s.destinationStation='%s' and s.departureDayOfWeek='%s' and s.departureTime >= '%s' and s.departureTime <= '%s'";
    private final String trainListQueryForArrivalDay = "select t.trainId, t.trainName, t.startStation, t.endStation, s.departureTime, s.arrivalTime, s.arrivalDayOfWeek, s.departureDayOfWeek from schedule s inner join train t on t.trainId = s.trainId where s.sourceStation='%s' and s.destinationStation='%s' and s.arrivalDayOfWeek='%s'";

    public List<TrainScheduleInfo> getTrains(String sourceStation, String destStation, int departureDayOfWeek, String timeRange1, String timeRange2, String departureDay) {
        String query = String.format(trainListQuery, sourceStation, destStation, departureDayOfWeek, timeRange1, timeRange2);
        List<Object[]> resultList = em.createNativeQuery(query).getResultList();

        List<TrainScheduleInfo> trainScheduleInfoList = new ArrayList<>();

        for (Object[] obj : resultList) {
            TrainScheduleInfo trainScheduleInfo = new TrainScheduleInfo();
            setTrainScheduleInfo(obj, trainScheduleInfo, sourceStation, destStation);

            trainScheduleInfo.setDepartureDay(departureDay);


            int arrivalDayOfWeek = (Integer) obj[6];
            int diffInDays = 0;
            if (departureDayOfWeek != arrivalDayOfWeek) {
                if (arrivalDayOfWeek < departureDayOfWeek) {
                    diffInDays = 7 - departureDayOfWeek + arrivalDayOfWeek;
                } else {
                    diffInDays = arrivalDayOfWeek - departureDayOfWeek;
                }
            }

            if(diffInDays > 0) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    Calendar c = Calendar.getInstance();
                    Date date = sdf.parse(departureDay);

                    c.setTime(date);
                    c.add(Calendar.DATE, diffInDays);
                    String arrivalDate = sdf.format(c.getTime());
                    trainScheduleInfo.setArrivalDay(arrivalDate);
                } catch (Exception ex) {

                }
            }
            else {
                trainScheduleInfo.setArrivalDay(departureDay);
            }
            trainScheduleInfoList.add(trainScheduleInfo);
        }
        return trainScheduleInfoList;
    }

    public List<TrainScheduleInfo> getTrainsOnArrivalDay(String sourceStation, String destStation, int arrivalDayOfWeek, String arrivalDay) {
        String query = String.format(trainListQueryForArrivalDay, sourceStation, destStation, arrivalDayOfWeek);
        List<Object[]> resultList = em.createNativeQuery(query).getResultList();

        List<TrainScheduleInfo> trainScheduleInfoList = new ArrayList<>();
        for (Object[] obj : resultList) {
            TrainScheduleInfo trainScheduleInfo = new TrainScheduleInfo();
            setTrainScheduleInfo(obj, trainScheduleInfo, sourceStation, destStation);

            trainScheduleInfo.setArrivalDay(arrivalDay);


            int departureDayOfWeek = (Integer) obj[7];
            int diffInDays = 0;
            if (arrivalDayOfWeek != departureDayOfWeek) {
                if (arrivalDayOfWeek < departureDayOfWeek) {
                    diffInDays = 7 - departureDayOfWeek + arrivalDayOfWeek;
                } else {
                    diffInDays = arrivalDayOfWeek - departureDayOfWeek;
                }
            }

            if(diffInDays > 0) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    Calendar c = Calendar.getInstance();
                    Date date = sdf.parse(arrivalDay);

                    c.setTime(date);
                    c.add(Calendar.DATE, (diffInDays * -1));
                    String arrivalDate = sdf.format(c.getTime());
                    trainScheduleInfo.setDepartureDay(arrivalDate);
                } catch (Exception ex) {

                }
            }
            else {
                trainScheduleInfo.setDepartureDay(arrivalDay);
            }
            trainScheduleInfoList.add(trainScheduleInfo);
        }
        return trainScheduleInfoList;

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
