package com.onlinelibrary.service.impl;

import com.onlinelibrary.dto.LoanDto;
import com.onlinelibrary.entity.Book;
import com.onlinelibrary.entity.Loan;
import com.onlinelibrary.entity.User;
import com.onlinelibrary.exception.ResourceNotFoundException;
import com.onlinelibrary.repository.BookRepository;
import com.onlinelibrary.repository.LoanRepository;
import com.onlinelibrary.repository.UserRepository;
import com.onlinelibrary.service.LoanService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {

    final private LoanRepository loanRepository;

    final private BookRepository bookRepository;

    final private UserRepository userRepository;

    final private ModelMapper modelMapper;

    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository, BookRepository bookRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public LoanDto createLoan(Long userId, Long bookId) {
        Optional<Loan> activeLoan = loanRepository.findByUserIdAndReturnDateIsNull(userId);
        if (activeLoan.isPresent()) {
            throw new IllegalStateException("User has an active loan, must return the current book first.");
        }

        Book book = bookRepository.findByIdAndAvailable(bookId, true)
                .orElseThrow(() -> new ResourceNotFoundException("Book is not available for borrowing ", "id", bookId));

        LoanDto loanDto = new LoanDto();
        LocalDateTime borrowedAt = LocalDateTime.now();
        loanDto.setBorrowedAt(borrowedAt);
        loanDto.setUserId(userId);
        loanDto.setBookId(bookId);

        Loan loan = mapToEntity(loanDto);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : ", "id", userId));
        loan.setUser(user);
        loan.setBook(book);

        loan = loanRepository.save(loan);

        book.setAvailable(false);
        bookRepository.save(book);

        return mapToDTO(loan);
    }




    @Override
    public List<LoanDto> getAllLoan() {
        List<Loan> loans = loanRepository.findAll();

        return loans.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LoanDto getLoanById(Long id) {
        Loan loan = loanRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Loan", "id", id));
        return mapToDTO(loan);
    }

    @Override
    public LoanDto updateLoan(Long id, LoanDto loanDto) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No loan found with id : ", "id", id));

        if (loan.getReturnDate() != null) {
            throw new IllegalStateException("Cannot update a loan that has already been returned.");
        }

        loan.setBorrowedAt(loanDto.getBorrowedAt());
        loan.setReturnDate(loanDto.getReturnDate());
        loan = loanRepository.save(loan);

        return mapToDTO(loan);
    }


    @Override
    public void deleteLoanById(Long id) {
        Loan loan = loanRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Loan", "id", id));
        loanRepository.delete(loan);
    }

    @Override
    public String returnBook(Long userId) {
        Optional<Loan> loan = Optional.ofNullable(loanRepository.findByUserIdAndReturnDateIsNull(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No active loan found for the user ", "id", userId)));

        Loan activeLoan = loan.get();
        activeLoan.setReturnDate(LocalDateTime.now());
        loanRepository.save(activeLoan);

        Book book = activeLoan.getBook();
        book.setAvailable(true);
        bookRepository.save(book);

        return "Book returned successfully!.";
    }

    @Override
    public boolean isBookOverDue(Long loanId) {
        Optional<Loan> loan = loanRepository.findById(loanId);
        if (loan.isPresent()) {
            Loan activeLoan = loan.get();
            return activeLoan.getReturnDate() == null && activeLoan.getBorrowedAt().plusDays(14).isBefore(LocalDateTime.now());
        }
        return false;
    }


    private LoanDto mapToDTO(Loan loan){
        return modelMapper.map(loan, LoanDto.class);
    }

    private Loan mapToEntity(LoanDto loanDto){
        return modelMapper.map(loanDto, Loan.class);
    }
}
