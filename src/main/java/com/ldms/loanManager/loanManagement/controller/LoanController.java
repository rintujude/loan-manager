package com.ldms.loanManager.loanManagement.controller;

import com.ldms.loanManager.exception.EnquiryNotFoundException;
import com.ldms.loanManager.loanManagement.model.Enquiry;
import com.ldms.loanManager.loanManagement.model.EnquiryRequest;
import com.ldms.loanManager.loanManagement.model.Schedule;
import com.ldms.loanManager.loanManagement.service.LoanService;
import com.ldms.loanManager.utils.CommonUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loan")
public class LoanController {

    private final static Logger logger = LogManager.getLogger(LoanController.class);
    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/generate-schedule")
    public ResponseEntity<Enquiry> generateSchedule(@RequestBody @Valid EnquiryRequest enquiryRequest){
        Enquiry enquiry = new Enquiry(enquiryRequest.getAssetCost(),enquiryRequest.getDeposit(),enquiryRequest.getInterestRate(),enquiryRequest.getNumberOfPayments(),enquiryRequest.getBalloonPayment());
        return new ResponseEntity<>(loanService.generateSchedule(enquiry), HttpStatus.CREATED) ;
    }

    @GetMapping("/enquiry/all")
    public ResponseEntity<List<Enquiry>> generateSchedule() throws EnquiryNotFoundException {
        return ResponseEntity.ok(loanService.fetchAllGeneratedSchedules());
    }

    @GetMapping("/enquiry/{id}")
    public ResponseEntity<Enquiry> generateScheduleDetails(@PathVariable Long id) throws EnquiryNotFoundException {
        return ResponseEntity.ok(loanService.getSchedule(id));
    }

    @DeleteMapping("/enquiry/{id}")
    public boolean deleteEnquiry(@PathVariable Long id) throws EnquiryNotFoundException {
        loanService.deleteScheduleById(id);
        return true;
    }
}
