  
# Database Definition
There are three tables created for database irctc:
1. schedule
2. train
3. station
  
- Schemas:
  
- CREATE TABLE irctc.schedule (  
  scheduleId varchar(60) not null,  
  journeyId varchar(60) not null,  
  trainId varchar(60) not null,  
  sourceStation varchar(60) NOT NULL,  
  destinationStation varchar(60) NOT NULL,  
  departureTime time not null,  
  arrivalTime time not null,  
  timeTaken int not null,  
  departureDayOfWeek int not null,  
  arrivalDayOfWeek int not null,    
  PRIMARY KEY (scheduleId). 
);  
    
 - This table basically stores all the information of all the trains from given source to destination.  
      
    - scheduleId: Primary Key.    
    - journeyId: Suppose the train is from Kolkata to Ahmedabad. So the stops in between are Kolkata-->Patna-->Lucknow-->Indore-->Delhi-->Ahmedabad. So for all the     - schedule eq: from Kolkata-->Indore or Delhi-->Ahmedabad the journeyId will be same. It can be used to know all the schedules for a given journey. It can also be used when the train is delayed and the timings for all the subsequent departures and arrrivals needed to be changed.   
    - trainId : Foreign key from train table. It gives information about the train.   
    - sourceStation: station from where passenger needs to depart.    
    - destinationStation : station from where passenger needs to arrive.    
    - departureTime : Time when trains comes at source station.    
    - arrivalTime : Time when trains arrives at destination station.  
    - timeTaken : Time taken from source to destination station.  
    - departureDayOfWeek: As we know, most of the trains are running on weekly basis like weekly once or twice. So instead of saving any static timestamp which needs to be changed every week what we can do is we can store the day of the week when train will be departing at source station given by passenger. So when passanger gives date for checking trains availability we can get the day of week from the date and check in the table if the train is scheduled for that day of week or not.  
    - arrivalDayOfWeek : Similar to departureDayOfWeek. Value ranges from [1..7] i.e from Sunday to Saturday. 


- CREATE TABLE irctc.train (  
  trainId varchar(60) not null,  
  trainName varchar(60) not null,  
  startStation varchar(60) NOT NULL,  
  endStation varchar(60) NOT NULL,  
  PRIMARY KEY (trainId)  
);  
      
- This tables is used to save information regarding train.  
  - trainId : Primary Key
  - trainName : Name of Train. 
  - startStation : Start Station of Train. 
  - endStation : End Station of Train. 
  
  
- CREATE TABLE irctc.station (  
  stationId varchar(60) not null,  
  stationName varchar(60) not null,  
  PRIMARY KEY (stationId)  
);  
This table saves information of stations.  
  
- INSERT INTO irctc.schedule( scheduleId, trainId, sourceStation, destinationStation, departureTime, arrivalTime,timeTaken, departureDayOfWeek, arrivalDayOfWeek, journeyId) VALUES   
( 's1', 't1', 'Ahmedabad', 'Bangalore', '12:00', '18:00', 6, 1,1, 'jrn1'),  
( 's2', 't2', 'Srinager', 'Chennai', '12:00', '18:00', 54, 6,1, 'jrn2'),  
( 's3', 't3', 'Lucknow', 'Kolkata', '12:00', '12:00', 24, 6, 7, 'jrn3'),  
( 's4', 't4', 'Srinager', 'Chennai', '14:00', '18:00', 52, 6,1, 'jrn4');     
    
 - select * from irctc.schedule;  
   
 - Insert INTO irctc.train(trainId, trainName, startStation, endStation) Values  
('t1', 'AbadToBlr', 'Jaipur', 'Cochi'),  
('t2', 'KashmirToCochi', 'Kashmir', 'Cochi'),  
('t3', 'DelhiToKolkta', 'Delhi', 'Kolkata'),  
('t4', 'SrinagerToTrivandrum', 'Srinager', 'Trivandrum');  
  
- select * from irctc.train;  
  
- CREATE TABLE irctc.station (  
stationId varchar(120) not null,  
stationName varchar(120) not null,  
PRIMARY KEY (stationId). 
);  
  
