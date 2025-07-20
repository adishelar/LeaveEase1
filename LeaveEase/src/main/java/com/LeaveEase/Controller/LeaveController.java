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

   package com.LeaveEase.Controller;

import com.LeaveEase.Entity.Leave;
import com.LeaveEase.Service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/leave")
public class LeaveController {

    @Autowired
    private LeaveService service;

    @PostMapping("/apply")
    public ResponseEntity<Leave> apply(@RequestBody Leave leave){
        try {
            Leave saved=service.applyLeave(leave);
             return new ResponseEntity<>(saved, HttpStatus.CREATED);
          } catch (Exception e){
              return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
     }
 @GetMapping("/recent")
    public ResponseEntity<List<Leave>> getRecent(){
         List<Leave> list=service.getRecentRequests();
        if(list.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
         return new ResponseEntity<>(list, HttpStatus.OK);
    }
  @GetMapping("/pending")
     public ResponseEntity<List<Leave>> getPending(){
         List<Leave> list=service.getPendingRequests();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
 @PutMapping("/approve/{id}")
    public ResponseEntity<String> approve(@PathVariable Long id){
        try{
            Leave updated=service.approveLeave(id);
            return new ResponseEntity<>("Leave approved for"+updated.getEmployeeName(),HttpStatus.ACCEPTED);
        } catch (NoSuchElementException e){
         return new ResponseEntity<>("Leave not found",HttpStatus.NOT_FOUND);
         } catch (Exception ex){
             return new ResponseEntity<>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
         }
  }


}
