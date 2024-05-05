package com.ldms.loanManager.loanManagement.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
public class Amortisation {

    public Amortisation(){
        schedule = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_interest")
    private double totalInterest;

    @Column(name = "total_payment")
    private double totalPayment;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "amortisation_id", referencedColumnName = "id")
    private List<Schedule> schedule;
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP", name = "created_at")
    LocalDateTime createdAt = LocalDateTime.now();
}