- Insert INTO irctc.station(stationId, stationName) values  
('stn1', 'Kolkata'),   
('stn2', 'Mumbai'),  
('stn3', 'Ahmedabad'),  
('stn4', 'Cochi'),  
('stn5', 'Delhi'),  
('stn6', 'Jaipur'),  
('stn7', 'Kashmir'),  
('stn8', 'Lucknow'),  
('stn9', 'Chennai'),  
('stn10', 'Bangalore'),  
('stn11', 'Srinagar');  
    
# Rest Apis. 
- There are three Rest Apis as per the requirements:  
1. trainsOnDepartureDay  
2. trainsOnArrivalDay  
3. stations  
  
- TrainsOnDepartureDay : RESTful API method for getting all trains available on given departure day and between particular time range.  
  Input Params are : sourceStation,destinationStation,departureDay,timeRange1,timeRange2.  
  timeRange1 and timeRange2 are used to select time range of day between which user needs to book the train.  
  Eg:  
  sourceStation : Srinager  
  destinationStation : Chennai  
  departureDay : 2020-11-07  
  timeRange1 : 10:00  
  timeRange2 : 16:00  
    
  The format of departurre day is YYYY-MM-dd and timeRange1, timeRange2 is hh:mm
  
- TrainsOnArrivalDay : RESTful API method for getting all trains available to reach on desired arrival day.  
  Input Params are : sourceStation,destinationStation,arrivalDay
    Eg:     
    sourceStation : Lucknow    
    destinationStation : Kolkata    
    departureDay : 2020-11-08   
   
- stations : RESTful API method for getting all the Station Names. 

<img width="1146" alt="Screenshot 2020-11-04 at 2 43 06 AM" src="https://user-images.githubusercontent.com/21036288/98041849-9c148080-1e48-11eb-8d3e-d12fc3c255ec.png">
<img width="1134" alt="Screenshot 2020-11-04 at 2 43 23 AM" src="https://user-images.githubusercontent.com/21036288/98041861-a0409e00-1e48-11eb-9e7c-90a627eb700d.png">  
<img width="1075" alt="Screenshot 2020-11-04 at 10 47 32 AM" src="https://user-images.githubusercontent.com/21036288/98072083-33022c80-1e8b-11eb-9641-c007cd4e9a10.png">
<img width="1091" alt="Screenshot 2020-11-04 at 2 43 34 AM" src="https://user-images.githubusercontent.com/21036288/98041864-a0d93480-1e48-11eb-91e0-64eb3d3d9393.png">
<img width="1093" alt="Screenshot 2020-11-04 at 2 43 47 AM" src="https://user-images.githubusercontent.com/21036288/98041866-a171cb00-1e48-11eb-8b42-029bdbf45251.png">
<img width="1090" alt="Screenshot 2020-11-04 at 2 44 05 AM" src="https://user-images.githubusercontent.com/21036288/98041867-a20a6180-1e48-11eb-9c2a-693e96b77ad5.png">
<img width="1081" alt="Screenshot 2020-11-04 at 2 44 55 AM" src="https://user-images.githubusercontent.com/21036288/98041869-a2a2f800-1e48-11eb-862e-50c170ff5ed0.png">


# Code Structure
- All the files are in src/main/java/com/example/irctcbooking
- Controller: TrainInfoController: Contains the rest endpoints
- Service: TrainInfoService
- Dao: TrainInfoDao: Communicates with Database
- Util: Util.java: Have common methods
- POJO: TrainScheduleInfo: POJO which has result object for trainInfo. 
- resources/application.properties : Have all the database properties.

# Code Flow:
- With the input parameters We will validate the date and time if given params in proper format or not.  
- Then Using the date given in input parrams for getting trains, we are getting the day of week and with day of week, and other parameters we are querying the database and creating the result object.

# Database Snapshots. 

<img width="1419" alt="Screenshot 2020-11-04 at 10 42 06 AM" src="https://user-images.githubusercontent.com/21036288/98071779-7b6d1a80-1e8a-11eb-81fe-48b91ea82034.png">
<img width="1423" alt="Screenshot 2020-11-04 at 10 42 18 AM" src="https://user-images.githubusercontent.com/21036288/98071784-8162fb80-1e8a-11eb-87a0-66cc982f3342.png">
   
   
  
































