package com.onlinelibrary.repository;

import com.onlinelibrary.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    Optional<Loan> findByUserIdAndReturnDateIsNull(Long userId);
}
