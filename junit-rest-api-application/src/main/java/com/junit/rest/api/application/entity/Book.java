package com.junit.rest.api.application.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name ="book_record")
@Data
@ToString
public class Book {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long bookId;

@NonNull
private String name;

@NonNull
    private String summary;

private String rating;
}
