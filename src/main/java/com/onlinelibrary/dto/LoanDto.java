package com.onlinelibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanDto {
    private Long id;
    private Long userId;
    private Long bookId;
    private LocalDateTime borrowedAt;
    private LocalDateTime returnDate;
}
