package com.ldms.loanManager.userManagement.repository;

import com.ldms.loanManager.userManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
