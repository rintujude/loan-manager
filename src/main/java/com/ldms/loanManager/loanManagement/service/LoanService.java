package com.ldms.loanManager.loanManagement.service;

import com.ldms.loanManager.exception.EnquiryNotFoundException;
import com.ldms.loanManager.loanManagement.model.Amortisation;
import com.ldms.loanManager.loanManagement.model.Enquiry;
import com.ldms.loanManager.loanManagement.model.Schedule;
import com.ldms.loanManager.loanManagement.repository.EnquiryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

@Service
public class LoanService {

    private final EnquiryRepository enquiryRepository;
    private final static Logger logger = LogManager.getLogger(LoanService.class);

    @Autowired
    public LoanService(EnquiryRepository enquiryRepository) {
        this.enquiryRepository = enquiryRepository;
    }


    public Enquiry generateSchedule(Enquiry enquiry) {
        try {
            double monthlyInterestRate =  round((enquiry.getInterestRate() / 100) / 12.0,5);
            double totalLoanAmount = enquiry.getAssetCost() - enquiry.getDeposit();

            double monthlyRepayment;
            if (enquiry.getBalloonPayment() > 0) {
                monthlyRepayment = calculateMonthlyRepaymentWithBalloon(totalLoanAmount, enquiry.getBalloonPayment(), monthlyInterestRate, enquiry.getNumberOfPayments());
            } else {
                monthlyRepayment = calculateMonthlyRepaymentWithoutBalloon(totalLoanAmount, monthlyInterestRate, enquiry.getNumberOfPayments());
            }
            System.out.println("monthlyRepayment: "+monthlyRepayment);
            Amortisation amortisation = new Amortisation();
            double balance = totalLoanAmount;
            double totalInterest = 0;
            for (int i = 1; i <= enquiry.getNumberOfPayments(); i++) {
                double interest = balance * monthlyInterestRate;
                double principal = monthlyRepayment - interest;
                balance -= principal;
                Schedule schedule = new Schedule();
                schedule.setPeriod(i);
                schedule.setPaymentAmount(round(monthlyRepayment, 2));
                schedule.setPrincipal(round(principal, 2));
                schedule.setInterest(round(interest, 2));
                totalInterest += schedule.getInterest();
                schedule.setBalance(round(balance, 2));
                amortisation.getSchedule().add(schedule);
            }
            amortisation.setTotalInterest(totalInterest);
            amortisation.setTotalPayment(totalInterest + totalLoanAmount);
            enquiry.setAmortisation(amortisation);
            enquiryRepository.save(enquiry);
        }catch (Exception e){
            logger.error(e);
        }
        return enquiry;
    }

    public double calculateMonthlyRepaymentWithoutBalloon(double totalLoanAmount, double monthlyInterestRate, int numberOfPayments) {
        double compoundFactor = Math.pow(1 + monthlyInterestRate, numberOfPayments);
        return totalLoanAmount * ((monthlyInterestRate * compoundFactor) / (compoundFactor - 1));
    }

    public double calculateMonthlyRepaymentWithBalloon(double totalLoanAmount, double balloonPayment, double monthlyInterestRate, int numberOfPayments) {
        double compoundFactor = Math.pow(1 + monthlyInterestRate, numberOfPayments);
        return  (totalLoanAmount - (balloonPayment / compoundFactor)) * (monthlyInterestRate / (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments)));
    }

    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public List<Enquiry> fetchAllGeneratedSchedules() throws EnquiryNotFoundException {
        List<Enquiry> enquiries = enquiryRepository.findAll();
        if (!enquiries.isEmpty()){
            return enquiries;
        }
        throw new EnquiryNotFoundException("no enquiries found");
    }

    public Enquiry getSchedule(Long id) throws EnquiryNotFoundException {
        return enquiryRepository.findById(id).orElseThrow(() -> new EnquiryNotFoundException("enquiry not found"));
    }

    public void deleteScheduleById(Long id) throws EnquiryNotFoundException {
         enquiryRepository.deleteById(id);
    }
}
