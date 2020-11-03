# irctc-assignment
  
* Database Definition
There are three tables created:
1. schedule
2. train
3. station
  
Schemas:
  
CREATE TABLE irctc.schedule (  
  scheduleId varchar(120) not null,  
  journeyId varchar(120) not null,  
  trainId varchar(120) not null,  
  sourceStation varchar(120) NOT NULL,  
  destinationStation varchar(120) NOT NULL,  
  departureTime time not null,  
  arrivalTime time not null,  
  timeTaken int not null,  
  departureDayOfWeek int not null,  
  arrivalDayOfWeek int not null,    
  PRIMARY KEY (scheduleId). 
);  
    
  This table basically stores all the information of all the trains from given source to destination.  
    
  scheduleId: Primary Key. 
  journeyId: Suppose the train is from Kolkata to Ahmedabad. So the stops in between are Kolkata-->Patna-->Lucknow-->Indore-->Delhi-->Ahmedabad. So for all the       schedule eq: from Kolkata-->Indore or Delhi-->Ahmedabad the journeyId will be same. It can be used to know all the schedules for a given journey. It can also be   used when the train is delayed and the timings for all the subsequent departures and arrrivals needed to be changed.
  trainId : Foreign key from train table. It gives information about the train.
  sourceStation: station from where passenger needs to depart.  
  destinationStation : station from where passenger needs to arrive.  
  departureTime : Time when trains comes at source station.  
  arrivalTime : Time when trains arrives at destination station.  
  timeTaken : Time taken from source to destination station.  
  departureDayOfWeek: As we know, most of the trains are running on weekly basis like weekly once or twice. So instead of saving any static timestamp which needs     to be changed every week what we can do is we can store the day of the week when train will be departing at source station given by passenger. So when passanger   gives date for checking trains availability we can get the day of week from the date and check in the table if the train is scheduled for that day of week or       not.  
  arrivalDayOfWeek : Similar to departureDayOfWeek. Value ranges from [1..7] i.e from Sunday to Saturday. 


CREATE TABLE irctc.train (  
  trainId varchar(120) not null,  
  trainName varchar(120) not null,  
  startStation varchar(120) NOT NULL,  
  endStation varchar(120) NOT NULL,  
  PRIMARY KEY (trainId). 
);  
    
This tables is used to save information regarding train.  
  trainId : Primary Key
  trainName : Name of Train. 
  startStation : Start Station of Train. 
  endStation : End Station of Train. 
  
  
CREATE TABLE irctc.station (  
  stationId varchar(120) not null,  
  stationName varchar(120) not null,  
  PRIMARY KEY (stationId)  
);  
This table saves information of stations.  
  
INSERT INTO irctc.schedule( scheduleId, trainId, sourceStation, destinationStation, departureTime, arrivalTime,timeTaken, departureDayOfWeek, arrivalDayOfWeek, journeyId) VALUES   
( 's1', 't1', 'Ahmedabad', 'Bangalore', '12:00', '18:00', 6, 1,1, 'jrn1'),  
( 's2', 't2', 'Srinager', 'Chennai', '12:00', '18:00', 54, 6,1, 'jrn2'),  
( 's3', 't3', 'Lucknow', 'Kolkata', '12:00', '12:00', 24, 6, 7, 'jrn3');  
    
 select * from irctc.schedule;  
   
 Insert INTO irctc.train(trainId, trainName, startStation, endStation) Values  
('t1', 'AbadToBlr', 'Jaipur', 'Cochi'),  
('t2', 'KashmirToCochi', 'Kashmir', 'Cochi'),  
('t3', 'DelhiToKolkta', 'Delhi', 'Kolkata');  
  
select * from irctc.train;  
  
CREATE TABLE irctc.station (  
stationId varchar(120) not null,  
stationName varchar(120) not null,  
PRIMARY KEY (stationId). 
);  
  
Insert INTO irctc.station(stationId, stationName) values  
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
  



































