package com.emt.med.baseBatch;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@MappedSuperclass
@Data
@EqualsAndHashCode(exclude = {"id", "quantity", "isAvailable"})
public abstract class BaseBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String manufacturer;
    private LocalDate expirationDate;
    private String administrationRoute;
    private BatchStatus status;
    private Integer quantity;
    private Boolean isAvailable;
    private Long parentBatchId;
}
