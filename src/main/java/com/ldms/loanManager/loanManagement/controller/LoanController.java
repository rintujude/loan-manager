package com.ldms.loanManager.loanManagement.controller;

import com.ldms.loanManager.exception.EnquiryNotFoundException;
import com.ldms.loanManager.loanManagement.model.Enquiry;
import com.ldms.loanManager.loanManagement.model.EnquiryRequest;
import com.ldms.loanManager.loanManagement.service.LoanService;
import jakarta.validation.Valid;
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
        logger.info("Enquiry Generated Successfully");
        return new ResponseEntity<>(loanService.generateSchedule(enquiry), HttpStatus.CREATED) ;
    }

    @GetMapping("/enquiry/all")
    public ResponseEntity<List<Enquiry>> generateSchedule() throws EnquiryNotFoundException {
        return ResponseEntity.ok(loanService.fetchAllGeneratedSchedules());
    }

    @GetMapping("/enquiry/{id}")
    public ResponseEntity<Enquiry> generateScheduleDetails(@PathVariable Long id) throws EnquiryNotFoundException {
        Enquiry enquiry = loanService.getSchedule(id);
        logger.info("enquiry details fetched successfully with the id: {}",id);
        return ResponseEntity.ok(enquiry);
    }

    @DeleteMapping("/enquiry/{id}")
    public boolean deleteEnquiry(@PathVariable Long id) {
        loanService.deleteScheduleById(id);
        return true;
    }
}
