package com.ldms.loanManager.loanManagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Enquiry{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Enquiry(double assetCost, double deposit, double interestRate, int numberOfPayments, double balloonPayment) {
        this.assetCost = assetCost;
        this.deposit = deposit;
        this.interestRate = interestRate;
        this.numberOfPayments = numberOfPayments;
        this.balloonPayment = balloonPayment;
    }

    private double assetCost;
    private double deposit;
    private double interestRate;
    private int numberOfPayments;
    private double balloonPayment;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "amortisation_id")
    Amortisation amortisation;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP", name = "created_at")
    LocalDateTime createdAt = LocalDateTime.now();
}
