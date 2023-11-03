package com.emt.med.batch;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BatchEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String manufacturer;
    private LocalDate expirationDate;
    private String administrationRoute;
    private BatchStatus status;


}
