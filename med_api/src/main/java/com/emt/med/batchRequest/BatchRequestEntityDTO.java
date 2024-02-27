package com.emt.med.batchRequest;

import com.emt.med.batch.BatchEntityDTO;
import com.emt.med.supplyOrder.SupplyOrderEntityDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchRequestEntityDTO {

    @Positive(message = "id number should be greater than zero")
    private Long id;

    @NotNull(message = "supplyOrder should not be null")
    private SupplyOrderEntityDTO supplyOrder;

    @NotNull(message = "supplyOrder should not be null")
    private BatchEntityDTO batch;

    @Positive(message = "quantity should be greater than zero")
    private int quantity;
}
