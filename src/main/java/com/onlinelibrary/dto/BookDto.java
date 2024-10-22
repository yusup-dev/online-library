package com.onlinelibrary.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private Long id;
    private String imageUrl;
    private String title;
    private String author;
    private boolean available;
}
