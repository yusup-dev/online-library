package com.onlinelibrary.controller;

import com.onlinelibrary.dto.LoanDto;
import com.onlinelibrary.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public LoanDto createLoan(@RequestParam Long userId, @RequestParam Long bookId){
        return loanService.createLoan(userId, bookId);
    }

    @GetMapping()
    public List<LoanDto> getAllLoan(){
        return loanService.getAllLoan();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanDto> getLoanById(@PathVariable Long id){
        return ResponseEntity.ok(loanService.getLoanById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<LoanDto> updateLoan(@PathVariable Long id, @RequestBody LoanDto loanDto){
        LoanDto responseLoan = loanService.updateLoan(id, loanDto);
        return new ResponseEntity<>(responseLoan, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLoan(@PathVariable(name = "id") Long id){
        loanService.deleteLoanById(id);
        return new ResponseEntity<>("Loan deleted successfully!", HttpStatus.OK);
    }

    @PostMapping("/return")
    public String returnBook(@RequestParam Long userId){
        return loanService.returnBook(userId);
    }

    @GetMapping("/overdue/{loanId}")
    public ResponseEntity<Boolean> isBookOverdue(@PathVariable Long loanId) {
        LoanDto loanDTO = loanService.getLoanById(loanId);
        if (loanDTO != null) {
            boolean isOverdue = loanService.isBookOverDue(loanId);
            return ResponseEntity.ok(isOverdue);
        }
        return ResponseEntity.notFound().build();
    }



}
