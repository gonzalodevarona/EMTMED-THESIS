package com.emt.med.medicationBatch;

import com.emt.med.batch.BatchStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicationBatchEntityDTO {

    @Positive(message = "id number should be greater than zero")
    private Long id;

    @NotBlank(message = "manufacturer shouldn't be blank")
    private String manufacturer;

    @Future(message = "expiration date should be in the future")
    private LocalDate expirationDate;

    @NotBlank(message = "administration route shouldn't be blank")
    private String administrationRoute;

    @NotNull(message = "status shouldn't be null")
    private BatchStatus status;

    @Min(0)
    private Integer quantity;

    @NotNull(message = "cum shouldn't be null")
    private String cum;
}
