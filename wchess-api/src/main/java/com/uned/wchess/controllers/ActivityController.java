package com.uned.wchess.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.uned.wchess.models.Activity;

@RestController
public class ActivityController {
	
	@RequestMapping(path="/activity/{activityId}", method=RequestMethod.GET)
	public ResponseEntity<Activity> get(@PathVariable long activityId){
		Activity activity = new Activity();
		
		return ResponseEntity.ok(activity);
	}
	
	@RequestMapping(path="/activity/", method=RequestMethod.GET)
	public ResponseEntity<Activity> getAll(){
		Activity activity = new Activity();
		
		return ResponseEntity.ok(activity);
	}
	
	@RequestMapping(path="/activity/", method=RequestMethod.POST)
	public ResponseEntity<Activity> save(@RequestBody Activity activity){
		Activity createdActivity = new Activity();
		
		return ResponseEntity.ok(createdActivity);
	}
	
	@RequestMapping(path="/activity/{activityId}", method=RequestMethod.PUT)
	public ResponseEntity<Activity> update(@RequestBody Activity activity, @PathVariable long activityId){
		Activity createdActivity = new Activity();
		
		return ResponseEntity.ok(createdActivity);
	}
	
	@RequestMapping(path="/activity/{activityId}", method=RequestMethod.DELETE)
	public ResponseEntity<Activity> delete(@PathVariable long activityId){
		Activity createdActivity = new Activity();
		
		return ResponseEntity.ok(createdActivity);
	}
}