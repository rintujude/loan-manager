package com.ldms.loanManager.loanManagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnquiryRequest {
    @NotNull(message = "shouldn't be null")
    @Min(value = 1, message = "should be least 1")
    private double assetCost;

    private double deposit;

    @NotNull(message = "shouldn't be null")
    @Min(value = 1, message = "should be least 1")
    private double interestRate;

    @Min(value = 1, message = "should be least 1")
    @NotNull(message = "shouldn't be null")
    private int numberOfPayments;

    private double balloonPayment;
}
