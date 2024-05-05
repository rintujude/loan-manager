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
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int period;
    private double principal;
    private double interest;
    private double balance;
    @Column(name = "payment_amount")
    private double paymentAmount;
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP", name = "created_at")
    LocalDateTime createdAt = LocalDateTime.now();
}
