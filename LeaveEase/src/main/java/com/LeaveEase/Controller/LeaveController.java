package com.LeaveEase.Controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.LeaveEase.Entity.Leave;
import com.LeaveEase.Service.LeaveService;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

	@Autowired
    private LeaveService service;

    @PostMapping("/apply")
    public ResponseEntity<Leave> applyLeave(@RequestBody Leave leave){
          Leave saved = service.applyLeave(leave);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
     }

    
    @GetMapping("/all")
    public ResponseEntity<List<Leave>> getAll(){
        return ResponseEntity.ok(service.getAllSortedByName());
   }

    @GetMapping("/recent")
    public ResponseEntity<List<Leave>> getRecentRequests(){
        return ResponseEntity.ok(service.getRecentRequests());
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Leave>> getPendingRequests(){
        return ResponseEntity.ok(service.getPendingRequests());
   }

    
    @GetMapping("/approved")
    public ResponseEntity<List<Leave>> getApprovedRequests(){
        return ResponseEntity.ok(service.getApprovedRequests());
     }

   
    @PutMapping("/approve/{id}")
    public ResponseEntity<?> approveLeave(@PathVariable Long id){
        try {
            Leave updatedLeave =service.approveLeave(id); // Handles status change + async email
            return ResponseEntity.ok(updatedLeave);
        }catch(NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Leave with ID "+id+" not found");
        }
    }


	@GetMapping("/triggerTask")
    public ResponseEntity<String> triggerBackgroundTask(){
        service.backgroundTask();
        return ResponseEntity.ok("Background Task Started");
    }
}
