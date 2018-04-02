package com.atc.codes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * This class provides REST end points.
 * 
 * @author sepehr safari
 *
 */
@RestController
public class ATCRestController {

	@Autowired
	QueueLinkedList<AirCraft> atcq;
	
	@Autowired
	ATCRepository database;
	
	@GetMapping("/air-controller/traffic")
	public ResponseEntity<?> listTraffic() {
		List<AirCraft> aircrafts = null;
		Iterator<AirCraft> it = atcq.getIterator(AirCraft.class);
		atcq.getLock().lock();
		try {
			if (it.hasNext()) {
				aircrafts = new ArrayList<AirCraft>();
				while(it.hasNext()) {
					aircrafts.add(it.next());
				}
			}
		} finally {
			atcq.getLock().unlock();
		}
		return new ResponseEntity<>(aircrafts, HttpStatus.OK);
	}
	
	@GetMapping("/air-controller/traffic/first-ac")
	public ResponseEntity<Comparable<AirCraft>> getFirstAircraft() {
		atcq.getLock().lock();
		if (atcq.getHeadData() != null) {
			Comparable<AirCraft> data = atcq.getHeadData();
			return new ResponseEntity<>(data, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/air-controller/traffic/dequeue")
	public ResponseEntity<Comparable<AirCraft>> dqueueFirstAircraft() {
		Comparable<AirCraft> data;
		try {
			data = atcq.dequeue();
			return new ResponseEntity<>(data, HttpStatus.OK);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping("/air-controller/traffic/enqueue")
	public ResponseEntity<?> addAircraft(@RequestBody AirCraft plane) {	
		try {
			atcq.add(plane, AirCraft.class);
			database.save(plane);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
