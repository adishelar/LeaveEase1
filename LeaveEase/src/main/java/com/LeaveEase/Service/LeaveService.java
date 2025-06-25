package com.LeaveEase.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.LeaveEase.Entity.Leave;
import com.LeaveEase.Repo.LeaveRepository;

@Service
public class LeaveService {

	@Autowired
    private LeaveRepository repo;

    public synchronized Leave applyLeave(Leave leave){
        leave.setStatus("Pending");
        return repo.save(leave);
     }

    public List<Leave> getAllSortedByName(){
        List<Leave> leaves = repo.findAll();
        leaves.sort(Comparator.comparing(Leave::getEmployeeName));
        return leaves;
   }

    public List<Leave> getRecentRequests(){
        return repo.findTop5ByOrderByIdDesc();
     }

    public List<Leave> getPendingRequests(){
        return repo.findByStatus("Pending");
    }

    public List<Leave> getApprovedRequests(){
        return repo.findByStatus("Approved");
    }

    public Leave approveLeave(Long id){
        Leave leave = repo.findById(id).orElseThrow(()->new NoSuchElementException("Leave not found"));
       leave.setStatus("Approved");
         Leave updated = repo.save(leave);
          sendApprovalEmailAsync(leave);
        return updated;
     }

    public void backgroundTask(){
        Runnable task = ()->{
            List<Leave> pending = repo.findByStatus("Pending");
             System.out.println("Pending Requests Count: " + pending.size());
       };
        new Thread(task).start();
   }

    private void sendApprovalEmailAsync(Leave leave){
        Runnable emailTask = () -> {
            String msg = "Hello " + leave.getEmployeeName() + ", your leave from " +
                         leave.getFromDate() + " to " + leave.getToDate() + " has been approved.";
            System.out.println("Sending Email to " + leave.getEmail() + ": " + msg);
            try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
            System.out.println("Email sent successfully to " + leave.getEmail());
        };
        new Thread(emailTask).start();
    }

	
}
