package com.onlinelibrary.service.impl;

import com.onlinelibrary.dto.BookDto;
import com.onlinelibrary.entity.Book;
import com.onlinelibrary.entity.Loan;
import com.onlinelibrary.exception.ResourceNotFoundException;
import com.onlinelibrary.repository.BookRepository;
import com.onlinelibrary.repository.LoanRepository;
import com.onlinelibrary.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    final private BookRepository bookRepository;

    final private LoanRepository loanRepository;

    final private ModelMapper modelMapper;

    public BookServiceImpl(BookRepository bookRepository, LoanRepository loanRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        Book book = mapToEntity(bookDto);
        Book newBook = bookRepository.save(book);

        return mapToDTO(newBook);
    }

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();

        return books.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
        return mapToDTO(book);
    }

    @Override
    public List<BookDto> getBookByUserLoans(Long userId) {
        Optional<Loan> loans = loanRepository.findByUserIdAndReturnDateIsNull(userId);
        List<Book> books = loans.stream()
                .map(Loan::getBook)
                .toList();
        return books.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto updateBook(BookDto bookDto, Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));

        book.setImageUrl(bookDto.getImageUrl());
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setAvailable(bookDto.isAvailable());

        Book updateBook = bookRepository.save(book);
        return mapToDTO(updateBook);
    }

    @Override
    public void deleteBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
        bookRepository.delete(book);
    }

    private BookDto mapToDTO(Book book){
        return modelMapper.map(book, BookDto.class);
    }

    private Book mapToEntity(BookDto bookDto){
        return modelMapper.map(bookDto, Book.class);
    }
}
