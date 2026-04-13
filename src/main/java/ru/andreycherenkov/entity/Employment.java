package ru.andreycherenkov.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity(name = "employment_types")
public class Employment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employment_id")
    private Integer employmentId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
