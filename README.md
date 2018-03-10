# ATC
--------
Build as Spring boot project.
Download codes and build as:   mvn clean install
To run the application use:    java -jar AirTrafficController-0.0.1-SNAPSHOT-spring-boot.jar

Open browser and start:  localhost:8080/
Providing very simple UI for testing the application. This will start the AirTrafficController 
main process and displays the main menu.



# Data Structures:
-------------------

QueueLinkedList -- Main structure to build and hold the aircraft queue. Provides looking to 
                   access the queue for avoiding race-conditions with adding and removing.

Aircrafts -- Class containing an airplane flight information.

AirTrafficController -- Class providing API Model View Controller to the main menu.

ATCRestController -- Class providing REST endpoints for Rest APIs to be called by 3rd parties.



# Database:
------------
It's in memory database for demonstating JPA repository usage. This can be change easily to support SQL or
no-AQL databases. 



