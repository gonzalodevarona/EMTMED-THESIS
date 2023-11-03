package com.emt.med.batch;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchEntityDTO {
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
}
