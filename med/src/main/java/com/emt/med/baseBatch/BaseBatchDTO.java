package com.emt.med.baseBatch;

import com.emt.med.supply.Supply;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseBatchDTO {
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
