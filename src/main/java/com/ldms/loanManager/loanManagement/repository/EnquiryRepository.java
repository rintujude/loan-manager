package com.ldms.loanManager.loanManagement.repository;

import com.ldms.loanManager.loanManagement.model.Enquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnquiryRepository extends JpaRepository<Enquiry, Long> {
}
