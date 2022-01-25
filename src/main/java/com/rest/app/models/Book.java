package com.rest.app.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "book_record")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    private String summary;

    private int rating;
}
